package com.svc.volei_club_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.service.UsuarioService;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

   

    //cadastrar
    @PostMapping
    public UsuarioModel cadastrar(@RequestBody UsuarioModel usuario) {
        
        
        return usuarioService.cadastrarUsuario(usuario);
    }

    //listar todos
    @GetMapping
    public Iterable<UsuarioModel> listar() {
        return usuarioService.listarUsuarios();
    }
    

    //aprovar usuario
    @PutMapping("/aprovar/{id}")
    public UsuarioModel aprovar(@PathVariable Long id) {
        return usuarioService.aprovarUsuario(id);
    }

    @GetMapping("/teste")
public String rotaProtegida() {

    return "Rota protegida acessada com sucesso";

}

@GetMapping("/me")
public UsuarioModel usuarioLogado() {

    String email = (String)
        SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

    return usuarioService.buscarUsuarioLogado(email);

}

}
