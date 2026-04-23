package com.svc.volei_club_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.service.AtletaService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    @Autowired
    private AtletaService atletaService;

    // =========================
    // CADASTRAR
    // =========================

    @PostMapping
    public AtletaModel cadastrar(
            @RequestBody AtletaModel atleta,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        return atletaService.cadastrarAtleta(
                atleta,
                email
        );

    }

    // =========================
    // LISTAR
    // =========================

    @GetMapping
    public List<AtletaModel> listar(
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        return atletaService.listarAtletas(email);

    }

    // =========================
    // BUSCAR POR ID
    // =========================

    @GetMapping("/{id}")
    public AtletaModel buscarPorId(
            @PathVariable Long id,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        return atletaService.buscarPorId(
                id,
                email
        );

    }

    // =========================
    // ATUALIZAR
    // =========================

    @PutMapping("/{id}")
    public AtletaModel atualizar(
            @PathVariable Long id,
            @RequestBody AtletaModel atleta,
            HttpServletRequest request
    ) {

        String email =
                (String) request.getAttribute("email");

        return atletaService.atualizarAtleta(
                id,
                atleta,
                email
        );

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
    @PatchMapping("/{id}")
public AtletaModel atualizarParcial(
        @PathVariable Long id,
        @RequestBody AtletaModel atleta,
        HttpServletRequest request
) {

    String email =
            (String) request.getAttribute("email");

    return atletaService.atualizarParcial(
            id,
            atleta,
            email
    );

}

}