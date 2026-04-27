package com.svc.volei_club_system.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class MensalidadeEmMassaRequestDTO {
    private List<Long> atletasIds; // Se vazio ou null, cria para todos
    private LocalDate dataVencimento;
    private Double valor;
    private Integer mesReferencia;
    private Integer anoReferencia;
    private String observacao;
}