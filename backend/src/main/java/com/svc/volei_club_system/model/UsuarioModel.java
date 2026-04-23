package com.svc.volei_club_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Integer idade;

    private String telefone;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha;

    private Boolean ativo;

    //admin ou atleta
    @Enumerated(EnumType.STRING)
    private Role role;
    



}
