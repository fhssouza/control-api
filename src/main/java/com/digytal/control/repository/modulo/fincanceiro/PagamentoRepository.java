package com.digytal.control.repository.modulo.fincanceiro;

import com.digytal.control.model.consulta.lancamentos.PagamentoFiltro;
import com.digytal.control.model.modulo.financeiro.pagamento.PagamentoEntity;
import com.digytal.control.model.modulo.financeiro.pagamento.response.PagamentoResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Integer> {
    List<PagamentoResponse> listarPagamentos(Integer empresa, PagamentoFiltro filtro);
    List<PagamentoResponse> listarPagamentosCompleto(Integer empresa, PagamentoFiltro filtro);
    //List<PagamentoResponse> listarPagamentosPorTipoNatureza(Integer empresa, LocalDate diaInicial, LocalDate diaFinal);
}
