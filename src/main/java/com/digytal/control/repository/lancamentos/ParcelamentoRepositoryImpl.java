package com.digytal.control.repository.lancamentos;

import com.digytal.control.infra.sql.StringSQL;
import com.digytal.control.model.core.lancamentos.lancamento.LancamentoTipo;
import com.digytal.control.model.core.lancamentos.parcelamento.ParcelamentoResponse;
import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaResponse;
import com.digytal.control.repository.core.QueryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ParcelamentoRepositoryImpl extends QueryRepository {
    public List<ParcelamentoResponse> listBy(Integer empresa, LocalDate diaInicial, LocalDate diaFinal, LancamentoTipo tipo, Integer cadastro){
        StringSQL sql = new StringSQL();
        sql.select(" SELECT l.id as id,l.descricao as descricao, l.negociacao as negociacao, l.pendencia as pendencia, " +
                " l.numeroDocumento as numeroDocumento, l.data as data, l.partes as partes, l.tipo as tipo, l.meioPagamento as meioPagamento, " +
                " c.id as cadastro_id,c.cpfCnpj as cadastro_cpfCnpj, c.telefone.celular as cadastro_celular, c.nomeFantasia as cadastro_nomeFantasia, c.sobrenomeSocial as cadastro_sobrenomeSocial" +
                " FROM ParcelamentoEntity l JOIN CadastroEntity c ON l.partes.cadastro = c.id ");

        Map<String, Object> filters = new LinkedHashMap<>();
        filters.put("empresa", empresa);
        filters.put("diaInicial", diaInicial);
        filters.put("diaFinal", diaFinal);
        if(cadastro!=null)
            filters.put("cadastro", cadastro);

        if(tipo!=null ) {
            filters.put("tipo", tipo);
        }

        sql.setFilters(filters)
                .where("l.partes.empresa").equal("empresa").integer()
                .and("l.data.dia").greaterThanEqual("diaInicial").localDate()
                .and("l.data.dia").lessThanEquals("diaFinal").localDate();
        if(cadastro!=null)
            sql.and("l.partes.cadastro").equal("cadastro").integer();
        if(tipo!=null ) {
            sql.and("l.tipo").equal("tipo").enumeration();
        }
        sql.orderBy("l.data.dataHora");
        return search(sql, ParcelamentoResponse.class);
    }
    public List<ParcelaResponse> listarParcelas(Integer parcelamento){
        StringSQL sql = new StringSQL();
        sql.select(" SELECT e.id as id, e.descricao as descricao, e.aliquota as aliquota, e.negociacao as negociacao, " +
                " e.pendencia as pendencia, e.boleto as boleto, " +
                " e.quitacao as quitacao, e.parcelamento as parcelamento, e.observacao as observacao" +
                " FROM ParcelaEntity e");
        sql.where("e.parcelamento").equal().integer(parcelamento);
        sql.orderBy("e.negociacao.numeroParcela");
        return search(sql, ParcelaResponse.class);
    }
}