package com.digytal.control.repository.modulo.cadastro;

import com.digytal.control.model.modulo.cadastro.produto.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {
    List<ProdutoEntity> findByOrganizacaoAndNomeContainingOrderByNome(Integer organizacao, String nome);
    List<ProdutoEntity> findByOrganizacaoAndInternoAndNomeContainingOrderByNome(Integer organizacao, boolean interno, String nome);
}
