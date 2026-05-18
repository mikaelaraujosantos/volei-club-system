package com.svc.volei_club_system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AtletaComUsuarioDTO {

    // Dados do Atleta

    private String nome;

    private LocalDate dataNascimento;

    private String telefone;

    private String posicao;

    private Double altura;

    private String responsavel;

    private Boolean ativo;

    // Dados do Usuário

    private String email;

    private String senha;

}