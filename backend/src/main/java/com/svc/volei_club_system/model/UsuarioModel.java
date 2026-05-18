package com.svc.volei_club_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.svc.volei_club_system.enums.Role;

@Entity
@Table(name = "usuarios")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String telefone;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private Role role;

}