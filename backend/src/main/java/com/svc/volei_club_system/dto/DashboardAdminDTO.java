package com.svc.volei_club_system.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DashboardAdminDTO {
    private Long totalAtletas;
    private Long totalMensalidades;
    private Long mensalidadesPagas;
    private Long mensalidadesPendentes;
    private Long mensalidadesVencidas;
    private Double receitaTotal;
    private Double receitaMesAtual;
    private List<MensalidadeResponseDTO> ultimasMensalidades;
    private Map<String, Long> mensalidadesPorStatus;
}