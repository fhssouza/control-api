package com.digytal.control.repository.acessos;

import com.digytal.control.model.core.acessos.empresa.EmpresaEntity;
import com.digytal.control.model.core.acessos.empresa.EmpresaConsultaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {
    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);
    @Query("SELECT e.organizacao FROM EmpresaEntity e WHERE e.id = :id")
    Integer buscarOrganizacao(@Param("id") Integer empresa);

    List<EmpresaConsultaResponse> listarEmpresas(Integer usuario);
}
