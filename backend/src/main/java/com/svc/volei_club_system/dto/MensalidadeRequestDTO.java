package com.svc.volei_club_system.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MensalidadeRequestDTO {
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Double valor;
    private String observacao;
    private Integer mesReferencia;
    private Integer anoReferencia;
    private Long atletaId;
}