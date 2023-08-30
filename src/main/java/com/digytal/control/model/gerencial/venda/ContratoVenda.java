package com.digytal.control.model.gerencial.venda;

import com.digytal.control.model.gerencial.ContratoPagamento;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContratoVenda {
    private Integer id;
    private String descricao;
    private LocalDate data;
    private Double valorPrevisto;
    private Double valorAplicado;
    private List<ContratoVendaPagamento> pagamentos = new ArrayList<>();
    private ContratoPagamento pagamento = new ContratoPagamento();
}
