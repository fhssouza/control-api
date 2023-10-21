package com.digytal.control.service.modulo.contrato;

import com.digytal.control.service.comum.AbstractService;

public abstract class ContratoService extends AbstractService {
    /*
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PagamentoService pagamentoService;
    protected ContratoEntity build(ContratoTipo tipo, ContratoRequest request){
        return build(tipo, ContratoSituacao.CONCLUIDO, request);
    }
    protected ContratoEntity build(ContratoTipo tipo, ContratoSituacao situacao, ContratoRequest request){
        ContratoEntity entity = new ContratoEntity();
        entity.setTipo(tipo);
        entity.setSituacao(situacao);
        entity.setNumero(gerarLocalizador());
        entity.setDescricao(request.getDescricao());
        entity.setPartes(definirParticipantes(request.getCadastro()));
        entity.setIntermediador(request.getIntermediador() == null ? entity.getPartes().getUsuario() : request.getIntermediador());
        entity.setData(RegistroData.of(request.getData()));
        entity.setPagamentos(conferirPagamentos(request));

        ContratoCalculo calculo = calcularItens(request.getItens());
        entity.setItens(calculo.getItens());

        ContratoValor valor = new ContratoValor();
        valor.setAplicado(Calculos.aplicarEscala(Calculos.ESCALA4, request.getValorAplicado()));
        valor.setPrevisto(calculo.getItensTotalPrevisto());
        valor.setAcrescimoItens(calculo.getItensTotalAcrescimo());
        valor.setAcrescimoPagamento(entity.getPagamentos().stream().mapToDouble(p -> Calculos.subtrair(Calculos.ESCALA4, p.getValorPago(), p.getValorOriginal())).sum());
        valor.setDescontoItens(calculo.getItensTotalDesconto());
        valor.setDescontoManual(0.0);
        entity.setValor(valor);
        conferirValores(valor.getAplicado(), valor.getAcrescimoPagamento(), calculo.getItensTotalAplicado() );
        processarPagamentos(tipo,entity, request.getFormasPagamento());
        return entity;
    }
    private void conferirValores(Double valorAplicado, Double valorAcrescimentoPagamento, Double valorTotalItens){
        Double subtotal = Calculos.subtrair(valorAplicado, valorAcrescimentoPagamento);
        if(!Calculos.compararIgualdade(subtotal, valorTotalItens))
            throw new RegistroIncompativelException("É necessário revisar os valores informados");

    }
    private ContratoCalculo calcularItens(List<ContratoItemRequest> itens){
        ContratoCalculo calculo = new ContratoCalculo();
        List<ContratoItemEntity> list = new ArrayList<>();
        itens.stream().forEach(i->{
            ContratoItemEntity item = new ContratoItemEntity();
            item.setDescricao(i.getDescricao());
            item.setProduto(produtoItem(i.getProduto()));

            item.setQuantidade(Calculos.aplicarEscala(Calculos.ESCALA4,i.getQuantidade()));
            item.setValorUnitario(Calculos.aplicarEscala(Calculos.ESCALA4,i.getValorUnitario()));
            item.setValorAplicado(Calculos.aplicarEscala(Calculos.ESCALA4,i.getValorAplicado()));
            item.setValorPrevisto(Calculos.multiplicar(Calculos.ESCALA4,item.getProduto().getPreco(), i.getQuantidade()) );
            Double variacao = Calculos.subtrair(Calculos.ESCALA4,item.getValorAplicado(),item.getValorPrevisto());
            item.setValorVariacao(variacao);
            calculo.setItensTotalPrevisto(Calculos.somar(Calculos.ESCALA4,calculo.getItensTotalPrevisto(), item.getValorPrevisto()));
            calculo.setItensTotalAplicado(Calculos.somar(Calculos.ESCALA4,calculo.getItensTotalAplicado(), item.getValorAplicado()));
            if (Calculos.compararMenorQue(variacao, 0.0))
                calculo.setItensTotalDesconto(Calculos.somar(Calculos.ESCALA4,calculo.getItensTotalDesconto(), variacao));
            else
                calculo.setItensTotalAcrescimo(Calculos.somar(Calculos.ESCALA4,calculo.getItensTotalAcrescimo(), variacao));
            list.add(item);
        });
        calculo.setItens(list);
        return calculo;
    }
    private ProdutoItem produtoItem(Integer produto){
        ProdutoEntity entity = produtoRepository.findById(produto).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.PRODUTO_ENTITY, Attributes.ID));
        ProdutoItem item = new ProdutoItem();
        item.setId(entity.getId());
        item.setSaldo(entity.getSaldo());
        item.setCodigoBarras(entity.getCodigoBarras());
        item.setTaxaLiquidacao(entity.getTaxaLiquidacao());
        item.setUnidadeMedidaSigla(String.valueOf(entity.getUnidadeEmbalagem()));
        item.setPreco(entity.getValor());
        return item;
    }
    private List<ContratoPagamentoEntity> conferirPagamentos(ContratoRequest request){
        List<ContratoPagamentoEntity> pagamentos = new ArrayList<>();
        if(request.getFormasPagamento()==null)
            request.setFormasPagamento(pagamentoService.definirPagamentoPadrao(request.getValorAplicado()));
        request.getFormasPagamento().forEach(pagto->{
            if(pagto.getMeioPagamento() == null)
                throw new CampoObrigatorioException(Attributes.MEIO_PAGAMENTO);

            ContratoPagamentoEntity entity = new ContratoPagamentoEntity();
            BeanUtils.copyProperties(pagto,entity);
            entity.setValorOriginal(entity.getValorOriginal() == null ? entity.getValorPago() : entity.getValorOriginal());
            entity.setTaxaPagamento(entity.getTaxaPagamento() == null ? 0.0 : entity.getTaxaPagamento());
            pagamentos.add(entity);
        });
        return pagamentos;
    }
    private void processarPagamentos(ContratoTipo tipo, ContratoEntity entity, List<TransacaoRateioRequest> pagamentos){

        AplicacaoTipo aplicacaoTipo = ContratoTipo.COMPRA == tipo ? AplicacaoTipo.DESPESA : AplicacaoTipo.RECEITA;
        PagamentoRequest request = new PagamentoRequest();
        request.setDescricao(tipo.getDescricao() + " " + request.getNumeroDocumento());
        request.setValor(entity.getValor().getAplicado());
        request.setCadastro(entity.getPartes().getCadastro());
        request.setData(entity.getData().getDia());
        request.setFormasPagamento(pagamentos);
        pagamentoService.incluir(aplicacaoTipo,request, entity.getNumero());
    }

     */
}
