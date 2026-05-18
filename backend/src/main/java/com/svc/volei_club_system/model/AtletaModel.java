package com.svc.volei_club_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "atleta")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AtletaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String telefone;

    private String posicao;

    private Double altura;

    private String responsavel;

    private String telefoneResponsavel;

    private Boolean ativo;

    private Boolean perfilCompleto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioModel usuario;

}