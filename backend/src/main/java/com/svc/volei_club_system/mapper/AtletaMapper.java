package com.svc.volei_club_system.mapper;

import com.svc.volei_club_system.dto.*;
import com.svc.volei_club_system.model.AtletaModel;

public class AtletaMapper {

    public static AtletaDTO toDTO(
            AtletaModel atleta
    ) {

        return AtletaDTO.builder()
                .id(atleta.getId())
                .nome(atleta.getNome())
                .dataNascimento(atleta.getDataNascimento())
                .telefone(atleta.getTelefone())
                .posicao(atleta.getPosicao())
                .altura(atleta.getAltura())
                .responsavel(atleta.getResponsavel())

                .telefoneResponsavel(
                        atleta.getTelefoneResponsavel()
                )

                .perfilCompleto(
                        atleta.getPerfilCompleto()
                )

                .ativo(atleta.getAtivo())
                .build();

    }

    public static AtletaModel toModel(
            AtletaCreateDTO dto
    ) {

        return AtletaModel.builder()
                .nome(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .telefone(dto.getTelefone())
                .posicao(dto.getPosicao())
                .altura(dto.getAltura())
                .responsavel(dto.getResponsavel())

                .telefoneResponsavel(
                        dto.getTelefoneResponsavel()
                )

                .perfilCompleto(
                        dto.getPerfilCompleto()
                )

                .ativo(dto.getAtivo())
                .build();

    }

    public static AtletaModel toModel(
            AtletaUpdateDTO dto
    ) {

        return AtletaModel.builder()
                .nome(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .telefone(dto.getTelefone())
                .posicao(dto.getPosicao())
                .altura(dto.getAltura())
                .responsavel(dto.getResponsavel())

                .telefoneResponsavel(
                        dto.getTelefoneResponsavel()
                )

                .perfilCompleto(
                        dto.getPerfilCompleto()
                )

                .ativo(dto.getAtivo())
                .build();

    }

}