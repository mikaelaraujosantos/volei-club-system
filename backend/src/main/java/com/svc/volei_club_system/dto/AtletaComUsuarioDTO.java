package com.svc.volei_club_system.dto;

import lombok.Data;

@Data
public class AtletaComUsuarioDTO {
    // Dados do Atleta
    private String nome;
    private Integer idade;
    private String telefone;
    private String posicao;
    private Double altura;
    private String responsavel;
    private Boolean ativo;
    
    // Dados do Usuário
    private String email;
    private String senha;
}