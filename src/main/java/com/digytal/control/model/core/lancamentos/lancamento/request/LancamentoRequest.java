package com.digytal.control.model.core.lancamentos.lancamento.request;
import lombok.Data;

@Data
public class LancamentoRequest extends TransacaoRequest {
   private Integer contaBancoEmpresa;
}
