package com.digytal.control.model.core.comum;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
@Getter
public class RegistroData {
    @Column(name = "cpt_dh_lancto")
    private LocalDateTime dataHora;
    @Column(name = "cpt_dia")
    private LocalDate dia;
    @Column(name = "cpt_mes")
    private Integer mes;
    @Column(name = "cpt_ano")
    private Integer ano;
    @Column(name = "cpt_per_lancto")
    private Integer periodo;
    @Column(name = "cpt_per_competencia")
    private Integer competencia;
    public static RegistroData of(){
        return of(LocalDateTime.now());
    }
    public static RegistroData of(LocalDate data, LocalTime hora){
        LocalDateTime dataHora = LocalDateTime.of(data==null?LocalDate.now():data, hora==null?LocalTime.now() : hora);
        return of(dataHora);
    }

    public static RegistroData of(LocalDateTime dataHora){
        RegistroData instance = new RegistroData();
        instance.dataHora = dataHora;
        LocalDate data = dataHora.toLocalDate();
        instance.dia = data;
        instance.mes=data.getMonthValue();
        instance.ano = data.getYear();
        instance.periodo = periodo(data);
        instance.competencia = instance.periodo;
        return instance;
    }
    public static Integer periodo (LocalDate data){
        return Integer.valueOf(data.getYear()+String.format("%02d", data.getMonthValue()));
    }

}
