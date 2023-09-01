package com.digytal.control.model.core.lancamentos.lancamento.response;

import com.digytal.control.model.core.comum.MeioPagamento;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.comum.RegistroData;
import com.digytal.control.model.core.comum.RegistroPartes;
import lombok.Data;

@Data
public abstract class LancamentoAbstractResponse {
    private Integer id;
    private String numeroDocumento;
    private String descricao;
    private RegistroData data = new RegistroData();
    private RegistroPartes partes = new RegistroPartes();
    private LancamentoTipo tipo;
    private LancamentoCadastro cadastro = new LancamentoCadastro();
    private MeioPagamento meioPagamento;
    private boolean contaCaixaBalcao;
}
