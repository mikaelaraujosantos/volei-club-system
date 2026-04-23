package com.svc.volei_club_system.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtletaUpdateDTO {

    private String nome;

    private Integer idade;

    private String telefone;

    private String posicao;

    private Double altura;

    private String responsavel;

    private Boolean ativo;

}