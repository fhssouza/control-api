package com.digytal.control.repository.lancamentos;

import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoEntity;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoResponse;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ParcelamentoRepository extends JpaRepository<ParcelamentoEntity, Integer> {
   List<ParcelamentoResponse> listBy(Integer empresa, LocalDate diaInicial, LocalDate diaFinal, LancamentoTipo tipo, Integer cadastro);
   List<ParcelaResponse> listarParcelas(Integer parcelamento);
}
