package com.svc.volei_club_system.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;



@Entity
@Table(name = "atleta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtletaModel {
    

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String nome;

private Integer idade;

private String telefone;

private String posicao;

private Double altura;

private String responsavel;

private Boolean ativo;

@ManyToOne
@JoinColumn(name = "usuario_id")
@JsonIgnore
private UsuarioModel usuario;

    
}
