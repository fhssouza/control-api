package com.digytal.control.repository.modulo.fincanceiro;

import com.digytal.control.infra.business.BusinessException;
import com.digytal.control.infra.business.ErroNaoMapeadoException;
import com.digytal.control.infra.persistence.QueryRepository;
import com.digytal.control.infra.sql.StringSQL;
import com.digytal.control.model.consulta.lancamentos.PagamentoFiltro;
import com.digytal.control.model.modulo.financeiro.pagamento.response.PagamentoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class PagamentoRepositoryImpl extends QueryRepository {

    public List<PagamentoResponse> listarPagamentos(Integer empresa, PagamentoFiltro filtro){
        return listarPagamentos(empresa, filtro, elaborarSql(null, null),"e.data.dataHora");
    }
    public List<PagamentoResponse> listarPagamentosCompleto(Integer empresa, PagamentoFiltro filtro){
        StringBuilder novosCampos = new StringBuilder("c.id as cadastro_id, c.cpfCnpj as cadastro_identificador, c.nomeFantasia as cadastro_descricao, ");
        novosCampos.append("a.id as area_id, a.id as area_identificador, a.nome as area_descricao, ");
        novosCampos.append("n.id as natureza_id, n.id as natureza_identificador, n.nome as natureza_descricao");

        StringBuilder joins = new StringBuilder("INNER JOIN CadastroEntity c ON e.partes.cadastro = c.id ");
        joins.append("INNER JOIN AplicacaoEntity a ON e.aplicacao.area = a.id ");
        joins.append("INNER JOIN AplicacaoEntity n ON e.aplicacao.natureza = n.id");

        String select = elaborarSql(novosCampos.toString(), joins.toString());

        return listarPagamentos(empresa, filtro, select ,"e.data.dataHora");
    }
    private List<PagamentoResponse> listarPagamentos(Integer empresa, PagamentoFiltro filtro, String select, String orderBy){
        try {
            StringSQL sql = new StringSQL();
            sql.select(select);
            Map<String, Object> filters = new LinkedHashMap<>();
            filters.put("empresa", empresa);
            filters.put("diaInicial", filtro.getDataInicial());
            filters.put("diaFinal", filtro.getDataFinal());

            sql.setFilters(filters)
                    .where("e.partes.empresa").equal("empresa").integer()
                    .and("e.data.dia").greaterThanEqual("diaInicial").localDate()
                    .and("e.data.dia").lessThanEquals("diaFinal").localDate();

            if (filtro.getMeioPagamento() != null) {
                filters.put("meioPagamento", filtro.getMeioPagamento());
                sql.and("e.meioPagamento").equal("meioPagamento").enumeration();
            }
            if (filtro.getTipo() != null) {
                filters.put("tipo", filtro.getTipo());
                sql.and("e.tipo").equal("tipo").enumeration();
            }
            if (filtro.getConta() != null) {
                filters.put("conta", filtro.getConta());
                sql.and("e.conta").equal("conta").integer();
            }
            if (filtro.getCadastro() != null) {
                filters.put("cadastro", filtro.getCadastro());
                sql.and("e.partes.cadastro").equal("cadastro").integer();
            }
            sql.orderBy(orderBy);
            List<PagamentoResponse> lista = search(sql, PagamentoResponse.class);
            return lista;
        }catch (BusinessException ex){
            log.warn(BusinessException.logMessage(ex));
            throw ex;
        }catch (Exception ex){
            log.error("Erro ao tentar realizar um pagamento",ex);
            throw new ErroNaoMapeadoException();
        }
    }
    private String elaborarSql(String campos, String tabelas){
        StringBuilder select = new StringBuilder();
        select.append(" SELECT e.id as id, e.numeroDocumento as numeroDocumento, e.numeroTransacao as numeroTransacao, e.titulo as titulo, e.descricao as descricao, e.tipo as tipo, e.data as data, " +
                " e.meioPagamento as meioPagamento, e.observacao as observacao, e.valor as valor");

        select.append(campos!=null?", " + campos:" ");

        select.append(" FROM PagamentoEntity e ");
        select.append(tabelas!=null?tabelas:" ");

        return select.toString();
    }
}
