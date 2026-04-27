package com.svc.volei_club_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.svc.volei_club_system.model.AtletaModel;
import com.svc.volei_club_system.model.UsuarioModel;
import com.svc.volei_club_system.repository.AtletaRepository;
import com.svc.volei_club_system.repository.UsuarioRepository;
import com.svc.volei_club_system.exception.ResourceNotFoundException;
import com.svc.volei_club_system.enums.Role;

import org.springframework.data.domain.Pageable;

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
    // LISTAR (COM ADMIN)
    // =========================

    public Page<AtletaModel> listarAtletas(
            String emailUsuario,
            Pageable pageable
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        // ADMIN vê todos

        if (usuario.getRole() == Role.ADMIN) {

            return atletaRepository.findAll(pageable);

        }

        // USER vê só os seus

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

        // ADMIN pode acessar qualquer atleta

        if (usuario.getRole() == Role.ADMIN) {

            return atletaRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Atleta não encontrado"
                            )
                    );

        }

        // USER só os próprios

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
        atleta.setIdade(dados.getIdade());
        atleta.setTelefone(dados.getTelefone());
        atleta.setPosicao(dados.getPosicao());
        atleta.setAltura(dados.getAltura());
        atleta.setResponsavel(dados.getResponsavel());
        atleta.setAtivo(dados.getAtivo());

        return atletaRepository.save(atleta);

    }

    // =========================
    // DELETAR (DESATIVAR)
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

        // Em vez de deletar, apenas desativa o atleta
        atleta.setAtivo(false);
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

        if (dados.getIdade() != null) {
            atleta.setIdade(dados.getIdade());
        }

        return atletaRepository.save(atleta);

    }

    // =========================
    // BUSCAR ATLETA POR USUÁRIO ID
    // =========================

    public AtletaModel buscarPorUsuarioId(Long usuarioId) {
        return atletaRepository.findByUsuarioId(usuarioId)
                .orElse(null);
    }
}