package com.svc.volei_club_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.svc.volei_club_system.dto.*;
import com.svc.volei_club_system.mapper.AtletaMapper;
import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.service.AtletaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

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

}