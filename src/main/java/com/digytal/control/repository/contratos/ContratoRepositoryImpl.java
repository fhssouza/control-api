package com.digytal.control.repository.contratos;

import com.digytal.control.infra.sql.StringSQL;
import com.digytal.control.model.contrato.contratos.contrato.ContratoTipo;
import com.digytal.control.model.gerencial.ContratoPagamentoAnalitico;
import com.digytal.control.repository.core.QueryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContratoRepositoryImpl extends QueryRepository {
    public List<ContratoPagamentoAnalitico> listarContratosPagamentos(ContratoTipo tipo, Integer empresa, LocalDate data){
        StringSQL sql = new StringSQL();
        sql.select("SELECT e.id as id, e.descricao as descricao, e.valorPrevisto as valorPrevisto , e.valorAplicado as valorAplicado, e.data.dia as data," +
                " pg.meioPagamento as meioPagamento, pg.valor as valorPago, pg.valorParcela as valorParcela, pg.numeroParcelas as numeroParcelas, pg.dataPrimeiroVencimento as parcelaPrimeiroVencimento   " +
                " FROM ContratoEntity e JOIN ContratoPagamentoEntity pg ON e.id = pg.contrato ");

        Map<String, Object> filters = new LinkedHashMap<>();
        filters.put("tipo", tipo);
        filters.put("empresa", empresa);
        filters.put("data", data);

        sql.setFilters(filters)
                .where("e.partes.empresa").equal("empresa").integer()
                .and("e.data.dia").equal("data").localDate()
                .and("e.tipo").equal("tipo").enumeration();

        sql.orderBy("e.data.dataHora");
        return search(sql, ContratoPagamentoAnalitico.class);
    }
}
