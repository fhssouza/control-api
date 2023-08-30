package com.digytal.control.service.contratos.gerencial;

import com.digytal.control.model.contrato.contratos.contrato.ContratoTipo;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.gerencial.ContratoPagamentoAnalitico;
import com.digytal.control.model.gerencial.venda.ContratoVenda;
import com.digytal.control.model.gerencial.venda.ContratoVendaPagamento;
import com.digytal.control.repository.contratos.ContratoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContratoGerencialService {
    @Autowired
    private ContratoRepository contratoRepository;
    public List<ContratoPagamentoAnalitico> listarContratosPagamentos(ContratoTipo tipo, Integer empresa, LocalDate data){
        return contratoRepository.listarContratosPagamentos(tipo, empresa, data);
    }
    public List<ContratoVenda> listarVendaPagamentos(Integer empresa, LocalDate data){
        List<ContratoPagamentoAnalitico> lista = contratoRepository.listarContratosPagamentos(ContratoTipo.VENDA, empresa, data);
        Map<Integer, ContratoVenda > map = new LinkedHashMap<>();
        for(ContratoPagamentoAnalitico item: lista){
            ContratoVenda venda = map.get(item.getId());
            if(venda==null){
                venda = criarVenda(item);
                map.put(item.getId(),venda);
            }
            venda.getPagamentos().add(criarPagamento(item));
        }
        List<ContratoVenda> vendas = new ArrayList<>(map.values());
        definirValorPagamento(vendas);
        return vendas ;
    }
    private void definirValorPagamento(List<ContratoVenda> vendas){
        for(ContratoVenda v: vendas){
            v.getPagamento().setDinheiro(v.getPagamentos().stream()
                    .filter(i->i.getMeioPagamento().getId().equals(MeioPagamento.DINHEIRO.getId()))
                    .mapToDouble(ContratoVendaPagamento::getValorPago)
                    .sum());

            v.getPagamento().setPix(v.getPagamentos().stream()
                    .filter(i->i.getMeioPagamento().getId().equals(MeioPagamento.PIX.getId()))
                    .mapToDouble(ContratoVendaPagamento::getValorPago)
                    .sum());

            v.getPagamento().setDebito(v.getPagamentos().stream()
                    .filter(i->i.getMeioPagamento().getId().equals(MeioPagamento.DEBITO.getId()))
                    .mapToDouble(ContratoVendaPagamento::getValorPago)
                    .sum());

            v.getPagamento().setBoleto(v.getPagamentos().stream()
                    .filter(i->i.getMeioPagamento().getId().equals(MeioPagamento.BOLETO.getId()))
                    .mapToDouble(ContratoVendaPagamento::getValorPago)
                    .sum());

            v.getPagamento().setParcelado(v.getPagamentos().stream()
                    .filter(i->i.getMeioPagamento().getId().equals(MeioPagamento.PARCELADO.getId()))
                    .mapToDouble(ContratoVendaPagamento::getValorPago)
                    .sum());

            v.getPagamento().setCredito(v.getPagamentos().stream()
                    .filter(i->i.getMeioPagamento().getId().equals(MeioPagamento.CREDITO.getId()))
                    .mapToDouble(ContratoVendaPagamento::getValorPago)
                    .sum());
        }
    }
    private ContratoVenda criarVenda(ContratoPagamentoAnalitico item){
        ContratoVenda elemento = new ContratoVenda();
        BeanUtils.copyProperties(item,elemento);
        return elemento;
    }
    private ContratoVendaPagamento criarPagamento(ContratoPagamentoAnalitico item){
        ContratoVendaPagamento elemento = new ContratoVendaPagamento();
        BeanUtils.copyProperties(item,elemento);
        return elemento;
    }
}
