package com.svc.volei_club_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svc.volei_club_system.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    //bucar por email
    Optional<UsuarioModel> findByEmail(String email);


}
