package com.digytal.control.repository.cadastros;

import com.digytal.control.model.core.acessos.organizacao.OrganizacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizacaoRepository extends JpaRepository<OrganizacaoEntity, Integer > {
    boolean existsByCpfCnpj(String cpfCnpj);
}
