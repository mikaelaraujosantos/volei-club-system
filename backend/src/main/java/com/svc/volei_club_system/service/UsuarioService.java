package com.svc.volei_club_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.svc.volei_club_system.enums.Role;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.repository.UsuarioRepository;
import com.svc.volei_club_system.exception.ResourceNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =========================
    // CADASTRAR USUÁRIO
    // =========================

    public UsuarioModel cadastrarUsuario(UsuarioModel usuario) {

    // verificar email duplicado
    if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {

        throw new RuntimeException("Email já cadastrado");

    }

    // 🔥 ativar usuário automaticamente
    usuario.setAtivo(true);

    // definir role
    usuario.setRole(Role.ATLETA);

    return usuarioRepository.save(usuario);

}
    // =========================
    // SALVAR USUÁRIO
    // =========================

    public UsuarioModel salvar(UsuarioModel usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        return usuarioRepository.save(usuario);

    }

    // =========================
    // LISTAR
    // =========================

    public Iterable<UsuarioModel> listarUsuarios() {

        return usuarioRepository.findAll();

    }

    // =========================
    // BUSCAR POR EMAIL
    // =========================

    public UsuarioModel buscarPorEmail(String email) {

        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

    }

    // =========================
    // APROVAR USUÁRIO
    // =========================

    public UsuarioModel aprovarUsuario(Long id) {

        UsuarioModel usuario =
                usuarioRepository
                        .findById(id)
                        .orElseThrow();

        usuario.setAtivo(true);

        return usuarioRepository.save(usuario);

    }

    // =========================
    // LOGIN
    // =========================

    public UsuarioModel login(String email, String senha) {

        UsuarioModel usuario =
                usuarioRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {

            throw new RuntimeException("Senha incorreta");

        }

        return usuario;

    }

    // =========================
    // USUÁRIO LOGADO
    // =========================

    public UsuarioModel buscarUsuarioLogado(String email) {

        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado"));

    }

}