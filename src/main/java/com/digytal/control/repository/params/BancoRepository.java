package com.digytal.control.repository.params;

import com.digytal.control.model.core.params.banco.BancoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoRepository extends JpaRepository<BancoEntity, Integer> {
    List<BancoEntity> findByNomeContainingOrderByNome(String nome);
}
