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

        String header =
                request.getHeader("Authorization");

        System.out.println("Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token =
                    header.substring(7);

            System.out.println("Token recebido: " + token);

            try {

                String email =
                        jwtUtil.validarToken(token);

                System.out.println("Email do token: " + email);

                // salva no request
                request.setAttribute("email", email);

                // CRIA AUTHENTICATION (ESSENCIAL)
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.singletonList(
                                        new SimpleGrantedAuthority("USER")
                                )
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);

                System.out.println("Authentication criada!");

            } catch (Exception e) {

                System.out.println("Erro ao validar token!");

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}