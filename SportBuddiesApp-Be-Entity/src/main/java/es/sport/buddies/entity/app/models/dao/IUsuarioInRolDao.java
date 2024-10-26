package es.sport.buddies.entity.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import es.sport.buddies.entity.app.models.entity.UsuarioInRol;
import es.sport.buddies.entity.app.models.entity.UsuarioInRolPk;

public interface IUsuarioInRolDao extends JpaRepository<UsuarioInRol, UsuarioInRolPk> {

}
