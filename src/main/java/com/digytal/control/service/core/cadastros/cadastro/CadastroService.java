package com.digytal.control.service.core.cadastros.cadastro;

import com.digytal.control.infra.business.CpfCnpjInvalidoException;
import com.digytal.control.infra.business.RegistroDuplicadoException;
import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.definition.Text;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validation;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.infra.model.CredencialResponse;
import com.digytal.control.model.core.acessos.empresa.EmpresaEntity;
import com.digytal.control.model.core.cadastros.cadastro.CadastroEntity;
import com.digytal.control.model.core.cadastros.cadastro.CadastroResponse;
import com.digytal.control.model.core.cadastros.produto.ProdutoResponse;
import com.digytal.control.model.core.comum.RegistroCadastralEntity;
import com.digytal.control.model.core.comum.TipoLogin;
import com.digytal.control.model.core.comum.cadastratamento.CadastroRequest;
import com.digytal.control.model.core.comum.cadastratamento.CadastroSimplificadoRequest;
import com.digytal.control.model.core.comum.cadastratamento.EmpresaRequest;
import com.digytal.control.repository.cadastros.CadastroRepository;
import com.digytal.control.service.core.comum.CadastroFactory;
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
        Validations.build(ORGANIZACAO).notEmpty().check(request);

        if(!organizacaoRepository.existsById(request.getOrganizacao()))
            throw new RegistroNaoLocalizadoException(Entities.ORGANIZACAO_ENTITY, ID);

        String cpfCnpj = Text.onlyDigits(request.getCpfCnpj());

        if (!Validation.cpfCnpj(cpfCnpj))
            throw new CpfCnpjInvalidoException();

        RegistroCadastralEntity registroEntity = build(request);
        CadastroEntity entity = new CadastroEntity();

        if(id!=null) {
            entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.CADASTRO_ENTITY, ID));
            validarIntegridade(cpfCnpj,request.getEmail(),  entity);
        }else {
            if(repository.existsByCpfCnpjAndOrganizacao(request.getCpfCnpj(), request.getOrganizacao()))
                throw new RegistroDuplicadoException(CPF_CNPJ, cpfCnpj);

            if(repository.existsByEmailAndOrganizacao(request.getEmail(),request.getOrganizacao()))
                throw new RegistroDuplicadoException(EMAIL, request.getEmail());
        }
        BeanUtils.copyProperties(registroEntity, entity);
        BeanUtils.copyProperties(request.getPerfil(), entity.getPerfil());
        entity.setCpfCnpj(cpfCnpj);
        repository.save(entity);
        return entity.getId();
    }
    @Transactional
    public CredencialResponse configurarPrimeiroAcesso(CadastroSimplificadoRequest request){
        if(usuarioRepository.existsByLogin(request.getEmail()))
            throw new RegistroIncompativelException("Já existe um usuário com este e-mail");

        RegistroCadastralEntity registro = build(request);
        CadastroEntity entity = new CadastroEntity();
        BeanUtils.copyProperties(registro, entity);
        entity.getPerfil().setCliente(true);
        repository.save(entity);
        CredencialResponse response = cadastrarUsuario(entity, TipoLogin.EMAIL);
        return response;
    }

    public List<CadastroResponse> listar(Integer organizacao, String nome){
        nome = Objects.toString(nome,"").toUpperCase();
        List<CadastroResponse> response = repository.findByOrganizacaoAndNomeFantasiaContainingOrderByNomeFantasia(organizacao, nome).stream().map(i->{
            CadastroResponse item= new CadastroResponse();
            BeanUtils.copyProperties(i,item);
            return item;
        }).collect(Collectors.toList());
        return response;
    }


}
