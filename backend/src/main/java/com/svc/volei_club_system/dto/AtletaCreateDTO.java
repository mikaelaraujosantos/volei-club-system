package com.svc.volei_club_system.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtletaCreateDTO {

    @NotBlank
    private String nome;

    @NotNull
    @Min(5)
    @Max(60)
    private Integer idade;

    @NotBlank
    private String telefone;

    @NotBlank
    private String posicao;

    @NotNull
    @Positive
    private Double altura;

    private String responsavel;

    private Boolean ativo;

}