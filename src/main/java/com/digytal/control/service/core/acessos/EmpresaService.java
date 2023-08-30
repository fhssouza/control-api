package com.digytal.control.service.core.acessos;

import com.digytal.control.infra.business.*;
import com.digytal.control.infra.commons.definition.Text;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validation;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.infra.model.CredencialResponse;
import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.acessos.empresa.EmpresaEntity;
import com.digytal.control.model.core.acessos.empresa.EmpresaResponse;
import com.digytal.control.model.core.acessos.empresa.conta.*;
import com.digytal.control.model.core.acessos.organizacao.OrganizacaoEntity;
import com.digytal.control.model.core.comum.RegistroCadastralEntity;
import com.digytal.control.model.core.comum.TipoLogin;
import com.digytal.control.model.core.comum.cadastratamento.CadastroSimplificadoRequest;
import com.digytal.control.model.core.comum.cadastratamento.EmpresaRequest;
import com.digytal.control.repository.acessos.EmpresaContaMeioPagamentoRepository;
import com.digytal.control.repository.acessos.EmpresaContaRepository;
import com.digytal.control.repository.acessos.EmpresaRepository;
import com.digytal.control.service.core.comum.CadastroFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.digytal.control.infra.commons.validation.Attributes.*;

@Service
public class EmpresaService extends CadastroFactory {
    @Autowired
    private EmpresaRepository repository;
    @Autowired
    private EmpresaContaRepository contaRepository;
    public Integer alterar(Integer id, EmpresaRequest request){
        return gravar(id,request);
    }
    public Integer incluir(EmpresaRequest request){
        return gravar(null,request);
    }
    @Transactional
    private Integer gravar(Integer id, EmpresaRequest request){
        Validations.build(ORGANIZACAO).notEmpty().check(request);

        if(!organizacaoRepository.existsById(request.getOrganizacao()))
            throw new RegistroNaoLocalizadoException(Entities.ORGANIZACAO_ENTITY, ID);

        String cpfCnpj = Text.onlyDigits(request.getCpfCnpj());

        RegistroCadastralEntity registroEntity = build(request);
        EmpresaEntity entity = new EmpresaEntity();
        Integer organizacao=request.getOrganizacao();
        if(id!=null) {
            entity = repository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.EMPRESA_ENTITY, ID));
            organizacao = entity.getOrganizacao();
            validarIntegridade(cpfCnpj,request.getEmail(),  entity);
        }else {
            if(repository.existsByCpfCnpj(request.getCpfCnpj()))
                throw new RegistroDuplicadoException(CPF_CNPJ, cpfCnpj);

            if(repository.existsByEmail(request.getEmail()))
                throw new RegistroDuplicadoException(EMAIL, request.getEmail());
        }
        BeanUtils.copyProperties(registroEntity, entity);
        entity.setCpfCnpj(cpfCnpj);
        entity.setOrganizacao(organizacao);
        repository.save(entity);
        return entity.getId();
    }
    @Transactional
    public Integer alterarConta(Integer id, EmpresaContaRequest request){
        EmpresaContaCadastroRequest cadastro = new EmpresaContaCadastroRequest();
        BeanUtils.copyProperties(request,cadastro);
        cadastro.setSaldo(0.0);
        return incluirConta(null, id, cadastro);
    }
    @Transactional
    public Integer incluirConta(Integer empresaId, EmpresaContaCadastroRequest request){
        return  incluirConta(empresaId, null, request);
    }

    private Integer incluirConta(Integer empresaId, Integer id, EmpresaContaCadastroRequest request){
        if (id == null && empresaId == null)
            throw new CampoObrigatorioException("Empresa");

        if (empresaId != null && !repository.existsById(empresaId))
            throw new RegistroNaoLocalizadoException(Entities.EMPRESA_ENTITY, ID);

        Validations.build(AGENCIA, NUMERO, LEGENDA, BANCO).notEmpty().check(request);

        if (request.isContaCredito()) {
            if (request.getFatura() == null)
                throw new RegistroIncompativelException("É necessário informar os campos dia intervalo e dia de vencimento para contas do tipo CRÉDITO");

            Validations.build(DIA_VENCIMENTO, DIAS_INTERVALO).notEmpty().check(request.getFatura());
        }

        EmpresaContaEntity entity = null;
        if (id != null) {
            entity = contaRepository.findById(id).orElseThrow(() -> new RegistroNaoLocalizadoException(Entities.EMPRESA_CONTA_ENTITY, ID));
            Double saldo = entity.getSaldo();
            BeanUtils.copyProperties(request, entity);
            entity.setSaldo(saldo);
            empresaId = entity.getEmpresa();
        } else {
            if (request.getSaldo() == null)
                throw new CampoObrigatorioException("Saldo");

            if (contaRepository.existsByAgenciaAndNumeroAndContaCredito(request.getAgencia(), request.getNumero(), request.isContaCredito()))
                throw new RegistroDuplicadoException(NUMERO, String.format("Agência: %s e Número: %s ", request.getAgencia(), request.getNumero()));

            entity = new EmpresaContaEntity();
            BeanUtils.copyProperties(request, entity);
            entity.setEmpresa(empresaId);
            entity.setSaldo(request.getSaldo());
            //entity.setContaPadrao(!contaRepository.existsByEmpresaAndContaCredito(empresaId, request.isContaCredito()));
        }
        boolean padrao = !contaRepository.existsByEmpresaAndContaCredito(empresaId, request.isContaCredito());

        entity.setContaPadrao(padrao ? true : request.isContaPadrao());
        contaRepository.save(entity);

        if (request.isContaPadrao()) {
            contaRepository.atualizarContasNaoPadrao(empresaId, request.isContaCredito(), entity.getId());
        }
        return entity.getId();

    }

    public EmpresaResponse buscar(Integer id){
        EmpresaEntity entity = repository.findById(id).orElseThrow(()->new RegistroNaoLocalizadoException(Entities.EMPRESA_ENTITY, ID));
        EmpresaResponse response = new EmpresaResponse();
        BeanUtils.copyProperties(entity,response);
        return response;
    }
    public List<EmpresaContaResponse> listarContas(Integer empresa){
        List<EmpresaContaEntity> list = contaRepository.findByEmpresa(empresa);
        List<EmpresaContaResponse> response = list.stream().map(i -> {
            EmpresaContaResponse item = new EmpresaContaResponse();
            BeanUtils.copyProperties(i, item);
            return item;
        }).collect(Collectors.toList());
        return response;
    }

    @Autowired
    private EmpresaContaRepository empresaContaRepository;
    @Autowired
    private EmpresaContaMeioPagamentoRepository empresaContaFormaPagamentoRepository;
    @Transactional
    public CredencialResponse configurarPrimeiroAcesso(String cpfCnpj, CadastroSimplificadoRequest request){
        cpfCnpj = Text.onlyDigits(cpfCnpj);

        if(Validation.isEmpty(cpfCnpj))
            throw new CampoObrigatorioException(CPF_CNPJ);
        else {
            if (!Validation.cpfCnpj(cpfCnpj))
                throw new CpfCnpjInvalidoException();
        }
        RegistroCadastralEntity registro = build(request);
        EmpresaEntity entity = new EmpresaEntity();

        OrganizacaoEntity organizacao = cadastrarOrganizacao(cpfCnpj, request.getNomeFantasia().concat(" ").concat(request.getSobrenomeSocial()));
        BeanUtils.copyProperties(registro, entity);
        entity.setCpfCnpj(cpfCnpj);
        entity.setOrganizacao(organizacao.getId());
        repository.save(entity);
        cadastrarContaPadrao(entity.getId());
        CredencialResponse response = cadastrarUsuario(entity, TipoLogin.CPF_CNPJ);
        return response;
    }

    private OrganizacaoEntity cadastrarOrganizacao(String cpfCnpj, String nome){
        if(organizacaoRepository.existsByCpfCnpj(cpfCnpj))
            throw new RegistroDuplicadoException(CPF_CNPJ, cpfCnpj);

        OrganizacaoEntity organizacaoEntity = new OrganizacaoEntity();
        organizacaoEntity.setNome(Text.maxLength(nome.toUpperCase(),100));
        organizacaoEntity.setCpfCnpj(Text.onlyDigits(cpfCnpj));
        organizacaoRepository.save(organizacaoEntity);
        return organizacaoEntity;
    }
    private void cadastrarContaPadrao(Integer empresa){
        EmpresaContaEntity entity = new EmpresaContaEntity();
        entity.setNumero("CCX01");
        entity.setAgencia("AGX01");
        entity.setEmpresa(empresa);
        entity.setLegenda("CONTA BALCAO (caixa empresa)");
        entity.setDescricao("Conta destinada as movimentações financeiras de meio de pagamento em DINHEIRO, conhecida como conta caixa ou balcão");
        entity.setSaldo(0.0);
        entity.setBanco(9999);
        empresaContaRepository.save(entity);
        criarEmpresaContaFormaPagamentoPadrao(entity.getId(), empresa);
    }
    private void criarEmpresaContaFormaPagamentoPadrao(Integer conta, Integer empresa){
        EmpresaContaMeioPagamentoEntity entiy = new EmpresaContaMeioPagamentoEntity();
        entiy.setMeioPagamento(MeioPagamento.DINHEIRO);
        entiy.setTaxa(0.0);
        entiy.setConta(conta);
        entiy.setEmpresa(empresa);
        empresaContaFormaPagamentoRepository.save(entiy);
    }


}
