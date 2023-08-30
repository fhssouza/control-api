package com.digytal.control.repository.params;

import com.digytal.control.model.core.params.cep.CepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CepRepository extends JpaRepository<CepEntity, String> {
}
