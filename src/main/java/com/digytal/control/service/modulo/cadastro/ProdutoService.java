package com.digytal.control.service.modulo.cadastro;

import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.definition.Text;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.modulo.cadastro.produto.ProdutoEntity;
import com.digytal.control.model.modulo.cadastro.produto.ProdutoRequest;
import com.digytal.control.model.modulo.cadastro.produto.ProdutoResponse;
import com.digytal.control.repository.modulo.acesso.OrganizacaoRepository;
import com.digytal.control.repository.modulo.cadastro.ProdutoRepository;
import com.digytal.control.service.comum.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.*;

@Service
public class ProdutoService extends AbstractService {
    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    public Integer incluir(ProdutoRequest request) {
        return gravar(null, request);
    }

    public Integer alterar(Integer id, ProdutoRequest request) {
        return gravar(id, request);
    }

    @Transactional
    private Integer gravar(Integer id, ProdutoRequest request) {

        Validations.build(NOME, UNIDADE_MEDIDA, VALOR).notEmpty().check(request);
        Validations.build(NOME).minLen(2).maxLen(50).check(request);
//        Validations.build(UNIDADE_MEDIDA).minLen(1).maxLen(12).check(request);
        Validations.build(NOME_ABREVIADO).minLen(2).maxLen(25).check(request);
        Validations.build(CODIGO_BARRAS, SKU).maxLen(15).check(request);
        Validations.build(VALOR).notZero().check(request);

        ProdutoEntity entity = new ProdutoEntity();

        if (id == null) {
            entity.setOrganizacao(requestInfo.getOrganizacao());

        } else {
            entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.PRODUTO_ENTITY, ID));
        }
        entity.setSaldo(0.0);
        entity.setTaxaLiquidacao(request.getTaxaLiquidacao()==null ? 0.0: request.getTaxaLiquidacao());
        //Definitions.build().upperNormalize(NOME, CODIGO_BARRAS, SKU, NOME_ABREVIADO).define(request);
        entity.setSku(Objects.toString(request.getSku(), request.getCodigoBarras()));
        BeanUtils.copyProperties(request, entity);
        String nomeAbreviado = Objects.toString(request.getNomeAbreviado(), request.getNome());
        entity.setNomeAbreviado(Text.maxLength(nomeAbreviado, 20));

        repository.save(entity);
        return entity.getId();
    }
    public ProdutoEntity pesquisar(Integer id){
        return  repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.PRODUTO_ENTITY, ID));
    }
    public List<ProdutoResponse> listar(boolean comInternos, String nome) {
        nome = Objects.toString(nome, "").toUpperCase();
        List<ProdutoEntity> list = null;

        if (comInternos) {
            list = repository.findByOrganizacaoAndInternoAndNomeContainingOrderByNome(requestInfo.getOrganizacao(),true, nome);
        } else {
            list = repository.findByOrganizacaoAndInternoAndNomeContainingOrderByNome(requestInfo.getOrganizacao(), false, nome);
        }
        List<ProdutoResponse> response = list.stream().map(i -> {
            ProdutoResponse item = new ProdutoResponse();
            BeanUtils.copyProperties(i, item);
            return item;
        }).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new RegistroNaoLocalizadoException(Entities.PRODUTO_ENTITY, NOME);
        }
        return response;
    }

}
