package com.svc.volei_club_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.model.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface AtletaRepository
        extends JpaRepository<AtletaModel, Long> {


    // BUSCAR por ID e usuário (SEGURANÇA)
    Optional<AtletaModel> findByIdAndUsuario(
            Long id,
            UsuarioModel usuario
    );

    Page<AtletaModel> findByUsuario(
        UsuarioModel usuario,
        Pageable pageable
);
List<AtletaModel> findByAtivoTrue();

 Optional<AtletaModel> findByUsuarioId(Long usuarioId);

 Optional<AtletaModel> findByUsuario(
        UsuarioModel usuario
);

}