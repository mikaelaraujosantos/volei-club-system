package com.svc.volei_club_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AtletaService atletaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
public LoginResponseDTO login(
        @RequestBody LoginDTO loginDTO
) {

    // =========================
    // BUSCAR USUÁRIO
    // =========================

    UsuarioModel usuario =
            usuarioService.buscarPorEmail(
                    loginDTO.getEmail()
            );

    // =========================
    // VALIDAR SENHA
    // =========================

    if (
            !passwordEncoder.matches(
                    loginDTO.getSenha(),
                    usuario.getSenha()
            )
    ) {

        throw new RuntimeException(
                "Email ou senha inválidos"
        );

    }

    // =========================
    // ROLE
    // =========================

    String roleString =
            usuario.getRole().name();

    // =========================
    // TOKEN
    // =========================

    String token =
            jwtUtil.gerarToken(
                    usuario.getEmail(),
                    roleString
            );

    // =========================
    // DADOS DO ATLETA
    // =========================

    Long atletaId = null;

    Boolean perfilCompleto = true;

    if (roleString.equals("ATLETA")) {

        AtletaModel atleta =
                atletaService.buscarPorUsuarioId(
                        usuario.getId()
                );

        if (atleta != null) {

            atletaId =
                    atleta.getId();

            perfilCompleto =
                    atleta.getPerfilCompleto();

        }

    }

    // =========================
    // RESPONSE
    // =========================

    return new LoginResponseDTO(

            token,

            roleString,

            atletaId,

            perfilCompleto

    );

}
}