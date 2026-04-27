package com.svc.volei_club_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "mensalidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensalidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    @Column(nullable = false)
    private Double valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMensalidade status;

    @Column(length = 255)
    private String observacao;

    private Integer mesReferencia;
    private Integer anoReferencia;

    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false)
    private AtletaModel atleta;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = StatusMensalidade.PENDENTE;
        }
        if (mesReferencia == null && dataVencimento != null) {
            mesReferencia = dataVencimento.getMonthValue();
            anoReferencia = dataVencimento.getYear();
        }
    }
}