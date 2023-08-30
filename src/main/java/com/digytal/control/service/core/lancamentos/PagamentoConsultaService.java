package com.digytal.control.service.core.lancamentos;

import com.digytal.control.infra.business.CampoObrigatorioException;
import com.digytal.control.infra.commons.validation.Validation;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.lancamentos.lancamento.response.FluxoCaixaResponse;
import com.digytal.control.model.core.lancamentos.lancamento.response.LancamentoResponse;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoResponse;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaResponse;
import com.digytal.control.repository.lancamentos.PagamentoRepository;
import com.digytal.control.repository.lancamentos.ParcelamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoConsultaService {
    @Autowired
    private ParcelamentoRepository parcelamentoRepository;

    @Autowired
    private PagamentoRepository lancamentoRepository;
    public List<LancamentoResponse> listarLancamentos(Integer empresa, LocalDate dataInicial, LocalDate dataFinal) {
        List<LancamentoResponse> response = lancamentoRepository.listBy(empresa, dataInicial, dataFinal);
        response.stream().forEach(i->
        {
            //i.setPlanoContaTipo(EnumUtils.find(TransacaoTipo.class, i.getTipo()));
            //i.setFormaPagamento(EnumUtils.find(FormaPagamento.class, i.getMeioPagamento()));
        });
        return response;
    }
    public List<ParcelaResponse> listarParcelas(Integer parcelamento) {
        return parcelamentoRepository.listarParcelas(parcelamento);

    }
    public List<FluxoCaixaResponse> listarFluxoCaixa(Integer empresa, LocalDate dataInicial, LocalDate dataFinal) {
        List<LancamentoResponse> list = listarLancamentos(empresa, dataInicial, dataFinal);
        List<FluxoCaixaResponse> response = list.stream().map(i -> {
            FluxoCaixaResponse item = new FluxoCaixaResponse();
            BeanUtils.copyProperties(i,item);
            Double valorOperacional= i.getValor().getValorOperacional();
            item.setValorReceita(item.getTipo() == LancamentoTipo.RECEITA ?valorOperacional:0.0);
            item.setValorDespesa(item.getTipo() == LancamentoTipo.DESPESA ?valorOperacional:0.0);
            return item;
        }).collect(Collectors.toList());
        return response;
    }
    public List<ParcelamentoResponse> listarParcelamentos(Integer empresa, LocalDate dataInicial, LocalDate dataFinal, LancamentoTipo tipo, Integer cadastro) {
        if(Validation.isEmpty(empresa))
            throw new CampoObrigatorioException("Empresa");

        if(Validation.isEmpty(dataInicial))
            throw new CampoObrigatorioException("Data Inicial");

        if(Validation.isEmpty(dataFinal))
            throw new CampoObrigatorioException("Data Final");

        List<ParcelamentoResponse> response = parcelamentoRepository.listBy(empresa, dataInicial, dataFinal,tipo,cadastro);
        response.stream().forEach(i->
        {
            //i.setPlanoContaTipo(EnumUtils.find(TransacaoTipo.class, i.getTipo()));
        });
        return response;
    }


}
