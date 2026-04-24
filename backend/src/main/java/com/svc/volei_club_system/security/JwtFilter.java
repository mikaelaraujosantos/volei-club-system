package com.svc.volei_club_system.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            System.out.println("Token recebido: " + token);

            try {

                // Valida token e obtém os claims
                Claims claims = jwtUtil.validarToken(token);
                
                String email = claims.getSubject();
                String role = claims.get("role", String.class);
                
                System.out.println("Email do token: " + email);
                System.out.println("Role do token: " + role);

                // Salva no request para controllers acessarem
                request.setAttribute("email", email);
                request.setAttribute("role", role);

                // Cria autenticação com a role correta
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.singletonList(
                                        new SimpleGrantedAuthority("ROLE_" + role)
                                )
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("Authentication criada com role: ROLE_" + role);

            } catch (Exception e) {

                System.out.println("Erro ao validar token: " + e.getMessage());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}