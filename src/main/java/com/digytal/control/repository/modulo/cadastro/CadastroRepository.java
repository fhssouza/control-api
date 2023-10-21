package com.digytal.control.repository.modulo.cadastro;

import com.digytal.control.model.modulo.cadastro.CadastroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CadastroRepository extends JpaRepository<CadastroEntity, Integer> {
    boolean existsByCpfCnpjAndOrganizacao(String cpfCnpj, Integer organizacao);
    boolean existsByEmailAndOrganizacao(String email,Integer organizacao);
    @Query("SELECT e FROM CadastroEntity e WHERE e.organizacao=:organizacao AND e.perfil.cliente=:cliente AND e.perfil.fornecedor=:fornecedor AND e.nomeFantasia LIKE %:nome% ORDER BY e.nomeFantasia")
    List<CadastroEntity> listar(@Param("organizacao") Integer organizacao, @Param("cliente") boolean cliente, @Param("fornecedor") boolean fornecedor, @Param("nome") String nome);
}
