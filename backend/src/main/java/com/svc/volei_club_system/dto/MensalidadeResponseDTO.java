package com.svc.volei_club_system.dto;

import lombok.Data;
import com.svc.volei_club_system.model.StatusMensalidade;
import java.time.LocalDate;

@Data
public class MensalidadeResponseDTO {
    private Long id;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Double valor;
    private StatusMensalidade status;
    private String observacao;
    private Integer mesReferencia;
    private Integer anoReferencia;
    private String nomeAtleta;
    private Long atletaId;
}