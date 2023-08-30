package com.digytal.control.job;

import com.digytal.control.model.core.lancamentos.parcelamento.parcela.ParcelaEntity;
import com.digytal.control.repository.lancamentos.ParcelaRepository;
import com.digytal.control.service.core.lancamentos.BoletoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class Job {
    @Autowired
    private ParcelaRepository parcelaRepository;
    @Autowired
    private BoletoService boletoService;
    /*
    https://mmarcosab.medium.com/agendando-coisas-em-programas-springboot-com-scheduled-1410bea2dda9

    “@Scheduled(cron = “1 2 3 4 5 6")”

        1: segundo (preenchido de 0 a 59)
        2: minuto (preenchido de 0 a 59)
        3 hora (preenchido de 0 a 23)
        4 dia (preenchido de 0 a 31)
        5 mês (preenchido de 1 a 12)
        6 dia da semana (preenchido de 0 a 6)

     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void consultarParcelasPendentes(){
        log.info("Executando o Job de consultarParcelasPendentes junto a Asaas Pagamento via BOLETO ");
        List<ParcelaEntity> parcelas = parcelaRepository.listarParcelasPendentesPagamento(LocalDate.of(2023,12,31));
        for(ParcelaEntity parcela: parcelas){
            boletoService.compensar(parcela);
        }
    }
}
