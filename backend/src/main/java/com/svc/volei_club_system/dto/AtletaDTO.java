package com.svc.volei_club_system.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AtletaDTO {

    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String telefone;

    private String posicao;

    private Double altura;

    private String responsavel;

    private Boolean ativo;

    private String telefoneResponsavel;

    private Boolean perfilCompleto;
}