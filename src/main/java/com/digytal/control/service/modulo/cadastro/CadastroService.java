package com.digytal.control.service.modulo.cadastro;

import com.digytal.control.infra.business.CpfCnpjInvalidoException;
import com.digytal.control.infra.business.RegistroDuplicadoException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.definition.Text;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validation;
import com.digytal.control.model.modulo.cadastro.CadastroEntity;
import com.digytal.control.model.modulo.cadastro.CadastroResponse;
import com.digytal.control.model.comum.EntidadeCadastral;
import com.digytal.control.model.modulo.cadastro.CadastroRequest;
import com.digytal.control.repository.modulo.cadastro.CadastroRepository;
import com.digytal.control.service.comum.CadastroFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.*;
import static com.digytal.control.infra.commons.validation.Attributes.EMAIL;

@Service
public class CadastroService extends CadastroFactory {
    @Autowired
    private CadastroRepository repository;
    public Integer alterar(Integer id, CadastroRequest request){
        return gravar(id,request);
    }
    public Integer incluir(CadastroRequest request){
        return gravar(null,request);
    }
    @Transactional
    private Integer gravar(Integer id, CadastroRequest request){
        String cpfCnpj = Text.onlyDigits(request.getCpfCnpj());

        if (!Validation.cpfCnpj(cpfCnpj))
            throw new CpfCnpjInvalidoException();

        EntidadeCadastral registroEntity = build(request);
        CadastroEntity entity = new CadastroEntity();

        if(id!=null) {
            entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.CADASTRO_ENTITY, ID));
            registroEntity.setOrganizacao(entity.getOrganizacao());
            checarIntegridadeOrganizacional(registroEntity.getOrganizacao());
        }else {
            Integer organizacao = requestInfo.getOrganizacao();
            if(repository.existsByCpfCnpjAndOrganizacao(request.getCpfCnpj(), organizacao))
                throw new RegistroDuplicadoException(CPF_CNPJ, cpfCnpj);

            if(repository.existsByEmailAndOrganizacao(request.getEmail(),organizacao))
                throw new RegistroDuplicadoException(EMAIL, request.getEmail());

            registroEntity.setOrganizacao(organizacao);
        }
        BeanUtils.copyProperties(registroEntity, entity);
        BeanUtils.copyProperties(request.getPerfil(), entity.getPerfil());
        entity.setCpfCnpj(cpfCnpj);
        entity.setIncompleto(false);
        repository.save(entity);
        return entity.getId();
    }
    public List<CadastroResponse> listarClientes(String nome){
        return listar(true,false,nome);
    }
    public List<CadastroResponse> listarForncedores(String nome){
        return listar(false,true,nome);
    }
    public List<CadastroResponse> listar(boolean cliente, boolean fornecedor, String nome){
        nome = Objects.toString(nome,"").toUpperCase();
        List<CadastroResponse> response = repository.listar(requestInfo.getOrganizacao(),cliente,fornecedor, nome).stream().map(i->{
            CadastroResponse item= new CadastroResponse();
            BeanUtils.copyProperties(i,item);
            return item;
        }).collect(Collectors.toList());
        return response;
    }
}
