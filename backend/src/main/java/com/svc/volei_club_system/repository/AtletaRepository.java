package com.svc.volei_club_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.model.UsuarioModel;

public interface AtletaRepository
        extends JpaRepository<AtletaModel, Long> {

    // LISTAR por usuário
    List<AtletaModel> findByUsuario(
            UsuarioModel usuario
    );

    // BUSCAR por ID e usuário (SEGURANÇA)
    Optional<AtletaModel> findByIdAndUsuario(
            Long id,
            UsuarioModel usuario
    );

}