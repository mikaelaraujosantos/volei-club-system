package com.svc.volei_club_system.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtil {

    private final String SECRET =
        "segredo_super_secreto_muito_grande_para_token_jwt_123456";

    private final Key key =
        Keys.hmacShaKeyFor(SECRET.getBytes());

    // Método para gerar token com email e role
    public String gerarToken(String email, String role) {
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("email", email);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10) // 10 horas
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para validar token e retornar Claims
    public Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    // Método auxiliar para extrair apenas o email
    public String extrairEmail(String token) {
        return validarToken(token).getSubject();
    }
    
    // Método auxiliar para extrair a role
    public String extrairRole(String token) {
        return validarToken(token).get("role", String.class);
    }
}