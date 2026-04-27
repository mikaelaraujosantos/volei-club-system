package com.svc.volei_club_system.dto;

import lombok.Data;
import java.util.List;

@Data
public class DashboardAtletaDTO {
    private String nomeAtleta;
    private Long mensalidadesPagas;
    private Long mensalidadesPendentes;
    private Long mensalidadesVencidas;
    private Double totalPago;
    private Double totalDevendo;
    private List<MensalidadeResponseDTO> proximasMensalidades;
    private MensalidadeResponseDTO ultimaMensalidade;
}