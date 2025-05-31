package com.alpha.school_system_api.infra;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private static final String SECRET = "minha-chave-super-secreta-para-gerar-jwt-12345678"; // deve ter no mínimo 256
                                                                                              // bits (32 caracteres)

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // 🔑 Gerar token
    public String gerarToken(UserDetails userDetails) {
        // Extraia as roles (autoridades) como Strings
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(granted -> granted.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles) // adiciona as roles no payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 📥 Extrair e-mail (username) do token
    public String extrairUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ Verificar se o token é válido
    public boolean validarToken(String token, UserDetails userDetails) {
        String username = extrairUsername(token);
        return username.equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    // 🕒 Verificar se expirou
    private boolean estaExpirado(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
