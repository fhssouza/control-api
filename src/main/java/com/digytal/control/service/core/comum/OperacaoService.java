package com.digytal.control.service.core.comum;


import com.digytal.control.infra.business.RegistroIncompativelException;
import com.digytal.control.infra.business.RegistroNaoLocalizadoException;
import com.digytal.control.infra.commons.validation.Entities;
import com.digytal.control.infra.commons.validation.Validations;
import com.digytal.control.model.core.acessos.empresa.EmpresaEntity;
import com.digytal.control.model.core.acessos.usuario.UsuarioEntity;
import com.digytal.control.model.core.comum.RegistroParteRequest;
import com.digytal.control.repository.acessos.EmpresaRepository;
import com.digytal.control.repository.acessos.UsuarioRepository;
import com.digytal.control.repository.cadastros.CadastroRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.digytal.control.infra.commons.validation.Attributes.*;

public abstract class OperacaoService {
    @Autowired
    protected CadastroRepository cadastroRepository;
    @Autowired
    protected UsuarioRepository usuarioRepository;
    @Autowired
    protected EmpresaRepository empresaRepository;
    protected EmpresaEntity validarPartes(RegistroParteRequest partes, boolean exigeCadastro){
        Validations.build(EMPRESA,USUARIO).notEmpty().check(partes);

        if(exigeCadastro){
            Validations.build(CADASTRO).notEmpty().check(partes);

            if( !cadastroRepository.existsById(partes.getCadastro()))
                throw new RegistroNaoLocalizadoException(Entities.CADASTRO_ENTITY, ID);
        }

        UsuarioEntity usuario = null;
        if(partes.getUsuario()!=null) {
            usuario= usuarioRepository.findById(partes.getUsuario()).orElseThrow(()-> new RegistroNaoLocalizadoException(Entities.USUARIO_ENTITY, ID));
        }else{
            usuario = usuarioRepository.findByCadastro(partes.getCadastro());
            if(usuario==null)
                throw new RegistroNaoLocalizadoException(Entities.USUARIO_ENTITY, ID);
            partes.setUsuario(usuario.getId());
        }

        if(usuario.getCadastro()==null) {
            if (!usuarioRepository.hasEmpresa(partes.getUsuario(), partes.getEmpresa()))
                throw new RegistroIncompativelException("Este usuário não possui acesso para realizar lançamentos para esta empresa");
        }

        EmpresaEntity empresaEntity = empresaRepository.findById(partes.getEmpresa())
                .orElseThrow(()->  new RegistroNaoLocalizadoException(Entities.EMPRESA_ENTITY, ID));

        return empresaEntity;

    }
}
