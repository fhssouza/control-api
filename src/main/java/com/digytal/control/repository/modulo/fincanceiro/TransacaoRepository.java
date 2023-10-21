package com.digytal.control.repository.modulo.fincanceiro;

import com.digytal.control.model.modulo.financeiro.transacao.TransacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<TransacaoEntity, Integer> {
}
