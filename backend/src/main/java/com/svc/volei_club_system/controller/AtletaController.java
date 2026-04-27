package com.svc.volei_club_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.svc.volei_club_system.dto.*;
import com.svc.volei_club_system.mapper.AtletaMapper;
import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.repository.AtletaRepository;
import com.svc.volei_club_system.service.AtletaService;
import com.svc.volei_club_system.service.UsuarioService;
import com.svc.volei_club_system.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AtletaRepository atletaRepository;
        
    @CrossOrigin(origins = "*", allowedHeaders = "*")       
    // =========================
    // CADASTRAR
    // =========================

    @PostMapping
    public AtletaDTO cadastrar(
           @Valid @RequestBody AtletaCreateDTO dto,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        AtletaModel atleta =
                AtletaMapper.toModel(dto);

        AtletaModel salvo =
                atletaService.cadastrarAtleta(
                        atleta,
                        email
                );

        return AtletaMapper.toDTO(salvo);

    }

    // =========================
    // LISTAR
    // =========================

    @GetMapping
    public Page<AtletaDTO> listar(
            Pageable pageable,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        return atletaService
                .listarAtletas(email, pageable)
                .map(AtletaMapper::toDTO);

    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @GetMapping("/{id}")
    public AtletaDTO buscarPorId(
            @PathVariable Long id,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        AtletaModel atleta =
                atletaService.buscarPorId(
                        id,
                        email
                );

        return AtletaMapper.toDTO(atleta);

    }

    // =========================
    // ATUALIZAR
    // =========================

    @PutMapping("/{id}")
    public AtletaDTO atualizar(
            @PathVariable Long id,
            @RequestBody AtletaUpdateDTO dto,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        AtletaModel dados =
                AtletaMapper.toModel(dto);

        AtletaModel atualizado =
                atletaService.atualizarAtleta(
                        id,
                        dados,
                        email
                );

        return AtletaMapper.toDTO(atualizado);

    }

    // =========================
    // DELETAR
    // =========================

    @DeleteMapping("/{id}")
    public void deletar(
            @PathVariable Long id,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        atletaService.deletarAtleta(
                id,
                email
        );

    }
    
    // =========================
    // CADASTRO COMPLETO (ATLETA + USUÁRIO)
    // =========================
    
    @PostMapping("/cadastro-completo")
public ResponseEntity<?> cadastrarAtletaComUsuario(
        @RequestBody AtletaComUsuarioDTO dto,
        HttpServletRequest request  // ADICIONE ESTE PARÂMETRO
) {
    try {
        // Pegar o email do ADMIN logado (quem está cadastrando)
        String emailAdmin = (String) request.getAttribute("email");
        
        // Verificar se o ADMIN existe (opcional, apenas para validação)
        UsuarioModel admin = usuarioService.buscarPorEmail(emailAdmin);
        if (admin == null) {
            return ResponseEntity
                .status(403)
                .body("Apenas administradores podem cadastrar atletas");
        }
        
        // 1. Verificar se email do atleta já existe
        if (usuarioService.buscarPorEmail(dto.getEmail()) != null) {
            return ResponseEntity
                .badRequest()
                .body("Email já está em uso!");
        }
        
        // 2. Criar usuário para o ATLETA
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setRole(Role.ATLETA);
        usuario.setAtivo(true);
        // Se houver outros campos obrigatórios, preencha aqui
        usuario.setNome(dto.getNome()); // se existir campo nome
        usuario.setIdade(dto.getIdade()); // se existir campo idade
        usuario.setTelefone(dto.getTelefone()); // se existir campo telefone
        
        UsuarioModel usuarioSalvo = usuarioService.salvar(usuario);
        
        // 3. Criar atleta vinculado ao usuário
        AtletaModel atleta = new AtletaModel();
        atleta.setNome(dto.getNome());
        atleta.setIdade(dto.getIdade());
        atleta.setTelefone(dto.getTelefone());
        atleta.setPosicao(dto.getPosicao());
        atleta.setAltura(dto.getAltura());
        atleta.setResponsavel(dto.getResponsavel());
        atleta.setAtivo(true);
        atleta.setUsuario(usuarioSalvo);
        
        AtletaModel atletaSalvo = atletaRepository.save(atleta);
        
        return ResponseEntity.ok(AtletaMapper.toDTO(atletaSalvo));
        
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity
            .status(500)
            .body("Erro ao cadastrar: " + e.getMessage());
    }
}
}