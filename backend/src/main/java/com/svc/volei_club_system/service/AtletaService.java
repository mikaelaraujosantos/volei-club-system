package com.svc.volei_club_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.svc.volei_club_system.dto.*;
import com.svc.volei_club_system.enums.Role;
import com.svc.volei_club_system.exception.ResourceNotFoundException;
import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.repository.AtletaRepository;
import com.svc.volei_club_system.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.Period;
@Service
public class AtletaService {

    @Autowired
    private AtletaRepository atletaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // =========================
    // BUSCAR USUÁRIO
    // =========================

    private UsuarioModel buscarUsuario(String emailUsuario) {

        return usuarioRepository
                .findByEmail(emailUsuario)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado"
                        )
                );

    }

    // =========================
    // CADASTRAR
    // =========================

    public AtletaModel cadastrarAtleta(
            AtletaModel atleta,
            String emailUsuario
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        atleta.setUsuario(usuario);

        return atletaRepository.save(atleta);

    }

    // =========================
    // LISTAR
    // =========================

    public Page<AtletaModel> listarAtletas(
            String emailUsuario,
            Pageable pageable
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        if (usuario.getRole() == Role.ADMIN) {

            return atletaRepository.findAll(pageable);

        }

        return atletaRepository
                .findByUsuario(usuario, pageable);

    }

    // =========================
    // BUSCAR POR ID
    // =========================

    public AtletaModel buscarPorId(
            Long id,
            String emailUsuario
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        if (usuario.getRole() == Role.ADMIN) {

            return atletaRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        }

        return atletaRepository
                .findByIdAndUsuario(id, usuario)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Atleta não encontrado"
                        )
                );

    }

    // =========================
    // ATUALIZAR
    // =========================

    public AtletaModel atualizarAtleta(
            Long id,
            AtletaModel dados,
            String emailUsuario
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        AtletaModel atleta;

        if (usuario.getRole() == Role.ADMIN) {

            atleta = atletaRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        } else {

            atleta = atletaRepository
                    .findByIdAndUsuario(id, usuario)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        }

        atleta.setNome(dados.getNome());

        atleta.setDataNascimento(
                dados.getDataNascimento()
        );

        atleta.setTelefone(dados.getTelefone());
        atleta.setPosicao(dados.getPosicao());
        atleta.setAltura(dados.getAltura());
        atleta.setResponsavel(dados.getResponsavel());
        atleta.setAtivo(dados.getAtivo());

        return atletaRepository.save(atleta);

    }

    // =========================
    // DELETAR
    // =========================

    public void deletarAtleta(
            Long id,
            String emailUsuario
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        AtletaModel atleta;

        if (usuario.getRole() == Role.ADMIN) {

            atleta = atletaRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        } else {

            atleta = atletaRepository
                    .findByIdAndUsuario(id, usuario)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        }

        atleta.setAtivo(false);

// INATIVAR USUÁRIO TAMBÉM

UsuarioModel usuarioAtleta =
        atleta.getUsuario();

usuarioAtleta.setAtivo(false);

usuarioRepository.save(usuarioAtleta);

atletaRepository.save(atleta);

    }

    // =========================
    // ATUALIZAR PARCIAL
    // =========================

    public AtletaModel atualizarParcial(
            Long id,
            AtletaModel dados,
            String emailUsuario
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        AtletaModel atleta;

        if (usuario.getRole() == Role.ADMIN) {

            atleta = atletaRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        } else {

            atleta = atletaRepository
                    .findByIdAndUsuario(id, usuario)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        }

        if (dados.getNome() != null) {
            atleta.setNome(dados.getNome());
        }

        if (dados.getDataNascimento() != null) {
            atleta.setDataNascimento(
                    dados.getDataNascimento()
            );
        }

        if (dados.getTelefone() != null) {
            atleta.setTelefone(dados.getTelefone());
        }

        if (dados.getPosicao() != null) {
            atleta.setPosicao(dados.getPosicao());
        }

        if (dados.getResponsavel() != null) {
            atleta.setResponsavel(dados.getResponsavel());
        }

        if (dados.getAltura() != null) {
            atleta.setAltura(dados.getAltura());
        }

        if (dados.getAtivo() != null) {
            atleta.setAtivo(dados.getAtivo());
        }

        return atletaRepository.save(atleta);

    }

    // =========================
    // BUSCAR POR USUÁRIO ID
    // =========================

    public AtletaModel buscarPorUsuarioId(Long usuarioId) {

        return atletaRepository
                .findByUsuarioId(usuarioId)
                .orElse(null);

    }

    public AtletaModel completarPerfil(
        CompletarPerfilDTO dto,
        String emailUsuario
) {

    UsuarioModel usuario =
            buscarUsuario(emailUsuario);

    AtletaModel atleta =
            atletaRepository
                    .findByUsuario(usuario)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

    // =========================
    // VALIDAR DATA
    // =========================

    if (dto.getDataNascimento() == null) {

        throw new RuntimeException(
                "Data de nascimento obrigatória"
        );

    }

    // =========================
    // CALCULAR IDADE
    // =========================

    int idade = Period.between(
            dto.getDataNascimento(),
            LocalDate.now()
    ).getYears();

    boolean menor = idade < 18;

    // =========================
    // VALIDAR RESPONSÁVEL
    // =========================

    if (menor) {

        if (
                dto.getResponsavel() == null
                        || dto.getResponsavel().isBlank()
        ) {

            throw new RuntimeException(
                    "Responsável obrigatório para menores"
            );

        }

        if (
                dto.getTelefoneResponsavel() == null
                        || dto.getTelefoneResponsavel().isBlank()
        ) {

            throw new RuntimeException(
                    "Telefone do responsável obrigatório"
            );

        }

    }

    // =========================
    // SALVAR DADOS
    // =========================

    atleta.setDataNascimento(
            dto.getDataNascimento()
    );

    atleta.setPosicao(
            dto.getPosicao()
    );

    atleta.setAltura(
            dto.getAltura()
    );

    atleta.setResponsavel(
            dto.getResponsavel()
    );

    atleta.setTelefoneResponsavel(
            dto.getTelefoneResponsavel()
    );

    atleta.setPerfilCompleto(true);

    return atletaRepository.save(atleta);

}

        // =========================
// REATIVAR ATLETA
// =========================

public void ativarAtleta(
        Long id,
        String emailUsuario
) {

    UsuarioModel usuario =
            buscarUsuario(emailUsuario);

    if (usuario.getRole() != Role.ADMIN) {

        throw new RuntimeException(
                "Apenas admin pode ativar atleta"
        );

    }

    AtletaModel atleta =
            atletaRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

    atleta.setAtivo(true);

    UsuarioModel usuarioAtleta =
            atleta.getUsuario();

    usuarioAtleta.setAtivo(true);

    usuarioRepository.save(usuarioAtleta);

    atletaRepository.save(atleta);

}

}