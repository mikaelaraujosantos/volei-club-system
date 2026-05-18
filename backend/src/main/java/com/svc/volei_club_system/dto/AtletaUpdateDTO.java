package com.svc.volei_club_system.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AtletaUpdateDTO {

    private String nome;

    private LocalDate dataNascimento;

    private String telefone;

    private String posicao;

    private Double altura;

    private String responsavel;

    private String telefoneResponsavel;

    private Boolean ativo;

    private Boolean perfilCompleto;

}