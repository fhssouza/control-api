package com.digytal.control.repository.lancamentos;

import com.digytal.control.model.core.lancamentos.parcelamento.liquidacao.ParcelaPagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaPagamentoRepository extends JpaRepository<ParcelaPagamentoEntity, Integer> {

}
