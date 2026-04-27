package com.svc.volei_club_system.controller;

import com.svc.volei_club_system.dto.MensalidadeRequestDTO;
import com.svc.volei_club_system.dto.MensalidadeResponseDTO;
import com.svc.volei_club_system.dto.MensalidadeEmMassaRequestDTO;
import com.svc.volei_club_system.service.MensalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/mensalidades")
@CrossOrigin(origins = "*")
public class MensalidadeController {

    @Autowired
    private MensalidadeService mensalidadeService;

    @PostMapping
    public ResponseEntity<MensalidadeResponseDTO> criar(@RequestBody MensalidadeRequestDTO request) {
        MensalidadeResponseDTO response = mensalidadeService.criarMensalidade(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MensalidadeResponseDTO>> criarMensalidadesEmMassa(@RequestBody MensalidadeEmMassaRequestDTO request) {
        List<MensalidadeResponseDTO> responses = mensalidadeService.criarMensalidadesEmMassa(request);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<MensalidadeResponseDTO> registrarPagamento(
            @PathVariable Long id,
            @RequestParam(required = false) LocalDate dataPagamento) {
        
        if (dataPagamento == null) {
            dataPagamento = LocalDate.now();
        }
        
        MensalidadeResponseDTO response = mensalidadeService.registrarPagamento(id, dataPagamento);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<MensalidadeResponseDTO> cancelarMensalidade(@PathVariable Long id) {
        MensalidadeResponseDTO response = mensalidadeService.cancelarMensalidade(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/atleta/{atletaId}")
    public ResponseEntity<List<MensalidadeResponseDTO>> listarPorAtleta(@PathVariable Long atletaId) {
        List<MensalidadeResponseDTO> mensalidades = mensalidadeService.listarPorAtleta(atletaId);
        return ResponseEntity.ok(mensalidades);
    }

    @GetMapping("/atleta/{atletaId}/pendentes")
    public ResponseEntity<List<MensalidadeResponseDTO>> listarPendentesPorAtleta(@PathVariable Long atletaId) {
        List<MensalidadeResponseDTO> mensalidades = mensalidadeService.listarPendentesPorAtleta(atletaId);
        return ResponseEntity.ok(mensalidades);
    }

    @GetMapping
    public ResponseEntity<List<MensalidadeResponseDTO>> listarTodas() {
        List<MensalidadeResponseDTO> mensalidades = mensalidadeService.listarTodas();
        return ResponseEntity.ok(mensalidades);
    }

    @PutMapping("/atualizar-status-vencidos")
    public ResponseEntity<Void> atualizarStatusVencidos() {
        mensalidadeService.atualizarStatusVencidas();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mensalidadeService.deletarMensalidade(id);
        return ResponseEntity.noContent().build();
    }
}