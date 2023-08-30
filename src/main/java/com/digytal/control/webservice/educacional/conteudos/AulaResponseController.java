package com.digytal.control.webservice.educacional.conteudos;

import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.model.educacional.conteudos.aula.AulaResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaResponseController {
    @GetMapping()
    public Response incluirConta(){
        List<AulaResponse> lista = new ArrayList<>();
        lista.add(new AulaResponse(1,"03:02","Trilha de estudos","Descubra e escolha a sua melhor trilha de estudos","https://player-vz-376af286-3ec.tv.pandavideo.com.br/embed/?v=07d611b7-7ef3-40d3-91a3-35d59c220a76"));
        lista.add(new AulaResponse(2,"01:24","Instalando o Git no Windows","Vídeo ilustrando a instalação do ferramenta de versionamento Git","https://player-vz-376af286-3ec.tv.pandavideo.com.br/embed/?v=c2a4863c-cbfb-443b-8a11-a9142a90831e"));

        return ResponseFactory.ok(lista,"Consulta realizada com sucesso");
    }
}
