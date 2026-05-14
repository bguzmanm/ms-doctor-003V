package cl.duoc.doctor.config;

import cl.duoc.doctor.filter.AuthFilter;
import cl.duoc.doctor.service.impl.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // habilita @PreAuthorize en métodos
public class SecurityConfig {

    @Bean
    public AuthFilter authFilter(AuthService authService, UserDetailsService userDetailsService) {
        return new AuthFilter(authService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthFilter authFilter) throws Exception {
        return http
                // Deshabilitar CSRF (no necesario en APIs REST stateless)
                .csrf(csrf -> csrf.disable())
                // Política STATELESS: sin sesiones HTTP
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Reglas de acceso por endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/doctors/**").hasRole("ADMIN") // solo ADMIN
                        .anyRequest().permitAll()                  // el resto: autenticado
                )

                // Agregar el filtro JWT antes del filtro de autenticación por defecto
                .addFilterBefore(authFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public AuthenticationManager authManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // hashear contraseñas
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("1234"))
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}
