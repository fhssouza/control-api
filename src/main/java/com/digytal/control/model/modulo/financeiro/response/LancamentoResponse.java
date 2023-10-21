package com.digytal.control.model.modulo.financeiro.response;

import com.digytal.control.model.modulo.acesso.empresa.aplicacao.AplicacaoTipo;
import com.digytal.control.model.comum.MeioPagamento;
import com.digytal.control.model.comum.RegistroData;
import com.digytal.control.model.comum.RegistroSimples;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "Resposta do lançamento",description = "Resposta de lancamento")
public class LancamentoResponse {
    private Integer id;
    private String numeroDocumento;
    private String titulo;
    private String descricao;
    private AplicacaoTipo tipo;
    private RegistroData data;
    //private Participante partes;
    private MeioPagamento meioPagamento;
    private String observacao;
    private RegistroSimples area;
    private RegistroSimples natureza;
    private String numeroTransacao;
}
