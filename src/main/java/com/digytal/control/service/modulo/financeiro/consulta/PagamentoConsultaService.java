package com.digytal.control.service.modulo.financeiro.consulta;


import com.digytal.control.infra.business.ParametroInvalidoException;
import com.digytal.control.infra.utils.Calculos;
import com.digytal.control.model.consulta.lancamentos.PagamentoFiltro;
import com.digytal.control.model.modulo.acesso.empresa.aplicacao.AplicacaoTipo;
import com.digytal.control.model.modulo.financeiro.pagamento.response.PagamentoResponse;
import com.digytal.control.model.modulo.financeiro.pagamento.response.PagamentoResumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digytal.control.repository.modulo.fincanceiro.PagamentoRepository;
import com.digytal.control.service.comum.AbstractService;
import com.digytal.control.service.modulo.acesso.ContaService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoConsultaService extends AbstractService {
    @Autowired
    private PagamentoRepository repository;
    
    @Autowired
    private ContaService contaService;
    public List<PagamentoResponse> listarPagamentos(PagamentoFiltro filtro){
        validarPeriodoData(filtro.getDataInicial(), filtro.getDataFinal());

        return repository.listarPagamentos(requestInfo.getEmpresa(), filtro);
    }
    public List<PagamentoResponse> listarPagamentosCompleto(PagamentoFiltro filtro){
        validarPeriodoData(filtro.getDataInicial(), filtro.getDataFinal());
        return repository.listarPagamentosCompleto(requestInfo.getEmpresa(), filtro);
    }
    private void validarPeriodoData(LocalDate dataInicial, LocalDate dataFinal){
        if(ChronoUnit.DAYS.between(dataInicial, dataFinal) > 31)
            throw new ParametroInvalidoException("O intervalo de datas ultrapassou os 31 dias");
    }
    public PagamentoResumo resumirPagamentos(PagamentoFiltro filtro){
        List<PagamentoResponse> pagamentos = listarPagamentosCompleto(filtro);
        List<PagamentoResponse> receitas = pagamentos.stream().filter(p-> AplicacaoTipo.RECEITA == p.getTipo()).collect(Collectors.toList());
        List<PagamentoResponse> despesas = pagamentos.stream().filter(p-> AplicacaoTipo.DESPESA == p.getTipo()).collect(Collectors.toList());

        PagamentoResumo resumo = new PagamentoResumo();
        resumo.setPagamentos(pagamentos);
        resumo.setReceitas(receitas);
        resumo.setDespesas(despesas);
        resumo.setTotalReceitas(receitas.stream().mapToDouble(p->p.getValor().getValorInformado()).sum());
        resumo.setTotalDespesas(despesas.stream().mapToDouble(p->p.getValor().getValorInformado()).sum());
        resumo.setSaldo(Calculos.subtrair(resumo.getTotalReceitas(), resumo.getTotalDespesas()));
        return resumo;
    }
}
