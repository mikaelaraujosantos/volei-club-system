package com.svc.volei_club_system.repository;

import com.svc.volei_club_system.model.MensalidadeModel;
import com.svc.volei_club_system.model.StatusMensalidade;
import com.svc.volei_club_system.model.AtletaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface MensalidadeRepository extends JpaRepository<MensalidadeModel, Long> {
    
    List<MensalidadeModel> findByAtletaId(Long atletaId);
    
    List<MensalidadeModel> findByStatus(StatusMensalidade status);
    
    List<MensalidadeModel> findByAtletaAndStatus(AtletaModel atleta, StatusMensalidade status);
    
    List<MensalidadeModel> findByDataVencimentoBeforeAndStatusNot(LocalDate data, StatusMensalidade status);
    
    @Query("SELECT m FROM MensalidadeModel m WHERE m.atleta.id = :atletaId AND m.mesReferencia = :mes AND m.anoReferencia = :ano")
    MensalidadeModel findByAtletaIdAndMesAno(@Param("atletaId") Long atletaId, @Param("mes") Integer mes, @Param("ano") Integer ano);
    
    List<MensalidadeModel> findByAtletaIdAndStatusOrderByDataVencimentoDesc(Long atletaId, StatusMensalidade status);
    void deleteByAtletaId(Long atletaId);
}