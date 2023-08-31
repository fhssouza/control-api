package com.digytal.control.repository.acessos;

import com.digytal.control.model.core.acessos.empresa.conta.EmpresaContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmpresaContaRepository extends JpaRepository<EmpresaContaEntity, Integer> {
    Optional<EmpresaContaEntity> findByEmpresaAndContaPadraoAndContaCredito(Integer empresa, boolean contaPadrao, boolean contaCredito);
    boolean existsByAgenciaAndNumeroAndContaCredito(String agencia,String numero,  boolean contaCredito);
    List<EmpresaContaEntity> findByEmpresa(Integer empresa);
    boolean existsByEmpresaAndContaCredito(Integer empresa, boolean contaCredito);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE EmpresaContaEntity e set e.contaPadrao = false WHERE e.empresa = :empresa AND e.contaCredito = :contaCredito and e.id <> :id ")
    void atualizarContasNaoPadrao(@Param("empresa") Integer empresa, @Param("contaCredito") boolean contaCredito, @Param("id") Integer id);

    @Query(value = "SELECT e.saldo FROM EmpresaContaEntity e WHERE e.banco = 9999 AND e.empresa = :empresa ")
    Double buscarContaBalcaoSaldo(@Param("empresa") Integer empresa);

    @Query(value = "SELECT e FROM EmpresaContaEntity e WHERE e.banco = 9999 AND e.empresa = :empresa ")
    EmpresaContaEntity buscarContaBalcao(@Param("empresa") Integer empresa);
}
