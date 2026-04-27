package com.svc.volei_club_system.service;

import com.svc.volei_club_system.dto.MensalidadeRequestDTO;
import com.svc.volei_club_system.dto.MensalidadeResponseDTO;
import com.svc.volei_club_system.dto.MensalidadeEmMassaRequestDTO;
import com.svc.volei_club_system.model.MensalidadeModel;
import com.svc.volei_club_system.model.StatusMensalidade;
import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.repository.MensalidadeRepository;
import com.svc.volei_club_system.repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensalidadeService {

    @Autowired
    private MensalidadeRepository mensalidadeRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    @Transactional
    public MensalidadeResponseDTO criarMensalidade(MensalidadeRequestDTO request) {
        AtletaModel atleta = atletaRepository.findById(request.getAtletaId())
            .orElseThrow(() -> new RuntimeException("Atleta não encontrado com ID: " + request.getAtletaId()));

        // Verificar se já existe mensalidade para este mês/ano
        MensalidadeModel existente = mensalidadeRepository.findByAtletaIdAndMesAno(
            request.getAtletaId(), 
            request.getMesReferencia(), 
            request.getAnoReferencia()
        );
        
        if (existente != null) {
            throw new RuntimeException("Já existe uma mensalidade para este atleta no mês/ano informado");
        }

        MensalidadeModel mensalidade = new MensalidadeModel();
        mensalidade.setDataVencimento(request.getDataVencimento());
        mensalidade.setDataPagamento(request.getDataPagamento());
        mensalidade.setValor(request.getValor());
        mensalidade.setObservacao(request.getObservacao());
        mensalidade.setMesReferencia(request.getMesReferencia());
        mensalidade.setAnoReferencia(request.getAnoReferencia());
        mensalidade.setAtleta(atleta);
        
        // Definir status baseado na data de pagamento
        if (request.getDataPagamento() != null) {
            mensalidade.setStatus(StatusMensalidade.PAGA);
        } else {
            mensalidade.setStatus(StatusMensalidade.PENDENTE);
        }

        MensalidadeModel saved = mensalidadeRepository.save(mensalidade);
        return convertToResponseDTO(saved);
    }

    @Transactional
    public List<MensalidadeResponseDTO> criarMensalidadesEmMassa(MensalidadeEmMassaRequestDTO request) {
        List<AtletaModel> atletas;
        
        // Se lista de atletas foi fornecida e não está vazia
        if (request.getAtletasIds() != null && !request.getAtletasIds().isEmpty()) {
            atletas = atletaRepository.findAllById(request.getAtletasIds());
        } else {
            // Criar para TODOS os atletas ativos
            atletas = atletaRepository.findByAtivoTrue();
        }
        
        if (atletas.isEmpty()) {
            throw new RuntimeException("Nenhum atleta encontrado para criar mensalidades");
        }
        
        List<MensalidadeResponseDTO> responses = new ArrayList<>();
        
        for (AtletaModel atleta : atletas) {
            // Verificar se já existe mensalidade para este mês/ano
            MensalidadeModel existente = mensalidadeRepository.findByAtletaIdAndMesAno(
                atleta.getId(), 
                request.getMesReferencia(), 
                request.getAnoReferencia()
            );
            
            if (existente == null) {
                MensalidadeModel mensalidade = new MensalidadeModel();
                mensalidade.setDataVencimento(request.getDataVencimento());
                mensalidade.setValor(request.getValor());
                mensalidade.setMesReferencia(request.getMesReferencia());
                mensalidade.setAnoReferencia(request.getAnoReferencia());
                mensalidade.setObservacao(request.getObservacao());
                mensalidade.setAtleta(atleta);
                mensalidade.setStatus(StatusMensalidade.PENDENTE);
                
                MensalidadeModel saved = mensalidadeRepository.save(mensalidade);
                responses.add(convertToResponseDTO(saved));
            }
        }
        
        return responses;
    }

    @Transactional
    public MensalidadeResponseDTO cancelarMensalidade(Long id) {
        MensalidadeModel mensalidade = mensalidadeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensalidade não encontrada"));
        
        // Só pode cancelar se estiver PENDENTE ou VENCIDA
        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new RuntimeException("Não é possível cancelar uma mensalidade já paga");
        }
        
        mensalidade.setStatus(StatusMensalidade.CANCELADA);
        MensalidadeModel updated = mensalidadeRepository.save(mensalidade);
        return convertToResponseDTO(updated);
    }

    @Transactional
    public MensalidadeResponseDTO registrarPagamento(Long id, LocalDate dataPagamento) {
        MensalidadeModel mensalidade = mensalidadeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensalidade não encontrada com ID: " + id));
        
        mensalidade.setDataPagamento(dataPagamento);
        mensalidade.setStatus(StatusMensalidade.PAGA);
        
        MensalidadeModel updated = mensalidadeRepository.save(mensalidade);
        return convertToResponseDTO(updated);
    }

    @Transactional
    public void atualizarStatusVencidas() {
        List<MensalidadeModel> vencidas = mensalidadeRepository.findByDataVencimentoBeforeAndStatusNot(
            LocalDate.now(), StatusMensalidade.PAGA);
        
        for (MensalidadeModel mensalidade : vencidas) {
            if (mensalidade.getStatus() != StatusMensalidade.VENCIDA) {
                mensalidade.setStatus(StatusMensalidade.VENCIDA);
            }
        }
        mensalidadeRepository.saveAll(vencidas);
    }

    public List<MensalidadeResponseDTO> listarPorAtleta(Long atletaId) {
        return mensalidadeRepository.findByAtletaId(atletaId)
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    public List<MensalidadeResponseDTO> listarPendentesPorAtleta(Long atletaId) {
        AtletaModel atleta = atletaRepository.findById(atletaId)
            .orElseThrow(() -> new RuntimeException("Atleta não encontrado"));
        
        return mensalidadeRepository.findByAtletaAndStatus(atleta, StatusMensalidade.PENDENTE)
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    public List<MensalidadeResponseDTO> listarTodas() {
        return mensalidadeRepository.findAll()
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deletarMensalidade(Long id) {
        if (!mensalidadeRepository.existsById(id)) {
            throw new RuntimeException("Mensalidade não encontrada");
        }
        mensalidadeRepository.deleteById(id);
    }

    private MensalidadeResponseDTO convertToResponseDTO(MensalidadeModel model) {
        MensalidadeResponseDTO dto = new MensalidadeResponseDTO();
        dto.setId(model.getId());
        dto.setDataVencimento(model.getDataVencimento());
        dto.setDataPagamento(model.getDataPagamento());
        dto.setValor(model.getValor());
        dto.setStatus(model.getStatus());
        dto.setObservacao(model.getObservacao());
        dto.setMesReferencia(model.getMesReferencia());
        dto.setAnoReferencia(model.getAnoReferencia());
        dto.setAtletaId(model.getAtleta().getId());
        dto.setNomeAtleta(model.getAtleta().getNome());
        return dto;
    }
}