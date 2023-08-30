package com.digytal.control.webservice.core.publico;


import com.digytal.control.infra.http.response.Response;
import com.digytal.control.infra.http.response.ResponseFactory;
import com.digytal.control.infra.model.CredencialResponse;
import com.digytal.control.infra.model.LoginRequest;
import com.digytal.control.model.core.acessos.usuario.SenhaRequest;
import com.digytal.control.model.core.comum.cadastratamento.CadastroSimplificadoRequest;
import com.digytal.control.service.core.acessos.EmpresaService;
import com.digytal.control.service.core.acessos.LoginService;
import com.digytal.control.service.core.acessos.UsuarioService;
import com.digytal.control.service.core.cadastros.cadastro.CadastroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicoResource {
    @Autowired
    private CadastroService cadastroService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private EmpresaService empresaService;


    @PostMapping("/login")
    public Response logar(@RequestBody LoginRequest login){
        return ResponseFactory.ok(loginService.autenticar(login),"Login realizado com sucesso");
    }
    @PatchMapping("/alteracao-senha/{expiracao}")
    public Response alterar(@PathVariable("expiracao")Long expiracao, @RequestBody SenhaRequest request){
        return ResponseFactory.ok(usuarioService.alterarSenha(expiracao, request),"Senha alterada com sucesso");
    }
    @PostMapping("/solicitacao-nova-senha/id/{id}")
    public Response solicitarNovaSenha(@PathVariable("id")Integer id){
        return ResponseFactory.ok(usuarioService.solicitarNovaSenha(id),"Solicitação de nova senha realizada com sucesso");
    }
    @PostMapping("/solicitacao-nova-senha/login/{login}")
    public Response solicitarNovaSenha(@PathVariable("login")String login){
        return ResponseFactory.ok(usuarioService.solicitarNovaSenha(login),"Solicitação de nova senha realizada com sucesso");
    }
    @PostMapping("/empresa/primeiro-acesso/{cpfCnpj}")
    public Response realizarPrimeiroAcessoEmpresa(@PathVariable("cpfCnpj") String cpfCnpj, @RequestBody CadastroSimplificadoRequest request){
        CredencialResponse response = empresaService.configurarPrimeiroAcesso(cpfCnpj, request);
        return ResponseFactory.ok(response,"Primeiro acesso realizado com sucesso, confira sua caixa de e-mail");
    }
    @PostMapping("/cadastro/primeiro-acesso")
    public Response realizarPrimeiroAcessoCadastro(@RequestBody CadastroSimplificadoRequest request){
        CredencialResponse response = cadastroService.configurarPrimeiroAcesso(request);
        return ResponseFactory.ok(response,"Primeiro acesso realizado com sucesso, confira sua caixa de e-mail");
    }


}
