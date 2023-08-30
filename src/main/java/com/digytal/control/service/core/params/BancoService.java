package com.digytal.control.service.core.params;

import com.digytal.control.model.core.params.banco.BancoResponse;
import com.digytal.control.repository.params.BancoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BancoService {
    @Autowired
    private BancoRepository repository;
    public List<BancoResponse> listar(String nome){
        nome = Objects.toString(nome,"").toUpperCase();
        List<BancoResponse> response = repository.findByNomeContainingOrderByNome(nome).stream().map(i->{
            BancoResponse item= new BancoResponse();
            BeanUtils.copyProperties(i,item);
            return item;
        }).collect(Collectors.toList());
        return response;
    }
}
