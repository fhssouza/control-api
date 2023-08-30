package com.digytal.control.repository.acessos;

import com.digytal.control.model.core.acessos.empresa.pagamento.EmpresaContaMeioPagamentoEntity;
import com.digytal.control.model.core.comum.MeioPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaContaMeioPagamentoRepository extends JpaRepository<EmpresaContaMeioPagamentoEntity, Integer> {
    List<EmpresaContaMeioPagamentoEntity> findByConta(Integer conta);
    List<EmpresaContaMeioPagamentoEntity> findByEmpresa(Integer empresa);
    boolean existsByEmpresaAndMeioPagamento(Integer empresa, MeioPagamento formaPagamento);
    EmpresaContaMeioPagamentoEntity findByEmpresaAndMeioPagamento(Integer empresa, MeioPagamento meioPagamento);
}
