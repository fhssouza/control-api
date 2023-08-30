package com.digytal.control.integracao.asaas;

import com.digytal.control.infra.business.BusinessException;
import com.digytal.control.infra.business.IntegracaoException;
import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.integracao.asaas.model.*;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class IntegradorPagamento {
    @Autowired
    private ObjectMapper mapper;
    @Value("${asaas.url}")
    private String root;
    @Value("${asaas.token}")
    private String token;

    private RestTemplate template = new RestTemplate();
    public Cadastro obterCadastro(String id){
        HttpEntity<String> entity = new HttpEntity<String>(null,headers());
        String url = root+"/customers?id={id}&deleted=false";
        ResponseEntity<Cadastro> response = template.exchange(url, HttpMethod.GET, entity, Cadastro.class,id);
        Cadastro cadastro = response.getBody();
        return cadastro;
    }
    public Cadastro cadastrar(Cadastro cadastro){
        HttpEntity<Cadastro> entity = new HttpEntity<Cadastro>(cadastro,headers());
        String url = root+"/customers";
        ResponseEntity<Cadastro> response = template.exchange(url, HttpMethod.POST, entity, Cadastro.class);;
        return response.getBody();
    }
    public BoletoResponse gerarBoleto(BoletoRequest boleto){
        try{
            HttpEntity<BoletoRequest> entity = new HttpEntity<BoletoRequest>(boleto,headers());
            String url = root+"/payments";
            ResponseEntity<BoletoResponse> response = template.exchange(url, HttpMethod.POST, entity, BoletoResponse.class);;
            return response.getBody();
        }catch (HttpClientErrorException httpex) {
            String message="";
            try {
                JsonNode node = mapper.readTree(httpex.getResponseBodyAsString());
                message = node.get("errors").get(0).get("description").asText();
                throw new IntegracaoException(message);
            } catch (JacksonException e) {
                throw new BusinessException();
            }

        }catch (Exception httpex){
            throw new BusinessException();
        }
    }
    public List<String> listarNotificacoes(String clienteCodigoIntegracao){
        try{
            List<String> ids = new ArrayList<>();
            HttpEntity<BoletoRequest> entity = new HttpEntity<BoletoRequest>(headers());
            String url = root+"/customers/"+clienteCodigoIntegracao+"/notifications";
            ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            Iterator<JsonNode> notificacoes = root.get("data").iterator();
            while (notificacoes.hasNext()){
                ids.add(notificacoes.next().get("id").asText());
            }
            return ids;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException();
        }
    }
    public void desativarNotificacoes(String clienteCodigoIntegracao){
        try{
            List<String> notificacoes = listarNotificacoes(clienteCodigoIntegracao);
            NotificacaoLote lote = new NotificacaoLote();
            lote.setCustomer(clienteCodigoIntegracao);
            for(String id: notificacoes){
                lote.getNotifications().add(new Notificacao(id));
            }
            HttpEntity<NotificacaoLote> entity = new HttpEntity<>(lote,headers());
            String url = root+"/notifications/batch";
            ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException();
        }
    }
    public BoletoResponse obterBoleto(String id){
        try{
            HttpEntity<String> entity = new HttpEntity<String>(null,headers());
            String url = root+"/payments?id={id}";
            ResponseEntity<BoletoResponse> response = template.exchange(url, HttpMethod.GET, entity, BoletoResponse.class, id);;
            return response.getBody();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("access_token", token);
        return headers;
    }
}
