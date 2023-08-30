package com.digytal.control.repository.lancamentos;


import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ParcelaRepository extends JpaRepository<ParcelaEntity, Integer> {
    @Query("SELECT e FROM ParcelaEntity e WHERE e.boleto.solicitado=true AND e.boleto.status='E' and e.negociacao.dataVencimento <= :dataVencimento ")
    List<ParcelaEntity> listarParcelasPendentesPagamento(@Param("dataVencimento") LocalDate dataVencimento);
}
