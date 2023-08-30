package com.digytal.control.webservice.core.lancamentos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.integracao.asaas.model.BoletoResponse;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoParcelaCorrecaoRequest;
import com.digytal.control.model.core.lancamentos.parcelamento.liquidacao.ParcelaPagamentoFormaRequest;
import com.digytal.control.service.core.lancamentos.BoletoService;
import com.digytal.control.service.core.lancamentos.ParcelamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoResource {

    @Autowired
    private ParcelamentoService service;

    @Autowired
    private BoletoService boletoService;

    @PostMapping("/parcelas/{id}/empresa/{empresa}/usuario/{usuario}/pagamento")
    @ResponseStatus( HttpStatus.CREATED )
    public Response realizarPagamento(@PathVariable("id") Integer id, @PathVariable("empresa") Integer empresa, @PathVariable("usuario") Integer usuario, @RequestBody List<ParcelaPagamentoFormaRequest> request){
        service.realizarPagamento(id,empresa, usuario, request);
        return ResponseFactory.create(true,"Pagamento realizado com sucesso");
    }
    @PatchMapping("/{id}/parcelas/{parcelaId}/correcao")
    @ResponseStatus( HttpStatus.CREATED )
    public Response realizarCorrecaoMonetariaManual(@PathVariable("id") Integer id, @PathVariable("parcelaId") Integer parcelaId, @RequestBody ParcelamentoParcelaCorrecaoRequest request){
        service.realizarCorrecaoMonetariaManual(id,parcelaId, request);
        return ResponseFactory.create(true,"Parcela corrigida com sucesso");
    }
    //@PatchMapping("/parcelas/{parcela}/valor/{valorBoleto/vencimento/{novoVencimento}")
    public Response gerarBoleto(@PathVariable("parcela") Integer parcela, @PathVariable("valorBoleto") Double valorBoleto, @PathVariable("novoVencimento") LocalDate novoVencimento ){
        BoletoResponse response= boletoService.gerarBoleto(parcela, valorBoleto, novoVencimento);
        return ResponseFactory.create(response,"Solicitação realizada com sucesso");
    }
    @PatchMapping("/parcelas/{parcela}/valor/{valorBoleto}")
    public Response gerarBoleto(@PathVariable("parcela") Integer parcela, @PathVariable("valorBoleto") Double valorBoleto){
        /*
        BoletoResponse response = new BoletoResponse();
        response.setBankSlipUrl("https://www.asaas.com/b/pdf/5322803930049907");
        response.setValue(5.0);
        */
        BoletoResponse response = boletoService.gerarBoleto(parcela, valorBoleto);
        return ResponseFactory.create(response,"Solicitação realizada com sucesso");
    }

}
