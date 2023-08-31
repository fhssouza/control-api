package com.digytal.control.webservice.consultas;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.repository.acessos.EmpresaContaRepository;
import com.digytal.control.service.contratos.gerencial.ContratoGerencialService;
import com.digytal.control.service.core.lancamentos.PagamentoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/consultas")
public class ConsultaResource {
    @Autowired
    private PagamentoConsultaService lancamentoConsultaService;
    @Autowired
    private EmpresaContaRepository empresaContaRepository;

    @Autowired
    private ContratoGerencialService contratoGerencialService;
    @GetMapping("/contratos/vendas/empresa/{empresa}/data/{data}")
    public Response listarVendaPagamentos(@PathVariable("empresa") Integer empresa, @PathVariable("data") LocalDate data){
        return ResponseFactory.ok(contratoGerencialService.listarVendaPagamentos(empresa, data),"Consulta realizada com sucesso");
    }
    @GetMapping("/lancamentos/empresa/{empresa}/data-inicial/{dataInicial}/data-final/{dataFinal}")
    public Response listarLancamentos(@PathVariable("empresa") Integer empresa, @PathVariable("dataInicial") LocalDate dataInicial, @PathVariable("dataFinal") LocalDate dataFinal){
        return ResponseFactory.ok(lancamentoConsultaService.listarLancamentos(empresa, dataInicial, dataFinal),"Consulta realizada com sucesso");
    }

    @GetMapping("/parcelamentos/empresa/{empresa}/data-inicial/{dataInicial}/data-final/{dataFinal}")
    public Response listarParcelamentos(@PathVariable("empresa") Integer empresa, @PathVariable("dataInicial") LocalDate dataInicial,
                                        @PathVariable("dataFinal") LocalDate dataFinal){
        return ResponseFactory.ok(lancamentoConsultaService.listarParcelamentos(empresa, dataInicial, dataFinal, null, null),"Consulta realizada com sucesso");
    }
    @GetMapping("/parcelamentos/receita/empresa/{empresa}/data-inicial/{dataInicial}/data-final/{dataFinal}")
    public Response listarParcelamentosReceita(@PathVariable("empresa") Integer empresa, @PathVariable("dataInicial") LocalDate dataInicial,
                                               @PathVariable("dataFinal") LocalDate dataFinal){
        return ResponseFactory.ok(lancamentoConsultaService.listarParcelamentos(empresa, dataInicial, dataFinal, LancamentoTipo.RECEITA,null),"Consulta realizada com sucesso");
    }
    @GetMapping("/parcelamentos/receita/empresa/{empresa}/data-inicial/{dataInicial}/data-final/{dataFinal}/cadastro/{cadastro}")
    public Response listarParcelamentosReceita(@PathVariable("empresa") Integer empresa, @PathVariable("dataInicial") LocalDate dataInicial,
                                                @PathVariable("dataFinal") LocalDate dataFinal, @PathVariable("cadastro") Integer cadastro){
        return ResponseFactory.ok(lancamentoConsultaService.listarParcelamentos(empresa, dataInicial, dataFinal, LancamentoTipo.RECEITA,cadastro),"Consulta realizada com sucesso");
    }
    @GetMapping("/parcelamentos/despesa/empresa/{empresa}/data-inicial/{dataInicial}/data-final/{dataFinal}/cadastro/{cadastro}")
    public Response listarParcelamentosDespesa(@PathVariable("empresa") Integer empresa, @PathVariable("dataInicial") LocalDate dataInicial,
                                               @PathVariable("dataFinal") LocalDate dataFinal, @PathVariable("cadastro") Integer cadastro){
        return ResponseFactory.ok(lancamentoConsultaService.listarParcelamentos(empresa, dataInicial, dataFinal, LancamentoTipo.DESPESA,cadastro),"Consulta realizada com sucesso");
    }

    @GetMapping("/fluxo-caixa/empresa/{empresa}/data-inicial/{dataInicial}/data-final/{dataFinal}")
    public Response listarFluxoCaixa(@PathVariable("empresa") Integer empresa, @PathVariable("dataInicial") LocalDate dataInicial, @PathVariable("dataFinal") LocalDate dataFinal){
        return ResponseFactory.ok(lancamentoConsultaService.listarFluxoCaixa(empresa, dataInicial, dataFinal),"Consulta realizada com sucesso");
    }
    @GetMapping("/empresa/{empresa}/conta-balcao")
    public Response buscarContaBalcaoSaldo(@PathVariable("empresa") Integer empresa){
        return ResponseFactory.ok(empresaContaRepository.buscarContaBalcaoSaldo(empresa),"Consulta realizada com sucesso");
    }
    @GetMapping("/parcelamentos/{parcelamento}/parcelas")
    public Response listarParcelamentosReceita(@PathVariable("parcelamento") Integer parcelamento){
        return ResponseFactory.ok(lancamentoConsultaService.listarParcelas(parcelamento),"Consulta realizada com sucesso");
    }
}
