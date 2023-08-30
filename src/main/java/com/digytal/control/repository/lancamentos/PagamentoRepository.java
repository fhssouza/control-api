package com.digytal.control.repository.lancamentos;

import com.digytal.control.model.core.lancamentos.lancamento.PagamentoEntity;
import com.digytal.control.model.core.lancamentos.lancamento.response.LancamentoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Integer> {
    List<LancamentoResponse> listBy(Integer empresa, LocalDate diaInicial, LocalDate diaFinal);

}
