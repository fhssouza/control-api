package com.digytal.control.repository.contratos;

import com.digytal.control.model.contrato.contratos.contrato.ContratoEntity;
import com.digytal.control.model.contrato.contratos.contrato.ContratoTipo;
import com.digytal.control.model.gerencial.ContratoPagamentoAnalitico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContratoRepository extends JpaRepository<ContratoEntity,Integer> {
    @Query("SELECT e FROM ContratoEntity e WHERE e.partes.empresa = :empresa AND e.data.dia = :dia  ")
    List<ContratoEntity> filtrar(Integer empresa, LocalDate dia);

    List<ContratoPagamentoAnalitico> listarContratosPagamentos(ContratoTipo tipo, Integer empresa, LocalDate data);
}
