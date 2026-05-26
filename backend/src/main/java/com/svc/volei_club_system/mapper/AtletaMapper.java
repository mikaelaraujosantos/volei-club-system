package com.svc.volei_club_system.mapper;

import java.time.LocalDate;
import java.time.Period;

import com.svc.volei_club_system.dto.*;
import com.svc.volei_club_system.model.AtletaModel;

public class AtletaMapper {

    // =========================
    // TO DTO
    // =========================

    public static AtletaDTO toDTO(
            AtletaModel atleta
    ) {

        Integer idade = null;

        // =========================
        // CALCULAR IDADE
        // =========================

        if (atleta.getDataNascimento() != null) {

            idade = Period.between(
                    atleta.getDataNascimento(),
                    LocalDate.now()
            ).getYears();

        }

        return AtletaDTO.builder()

                .id(atleta.getId())

                .nome(atleta.getNome())

                .dataNascimento(
                        atleta.getDataNascimento()
                )

                .idade(idade)

                .telefone(
                        atleta.getTelefone()
                )

                .posicao(
                        atleta.getPosicao()
                )

                .altura(
                        atleta.getAltura()
                )

                .responsavel(
                        atleta.getResponsavel()
                )

                .telefoneResponsavel(
                        atleta.getTelefoneResponsavel()
                )

                .perfilCompleto(
                        atleta.getPerfilCompleto()
                )

                .ativo(
                        atleta.getAtivo()
                )

                .build();

    }

    // =========================
    // CREATE DTO -> MODEL
    // =========================

    public static AtletaModel toModel(
            AtletaCreateDTO dto
    ) {

        return AtletaModel.builder()

                .nome(dto.getNome())

                .dataNascimento(
                        dto.getDataNascimento()
                )

                .telefone(
                        dto.getTelefone()
                )

                .posicao(
                        dto.getPosicao()
                )

                .altura(
                        dto.getAltura()
                )

                .responsavel(
                        dto.getResponsavel()
                )

                .telefoneResponsavel(
                        dto.getTelefoneResponsavel()
                )

                .perfilCompleto(
                        dto.getPerfilCompleto()
                )

                .ativo(
                        dto.getAtivo()
                )

                .build();

    }

    // =========================
    // UPDATE DTO -> MODEL
    // =========================

    public static AtletaModel toModel(
            AtletaUpdateDTO dto
    ) {

        return AtletaModel.builder()

                .nome(dto.getNome())

                .dataNascimento(
                        dto.getDataNascimento()
                )

                .telefone(
                        dto.getTelefone()
                )

                .posicao(
                        dto.getPosicao()
                )

                .altura(
                        dto.getAltura()
                )

                .responsavel(
                        dto.getResponsavel()
                )

                .telefoneResponsavel(
                        dto.getTelefoneResponsavel()
                )

                .perfilCompleto(
                        dto.getPerfilCompleto()
                )

                .ativo(
                        dto.getAtivo()
                )

                .build();

    }

}