package com.digytal.control.service.contratos;

import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.model.contrato.contratos.contrato.ContratoEntity;
import com.digytal.control.model.contrato.contratos.contrato.ContratoItemEntity;
import com.digytal.control.model.contrato.contratos.contrato.ContratoSituacao;
import com.digytal.control.model.contrato.contratos.contrato.ContratoTipo;
import com.digytal.control.model.contrato.contratos.assinatura.AssinaturaRequest;
import com.digytal.control.model.contrato.meiopagamento.ContratoPagamentoEntity;
import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import com.digytal.control.model.core.cadastros.produto.ProdutoItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AssinaturaService extends ContratoService {
    @Transactional
    public Integer gerar(AssinaturaRequest request){
        ContratoEntity entity = new ContratoEntity();
        entity.setDescricao(request.getDescricao());
        entity.setTipo(ContratoTipo.SERVICO);
        entity.setSituacao(ContratoSituacao.ANALISANDO);

        ProdutoEntity produto= findProduto(request.getProduto());
        ContratoItemEntity item = new ContratoItemEntity();
        item.setProduto(ProdutoItem.of(produto.getId(), produto.getNome(),produto.getUnidadeMedida(),  produto.getValor(), produto.getCodigoBarras(), produto.getSku(), produto.getSaldo()));
        item.setQuantidade(1.0);
        item.setValorUnitario(produto.getValor());
        entity.getItens().add(item);

        ContratoPagamentoEntity pagamento = new ContratoPagamentoEntity();
        pagamento.setMeioPagamento(MeioPagamento.PARCELADO);
        pagamento.setDataPrimeiroVencimento(LocalDate.now().plusDays(7l));
        pagamento.setNumeroParcelas(1);
        entity.getPagamentos().add(pagamento);

        RegistroParteRequest parte = new RegistroParteRequest();
        parte.setCadastro(request.getCadastro());
        parte.setEmpresa(request.getEmpresa());

        return null;//persist(parte, entity);
    }
}
