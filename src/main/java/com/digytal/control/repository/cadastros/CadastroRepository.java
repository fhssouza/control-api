package com.digytal.control.repository.cadastros;

import com.digytal.control.model.core.cadastros.cadastro.CadastroEntity;
import com.digytal.control.model.core.cadastros.produto.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CadastroRepository extends JpaRepository<CadastroEntity, Integer> {
    boolean existsByCpfCnpjAndOrganizacao(String cpfCnpj, Integer organizacao);
    boolean existsByEmailAndOrganizacao(String email,Integer organizacao);

    List<CadastroEntity> findByOrganizacaoAndNomeFantasiaContainingOrderByNomeFantasia(Integer organizacao, String nome);
}
