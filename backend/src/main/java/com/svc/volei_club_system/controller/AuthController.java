package com.svc.volei_club_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.svc.volei_club_system.dto.LoginDTO;
import com.svc.volei_club_system.dto.LoginResponseDTO;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.security.JwtUtil;
import com.svc.volei_club_system.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

   @PostMapping("/login")
public LoginResponseDTO login(
        @RequestBody LoginDTO loginDTO
) {

    UsuarioModel usuario =
        usuarioService.buscarPorEmail(loginDTO.getEmail());

    if (!usuario.getSenha().equals(loginDTO.getSenha())) {

        throw new RuntimeException("Senha inválida");

    }

    String token =
        jwtUtil.gerarToken(usuario.getEmail());

    return new LoginResponseDTO(token);

}

}