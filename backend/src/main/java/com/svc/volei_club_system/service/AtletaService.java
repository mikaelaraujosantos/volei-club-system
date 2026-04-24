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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;



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
                                "Atleta não encontrado"
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
            String emailUsuario, Pageable pageable
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

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

        AtletaModel atleta =
                atletaRepository
                .findByIdAndUsuario(id, usuario)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Atleta não encontrado"
                        )
                );

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
    // DELETAR
    // =========================

    public void deletarAtleta(
            Long id,
            String emailUsuario
    ) {

        UsuarioModel usuario =
                buscarUsuario(emailUsuario);

        AtletaModel atleta =
                atletaRepository
                .findByIdAndUsuario(id, usuario)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Atleta não encontrado"
                        )
                );

        atletaRepository.delete(atleta);

    }
    public AtletaModel atualizarParcial(
        Long id,
        AtletaModel dados,
        String emailUsuario
) {

    UsuarioModel usuario =
            buscarUsuario(emailUsuario);

    AtletaModel atleta =
            atletaRepository
            .findByIdAndUsuario(id, usuario)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Atleta não encontrado"
                    )
            );

    // Atualiza apenas se vier valor

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

    // idade é int (não aceita null)
    if (dados.getIdade() != null) {
        atleta.setIdade(dados.getIdade());
    }

    return atletaRepository.save(atleta);

}

}