package com.digytal.control.service.core.acessos;


import com.digytal.control.infra.business.login.LoginException;
import com.digytal.control.infra.business.login.SenhaExpiradaException;
import com.digytal.control.infra.business.login.UsuarioBloqueadoException;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.infra.model.*;
import com.digytal.control.infra.security.jwt.JwtCreator;
import com.digytal.control.infra.security.jwt.JwtObject;
import com.digytal.control.infra.security.jwt.SecurityConfig;
import com.digytal.control.model.core.acessos.usuario.UsuarioEntity;
import com.digytal.control.repository.acessos.EmpresaRepository;
import com.digytal.control.repository.acessos.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static com.digytal.control.infra.commons.validation.Attributes.*;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder encoder;

    public SessaoResponse autenticar(LoginRequest login){
        Validations.build(USUARIO, SENHA).notEmpty().check(login);

        String username = login.getUsuario().toLowerCase();
        UsuarioEntity entity = repository.findByLogin(username);
        if(entity!=null){

            boolean passwordOk = encoder.matches(login.getSenha(), entity.getSenha());
            if (!passwordOk) {
                throw new LoginException();
            }
            if(entity.isExpirado())
                throw new SenhaExpiradaException();

            if(entity.isBloqueado())
                throw new UsuarioBloqueadoException();

            if(entity.isExpirado())
                throw new SenhaExpiradaException();

            if(entity.isBloqueado())
                throw new UsuarioBloqueadoException();

            final int HORAS_EXPIRACAO = 4;

            SessaoResponse session = new SessaoResponse();

            session.setInicioSessao(LocalDateTime.now());
            session.setFimSessao(session.getInicioSessao().plusHours(HORAS_EXPIRACAO));
            if(entity.getCadastro()!=null){
                UsuarioCadastroResponse response = new UsuarioCadastroResponse();
                BeanUtils.copyProperties(entity, response);
                response.setCadastro(entity.getCadastro());
                session.setUsuario(response);
            }else{
                UsuarioEmpresaResponse response = new UsuarioEmpresaResponse();
                BeanUtils.copyProperties(entity, response);
                response.setEmpresas(empresaRepository.listarEmpresas(entity.getId()));
                session.setUsuario(response);
            }
            JwtObject jwtObject = JwtObject.builder()
                    .subject(login.getUsuario())
                    .issuedAt()
                    .expirationHours(HORAS_EXPIRACAO)
                    .roles("ADMIN");

            session.setToken(JwtCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));

            return session;

        }else
            throw new LoginException();
    }
}
