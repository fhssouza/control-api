package com.digytal.control.repository.lancamentos;

import com.digytal.control.infra.sql.StringSQL;
import com.digytal.control.model.core.lancamentos.lancamento.response.LancamentoResponse;
import com.digytal.control.repository.core.QueryRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PagamentoRepositoryImpl extends QueryRepository {
    public List<LancamentoResponse> listBy(Integer empresa, LocalDate diaInicial, LocalDate diaFinal){
        StringSQL sql = new StringSQL();
        sql.select(" SELECT l.id as id,l.descricao as descricao, l.valor as valor, l.tipo as tipo, " +
                " l.numeroDocumento as numeroDocumento, l.data as data, l.partes as partes,l.meioPagamento as meioPagamento, CASE WHEN ec.banco = 9999 THEN 'true' else 'false' END as contaCaixaBalcao, " +
                " c.id as cadastro_id,c.cpfCnpj as cadastro_cpfCnpj, c.telefone.celular as cadastro_celular, c.nomeFantasia as cadastro_nomeFantasia, c.sobrenomeSocial as cadastro_sobrenomeSocial" +
                " FROM PagamentoEntity l INNER JOIN EmpresaContaEntity ec ON l.contaBanco = ec.id LEFT JOIN CadastroEntity c ON l.partes.cadastro = c.id ");


        Map<String, Object> filters = new LinkedHashMap<>();
        filters.put("empresa", empresa);
        filters.put("diaInicial", diaInicial);
        filters.put("diaFinal", diaFinal);

        sql.setFilters(filters)
                .where("l.partes.empresa").equal("empresa").integer()
                .and("l.data.dia").greaterThanEqual("diaInicial").localDate()
                .and("l.data.dia").lessThanEquals("diaFinal").localDate();

        //pegar somente da conta balcao da empresa
        sql.orderBy("l.data.dataHora");
        return search(sql, LancamentoResponse.class);
    }
}