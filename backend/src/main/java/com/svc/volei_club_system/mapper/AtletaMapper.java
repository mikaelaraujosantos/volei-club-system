package com.svc.volei_club_system.mapper;

import com.svc.volei_club_system.dto.AtletaDTO;
import com.svc.volei_club_system.dto.AtletaCreateDTO;
import com.svc.volei_club_system.dto.AtletaUpdateDTO;
import com.svc.volei_club_system.model.AtletaModel;

public class AtletaMapper {

    // Model → DTO

    public static AtletaDTO toDTO(AtletaModel atleta) {

        return AtletaDTO.builder()
                .id(atleta.getId())
                .nome(atleta.getNome())
                .idade(atleta.getIdade())
                .telefone(atleta.getTelefone())
                .posicao(atleta.getPosicao())
                .altura(atleta.getAltura())
                .responsavel(atleta.getResponsavel())
                .ativo(atleta.getAtivo())
                .build();

    }

    // CreateDTO → Model

    public static AtletaModel toModel(
            AtletaCreateDTO dto
    ) {

        return AtletaModel.builder()
                .nome(dto.getNome())
                .idade(dto.getIdade())
                .telefone(dto.getTelefone())
                .posicao(dto.getPosicao())
                .altura(dto.getAltura())
                .responsavel(dto.getResponsavel())
                .ativo(dto.getAtivo())
                .build();

    }

    // UpdateDTO → Model

    public static AtletaModel toModel(
            AtletaUpdateDTO dto
    ) {

        return AtletaModel.builder()
                .nome(dto.getNome())
                .idade(dto.getIdade())
                .telefone(dto.getTelefone())
                .posicao(dto.getPosicao())
                .altura(dto.getAltura())
                .responsavel(dto.getResponsavel())
                .ativo(dto.getAtivo())
                .build();

    }

}