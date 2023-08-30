package com.digytal.control.webservice.core.lancamentos;

import com.digytal.control.service.core.lancamentos.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
    @Autowired
    private PagamentoService service;
    /*
    private Response incluirReceita(@PathVariable("contaBancoEmpresa") Integer contaBancoEmpresa,@PathVariable("planoContasId") Integer planoContasId, @RequestBody LancamentoRequest request, String tipo){
        return ResponseFactory.create(service.incluir(contaBancoEmpresa, planoContasId, PlanoContaTipo.RECEITA, request,null),String.format("Lançamento de %s realizado com sucesso", tipo));
    }
    private Response incluirDespesa(@PathVariable("contaBancoEmpresa") Integer contaBancoEmpresa,@PathVariable("planoContasId") Integer planoContasId, @RequestBody LancamentoRequest request, String tipo){
        return ResponseFactory.create(service.incluir(contaBancoEmpresa, planoContasId, PlanoContaTipo.DESPESA, request,null),String.format("Lançamento de %s realizado com sucesso", tipo));
    }
    //sobrecargas
    //@PostMapping("/receitas/conta/{contaBancoEmpresa}/plano-contas/{planoContasId}")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirReceita(@PathVariable("contaBancoEmpresa") Integer contaBancoEmpresa,@PathVariable("planoContasId") Integer planoContasId,   @RequestBody LancamentoRequest request){
        return incluirReceita(contaBancoEmpresa, planoContasId, request,"receita");
    }

    //@PostMapping("/despesas/conta/{contaBancoEmpresa}/plano-contas/{planoContasId}")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirDespesa(@PathVariable("contaBancoEmpresa") Integer contaBancoEmpresa,@PathVariable("planoContasId") Integer planoContasId,   @RequestBody LancamentoRequest request){
        return incluirDespesa(contaBancoEmpresa, planoContasId, request,"despesa");
    }
    //@PostMapping("/receitas")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirReceita(@RequestBody LancamentoRequest request){
        return incluirReceita(null, null,request);
    }

    //@PostMapping("/despesas")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirDespesa(@RequestBody LancamentoRequest request){
        return incluirDespesa(null, null,request);
    }
    //parcelamentos
    //@PostMapping("/receitas/conta/{contaBancoEmpresa}/plano-contas/{planoContasId}/parcelamento")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirReceitaParcelamento(@PathVariable("contaBancoEmpresa") Integer contaBancoEmpresa,@PathVariable("planoContasId") Integer planoContasId,   @RequestBody LancamentoParcelamentoRequest request){
        return incluirReceita(contaBancoEmpresa, planoContasId, request,"parcelamento de receita");
    }

    //@PostMapping("/despesas/conta/{contaBancoEmpresa}/plano-contas/{planoContasId}/parcelamento")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirDespesaParcelamento(@PathVariable("contaBancoEmpresa") Integer contaBancoEmpresa,@PathVariable("planoContasId") Integer planoContasId,   @RequestBody LancamentoParcelamentoRequest request){
        return incluirDespesa(contaBancoEmpresa, planoContasId, request,"parcelamento de despesa");
    }
    //@PostMapping("/receitas/parcelamento")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirReceitaParcelamento(@RequestBody LancamentoParcelamentoRequest request){
        return incluirReceitaParcelamento(null, null,request);
    }

    //@PostMapping("/despesas/parcelamento")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirDespesaParcelamento(@RequestBody LancamentoParcelamentoRequest request){
        return incluirDespesaParcelamento(null, null,request);
    }
    //@PostMapping("/despesas/parcelamento/fatura")
    //@ResponseStatus( HttpStatus.CREATED )
    public Response incluirDespesaParcelamentoFatura(@RequestBody LancamentoParcelamentoRequest request){
        request.setFatura(true);
        return incluirDespesaParcelamento(null, null,request);
    }

    @PostMapping("/transferencias")
    @ResponseStatus( HttpStatus.CREATED )
    public Response transferir(@RequestBody TransferenciaRequest request){
        service.transferir(request);
        return ResponseFactory.create(true,"Transferência realizada com sucesso");
    }
    *
     */

}
