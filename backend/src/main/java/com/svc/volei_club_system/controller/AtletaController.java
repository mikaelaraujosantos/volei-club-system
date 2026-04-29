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
import com.svc.volei_club_system.repository.AtletaRepository;
import com.svc.volei_club_system.service.AtletaService;
import com.svc.volei_club_system.service.UsuarioService;
import com.svc.volei_club_system.enums.Role;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/atletas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AtletaRepository atletaRepository;

    // =========================
    // LISTAR ATLETAS
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
    // CADASTRO COMPLETO
    // ATLETA + USUÁRIO
    // =========================

    @PostMapping("/cadastro-completo")
    public ResponseEntity<?> cadastrarAtletaComUsuario(
            @RequestBody AtletaComUsuarioDTO dto
    ) {

        try {

            // =========================
            // VERIFICAR EMAIL
            // =========================

            try {
                usuarioService.buscarPorEmail(dto.getEmail());

                return ResponseEntity
                        .badRequest()
                        .body("Email já está em uso!");

            } catch (Exception e) {
                // email não existe → continuar
            }

            // =========================
            // CRIAR USUÁRIO
            // =========================

            UsuarioModel usuario =
                    new UsuarioModel();

            usuario.setNome(dto.getNome());
            usuario.setIdade(dto.getIdade());
            usuario.setTelefone(dto.getTelefone());

            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());

            usuario.setRole(Role.ATLETA);
            usuario.setAtivo(true);

            UsuarioModel usuarioSalvo =
                    usuarioService.salvar(usuario);

            // =========================
            // CRIAR ATLETA
            // =========================

            AtletaModel atleta =
                    new AtletaModel();

            atleta.setNome(dto.getNome());
            atleta.setIdade(dto.getIdade());
            atleta.setTelefone(dto.getTelefone());

            atleta.setPosicao(dto.getPosicao());
            atleta.setAltura(dto.getAltura());
            atleta.setResponsavel(dto.getResponsavel());

            atleta.setAtivo(true);
            atleta.setUsuario(usuarioSalvo);

            AtletaModel atletaSalvo =
                    atletaRepository.save(atleta);

            return ResponseEntity.ok(
                    AtletaMapper.toDTO(atletaSalvo)
            );

        }

        catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .status(500)
                    .body("Erro ao cadastrar: "
                            + e.getMessage());

        }

    }

}