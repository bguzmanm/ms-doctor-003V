package cl.duoc.doctor.filter;

import cl.duoc.doctor.service.impl.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService service;
    private final UserDetailsService userDetailsService;

    public AuthFilter(AuthService service, UserDetailsService userDetailsService) {
        this.service = service;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. Leer el header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Si no hay token o no empieza con "Bearer ", continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitar "Bearer ")
        String jwt = authHeader.substring(7);

        // 4. Extraer el username del token
        String username = service.extractUsername(jwt);
        logger.info(username);

        // 5. Si hay username y el contexto de seguridad está vacío
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails usr = userDetailsService.loadUserByUsername(username);

            // 6. Validar el token
            if (service.isValidToken(jwt, usr)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                usr, null, usr.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                // 7. Cargar la identidad en el SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // continuar la cadena
    }
}
