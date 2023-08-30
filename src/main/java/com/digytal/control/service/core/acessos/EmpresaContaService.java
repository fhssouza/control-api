package com.digytal.control.service.core.acessos;

import com.digytal.control.infra.business.*;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.core.acessos.empresa.conta.*;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoRequest;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoResponse;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.repository.acessos.EmpresaContaMeioPagamentoRepository;
import com.digytal.control.repository.acessos.EmpresaContaRepository;
import com.digytal.control.service.core.comum.CadastroFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.*;
import static com.digytal.control.infra.commons.validation.Entities.CONTA_FORMA_PAGAMENTO_ENTITY;
import static com.digytal.control.infra.commons.validation.Entities.EMPRESA_CONTA_ENTITY;

@Service
public class EmpresaContaService extends CadastroFactory {
    @Autowired
    private EmpresaContaRepository contaRepository;
    @Autowired
    private EmpresaContaMeioPagamentoRepository empresaContaFormaPagamentoRepository;

    @Transactional
    public Integer incluir(Integer conta, EmpresaContaMeioPagamentoRequest request){
        Validations.build(CONTA, FORMA_PAGAMENTO).check(request);

        EmpresaContaEntity contaEntity = contaRepository.findById(conta).orElseThrow(() -> new RegistroNaoLocalizadoException(EMPRESA_CONTA_ENTITY, ID));

        if(empresaContaFormaPagamentoRepository.existsByEmpresaAndMeioPagamento(contaEntity.getEmpresa(), request.getMeioPagamento()))
            throw new RegistroDuplicadoException(FORMA_PAGAMENTO, request.getMeioPagamento().getDescricao());

        EmpresaContaMeioPagamentoEntity entity = new EmpresaContaMeioPagamentoEntity();
        entity.setConta(contaEntity.getId());
        entity.setEmpresa(contaEntity.getEmpresa());
        entity.setTaxa(request.getTaxa()==null?0.0: request.getTaxa());
        entity.setMeioPagamento(request.getMeioPagamento());
        empresaContaFormaPagamentoRepository.save(entity);
        return entity.getId();
    }
    @Transactional
    public boolean alterarTaxa(Integer id, Double taxa){
        EmpresaContaMeioPagamentoEntity entity = empresaContaFormaPagamentoRepository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(CONTA_FORMA_PAGAMENTO_ENTITY, ID));
        entity.setTaxa(taxa);
        empresaContaFormaPagamentoRepository.save(entity);
        return true;
    }
    @Transactional
    public boolean excluir(Integer id){
        EmpresaContaMeioPagamentoEntity entity = empresaContaFormaPagamentoRepository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(CONTA_FORMA_PAGAMENTO_ENTITY, ID));
        empresaContaFormaPagamentoRepository.deleteById(id);
        return true;
    }

    public List<EmpresaContaMeioPagamentoResponse> listarContaFormasPagamento(Integer conta){
        List<EmpresaContaMeioPagamentoEntity> list = empresaContaFormaPagamentoRepository.findByConta(conta);
        List<EmpresaContaMeioPagamentoResponse> response = list.stream().map(i -> {
            EmpresaContaMeioPagamentoResponse item = new EmpresaContaMeioPagamentoResponse();
            BeanUtils.copyProperties(i, item);
            return item;
        }).collect(Collectors.toList());
        return response;
    }

    public ContaPagamentoResponse verificarContaFormaPagamento(Integer empresa){
        List<EmpresaContaMeioPagamentoEntity> list = empresaContaFormaPagamentoRepository.findByEmpresa(empresa);
        ContaPagamentoResponse response = new ContaPagamentoResponse();

        response.setDinheiro(list.stream().filter(i-> i.getMeioPagamento() == MeioPagamento.DINHEIRO).count()>0);
        response.setBoleto(list.stream().filter(i-> i.getMeioPagamento() == MeioPagamento.BOLETO).count()>0);
        response.setParcelado(list.stream().filter(i-> i.getMeioPagamento() == MeioPagamento.PARCELADO).count()>0);
        response.setCredito(list.stream().filter(i-> i.getMeioPagamento() == MeioPagamento.CREDITO).count()>0);
        response.setDebito(list.stream().filter(i-> i.getMeioPagamento() == MeioPagamento.DEBITO).count()>0);
        response.setPix(list.stream().filter(i-> i.getMeioPagamento() == MeioPagamento.PIX).count()>0);

        return response;

    }
}
