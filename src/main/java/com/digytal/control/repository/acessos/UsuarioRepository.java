package com.digytal.control.repository.acessos;

import com.digytal.control.model.core.acessos.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    boolean existsByLogin(String login);

    UsuarioEntity findByLogin(String login);

    UsuarioEntity findByCadastro(Integer cadastro);

    UsuarioEntity findByLoginOrEmail(String login,String email);

    @Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN 'true' ELSE 'false' END CONTAGEM FROM  apl_acessos.tab_usuario_empresa e WHERE e.usuario_id = ? AND e.empresa_id = ? ", nativeQuery = true)
    boolean hasEmpresa(Integer usuario, Integer empresa);
}
