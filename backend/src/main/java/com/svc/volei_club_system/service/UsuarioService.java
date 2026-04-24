package com.svc.volei_club_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svc.volei_club_system.enums.Role;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.repository.UsuarioRepository;
import com.svc.volei_club_system.exception.ResourceNotFoundException;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    

    //cadastrar usuario
public UsuarioModel cadastrarUsuario(UsuarioModel usuario) {

    // verificar email duplicado
    if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {

        throw new RuntimeException("Email já cadastrado");

    }

    usuario.setAtivo(false);
    usuario.setRole(Role.ATLETA);

    return usuarioRepository.save(usuario);

}

    //listar todos 
    public Iterable<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    //buscar por email
    public UsuarioModel buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    //aprovar usuario
    public UsuarioModel aprovarUsuario(Long id) {
        UsuarioModel usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }

    //login
    public UsuarioModel login(String email, String senha) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }
        
        return usuario;
    }

   public UsuarioModel buscarUsuarioLogado(String email) {

    return usuarioRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado"));

}

}
