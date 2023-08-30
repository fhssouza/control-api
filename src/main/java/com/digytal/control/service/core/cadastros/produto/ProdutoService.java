package com.digytal.control.service.core.cadastros.produto;

import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import com.digytal.control.model.core.cadastros.produto.ProdutoRequest;
import com.digytal.control.model.core.cadastros.produto.ProdutoResponse;
import com.digytal.control.model.core.params.banco.BancoResponse;
import com.digytal.control.repository.cadastros.OrganizacaoRepository;
import com.digytal.control.repository.estoque.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.*;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public List<ProdutoEntity> listar(Integer organizacao){
        return repository.findByOrganizacao(organizacao);
    }

    public Integer incluir(ProdutoRequest request){
        return gravar(null, request);
    }
    public Integer alterar(Integer id , ProdutoRequest request){
        return gravar(id, request);
    }
    @Transactional
    private Integer gravar(Integer id , ProdutoRequest request){
        Validations.build(NOME,UNIDADE_MEDIDA, VALOR, ORGANIZACAO).notEmpty().check(request);
        Validations.build(NOME).maxLen(50).check(request);
        Validations.build(UNIDADE_MEDIDA).maxLen(5).check(request);
        Validations.build(CODIGO_BARRAS).maxLen(15).check(request);
        if(!organizacaoRepository.existsById(request.getOrganizacao()))
            throw new RegistroNaoLocalizadoException(Entities.ORGANIZACAO_ENTITY, ID);

        ProdutoEntity entity =null;

        if(id!=null) {
            entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.PRODUTO_ENTITY, ID));
            if(!entity.getOrganizacao().equals(request.getOrganizacao()))
                throw new RegistroIncompativelException("Não é permitido alterar a organização do produto");
        }else {
            entity = new ProdutoEntity();
            entity.setSaldo(0.0);
        }
        BeanUtils.copyProperties(request,entity);
        entity.setSku(Objects.toString(request.getSku(), request.getCodigoBarras()));
        repository.save(entity);
        return entity.getId();
    }

    public List<ProdutoResponse> listar(Integer organizacao, String nome){
        nome = Objects.toString(nome,"").toUpperCase();
        List<ProdutoResponse> response = repository.findByOrganizacaoAndNomeContainingOrderByNome(organizacao, nome).stream().map(i->{
            ProdutoResponse item= new ProdutoResponse();
            BeanUtils.copyProperties(i,item);
            return item;
        }).collect(Collectors.toList());
        return response;
    }
}
