package cl.duoc.doctor.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {
    // Clave secreta (en producción: usar variable de entorno)
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    // Extraer el username del token
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Validar que el token sea correcto y no haya expirado
    public boolean isValidToken(String token, UserDetails usr) {
        String username = extractUsername(token);
        return username.equals(usr.getUsername()) && !isExpiredToken(token);
    }

    public boolean isExpiredToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
}
