package com.digytal.control.service.modulo.cadastro;

import com.digytal.control.infra.business.BusinessException;
import com.digytal.control.infra.business.ErroNaoMapeadoException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.definition.Definition;
import com.digytal.control.infra.commons.definition.Definitions;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.modulo.cadastro.produto.modelo.ModeloEntity;
import com.digytal.control.model.modulo.cadastro.produto.modelo.ModeloRequest;
import com.digytal.control.model.modulo.cadastro.produto.modelo.ModeloResponse;
import com.digytal.control.repository.modulo.acesso.OrganizacaoRepository;
import com.digytal.control.repository.modulo.cadastro.ModeloRepositoty;
import com.digytal.control.service.comum.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.*;

@Service
@Slf4j
public class ModeloService extends AbstractService {
    @Autowired
    private ModeloRepositoty repository;
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public Integer incluir( ModeloRequest request) {
        return gravar(null, request);
    }
    public Integer alterar(Integer id , ModeloRequest request){
        return gravar(id,request);

    }
    public List<ModeloResponse> listar(){
        return listar(null);
    }
    @Transactional
    private Integer gravar(Integer id , ModeloRequest request){
        try{
        request.setNomeAbreviado(Definition.seNuloOuVazio(request.getNomeAbreviado(), request.getNome(), 20));

        Validations.build(NOME).notEmpty().minLen(2).maxLen(30).check(request);
        Validations.build(NOME_ABREVIADO).notEmpty().minLen(2).maxLen(20).check(request);
        Validations.build(SIGLA).notEmpty().minLen(2).maxLen(6).check(request);

        ModeloEntity entity = new ModeloEntity();
        if(id==null){
            entity.setOrganizacao(requestInfo.getOrganizacao());
        }else{
            entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.MODELO_ENTITY, ID));
        }

        Definitions.build().upperNormalize(NOME,SIGLA).define(request);
        BeanUtils.copyProperties(request, entity);
        entity.setLocaliza(normalizar(request.getNome()));
        repository.save(entity);
        return entity.getId();
        }catch (
                BusinessException ex){
            log.warn(BusinessException.logMessage(ex));
            throw ex;
        }catch (Exception ex){
            log.error(BusinessException.errorMessage("Não foi possível incluir ou alterar o modelo [ %s ]", request.getNome()), ex);
            throw new ErroNaoMapeadoException();
        }

    }
    public List<ModeloResponse> listar(String nome) {
        nome = normalizar( Objects.toString(nome,""));
//        if (nome.isEmpty())
//            throw new ParametroInvalidoException("É necessário informar o nome correto para relaizar da consulta");

        List<ModeloEntity> list= repository.findByOrganizacaoAndLocalizaContaining(requestInfo.getOrganizacao(),nome);
        List<ModeloResponse> response = list.stream().map(i -> {
            ModeloResponse item = new ModeloResponse();
            BeanUtils.copyProperties(i, item);
            return item;
        }).collect(Collectors.toList());
        if(response.isEmpty()){
            throw new RegistroNaoLocalizadoException(Entities.MODELO_ENTITY,NOME);
        }
        return response;
    }

}
