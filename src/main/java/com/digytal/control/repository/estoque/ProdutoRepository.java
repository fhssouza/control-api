package com.digytal.control.repository.estoque;

import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import com.digytal.control.model.core.params.banco.BancoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    List<ProdutoEntity> findByOrganizacao(Integer organizacao);
    List<ProdutoEntity> findByOrganizacaoAndNomeContainingOrderByNome(Integer organizacao, String nome);
}
