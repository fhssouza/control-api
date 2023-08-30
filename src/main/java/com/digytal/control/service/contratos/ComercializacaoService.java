package com.digytal.control.service.contratos;

import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.model.contrato.contratos.comercializacao.ComercializacaoRequest;
import com.digytal.control.model.contrato.contratos.contrato.ContratoEntity;
import com.digytal.control.model.contrato.contratos.contrato.ContratoResponse;
import com.digytal.control.model.contrato.contratos.contrato.ContratoSituacao;
import com.digytal.control.model.contrato.contratos.contrato.ContratoTipo;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.ID;

@Service
public class ComercializacaoService extends ContratoService {
    @Transactional
    public Integer confirmarVendaDireta(ComercializacaoRequest request){
        return  confirmarVenda(request,ContratoSituacao.CONCLUIDO);
    }
    @Transactional
    public Integer confirmarVenda(ComercializacaoRequest request,ContratoSituacao situacao){
        request.setDescricao("Contrato de Venda\\Serviço");
        return  confirmar(request,ContratoTipo.VENDA, situacao, LancamentoTipo.RECEITA);
    }

    private Integer confirmar(ComercializacaoRequest request, ContratoTipo tipo, ContratoSituacao situacao, LancamentoTipo transacaoTipo){
        ContratoEntity entity = new ContratoEntity();
        entity.setData(RegistroData.of(request.getData(), request.getHora()));
        entity.setTipo(tipo);
        entity.setDescricao(request.getDescricao());
        entity.setSituacao(situacao);
        entity.setIntermediador(request.getIntermediador()==null?request.getPartes().getUsuario():request.getIntermediador());
        usuarioRepository.findById(entity.getIntermediador()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.VENDEDOR_ENTITY, ID));
        entity.setItens(itens(request.getItens()));
        entity.setPagamentos(pagamentos(calcularValorAplicado(entity), request.getPagamentos()));

        persist(request.getPartes(), request.getIntermediador(), entity, transacaoTipo);
        return entity.getId();
    }

    public List<ContratoResponse> listar(Integer empresa, LocalDate data){
        List<ContratoEntity> list = contratoRepository.filtrar(empresa, data);
        List<ContratoResponse> response = list.stream().map(i -> {
            ContratoResponse item = new ContratoResponse();
            BeanUtils.copyProperties(i, item);
            BeanUtils.copyProperties(i.getData(), item.getData());
            BeanUtils.copyProperties(i.getPartes(), item.getPartes());
            return item;
        }).collect(Collectors.toList());
        return response;
    }

}
