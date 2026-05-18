package com.svc.volei_club_system.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CompletarPerfilDTO {

    private LocalDate dataNascimento;

    private String posicao;

    private Double altura;

    private String responsavel;

    private String telefoneResponsavel;

}