package com.digytal.control.service.modulo.cadastro;

import com.digytal.control.infra.business.*;
import com.digytal.control.infra.commons.definition.Definition;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.modulo.cadastro.produto.unidademedida.UnidadeMedidaEntity;
import com.digytal.control.model.modulo.cadastro.produto.unidademedida.UnidadeMedidaRequest;
import com.digytal.control.model.modulo.cadastro.produto.unidademedida.UnidadeMedidaResponse;
import com.digytal.control.repository.modulo.cadastro.UnidadeMedidaRepository;
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
public class UnidadeMedidaService extends AbstractService {
    @Autowired
    private UnidadeMedidaRepository repository;
    public List<UnidadeMedidaResponse> listar() {
        return listar(null);
    }
    public List<UnidadeMedidaResponse> listar(String nome) {
        nome = normalizar( Objects.toString(nome,""));
        List<UnidadeMedidaEntity> list= repository.findByOrganizacaoAndLocalizaContaining(requestInfo.getOrganizacao(),nome);
        List<UnidadeMedidaResponse> response = list.stream().map(i->{
            UnidadeMedidaResponse item= new UnidadeMedidaResponse();
            BeanUtils.copyProperties(i,item);
            return item;
        }).collect(Collectors.toList());
        if(response.isEmpty()){
            throw new ConsultaSemRegistrosException();
        }
        return response;
    }
    public Integer incluir( UnidadeMedidaRequest request) {
        return gravar(null,request);
    }
    public Integer alterar(Integer id, UnidadeMedidaRequest request) {
        return gravar(id,request);
    }

    @Transactional
    private Integer gravar(Integer id, UnidadeMedidaRequest request) {
        try {
            request.setDescricao(Definition.seNuloOuVazio(request.getDescricao(), request.getNome(),25));

            Validations.build(SIGLA).notEmpty().maxLen(6).check(request);
            Validations.build(NOME).notEmpty().minLen(2).maxLen(25).check(request);
            Validations.build(DESCRICAO).minLen(10).maxLen(80).check(request);

            UnidadeMedidaEntity entity = new UnidadeMedidaEntity();

            if(id==null){
                entity.setOrganizacao(requestInfo.getOrganizacao());
            }else{
                entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.UNIDADE_MEDIDA_ENTITY, ID));
            }

            BeanUtils.copyProperties(request, entity);
            entity.setConteudo(Objects.requireNonNullElse(request.getConteudo(), 1.0));
            entity.setLocaliza(normalizar(request.getNome()));
            repository.save(entity);
            return entity.getId();
        }catch (BusinessException ex){
            log.warn(BusinessException.logMessage(ex));
            throw ex;
        }catch (Exception ex){
            log.error(BusinessException.errorMessage("Não foi possível incluir ou alterar a unidade de medida [ %s ]", request.getNome()), ex);
            throw new ErroNaoMapeadoException();
        }
    }
}
