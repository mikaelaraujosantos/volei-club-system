package com.svc.volei_club_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.svc.volei_club_system.dto.LoginDTO;
import com.svc.volei_club_system.dto.LoginResponseDTO;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.security.JwtUtil;
import com.svc.volei_club_system.service.UsuarioService;
import com.svc.volei_club_system.service.AtletaService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AtletaService atletaService;  // Adicione esta linha

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDTO) {

        UsuarioModel usuario = usuarioService.buscarPorEmail(loginDTO.getEmail());

        if (usuario == null || !usuario.getSenha().equals(loginDTO.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        // Converte Enum para String
        String roleString = usuario.getRole().name();
        
        // Gera token com email e role
        String token = jwtUtil.gerarToken(usuario.getEmail(), roleString);
        
        // Buscar atletaId se for ATLETA
        Long atletaId = null;
        if (roleString.equals("ATLETA")) {
            AtletaModel atleta = atletaService.buscarPorUsuarioId(usuario.getId());
            if (atleta != null) {
                atletaId = atleta.getId();
            }
        }

        return new LoginResponseDTO(token, roleString, atletaId);
    }
}