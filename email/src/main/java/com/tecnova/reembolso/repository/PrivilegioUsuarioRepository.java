package com.tecnova.reembolso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tecnova.reembolso.entity.PrivilegioUsuario;

@RepositoryRestResource
public interface PrivilegioUsuarioRepository extends JpaRepository<PrivilegioUsuario, Long> {

	@Query(value = "Select * from privilegio_usuario where privilegio = ?", nativeQuery = true)
	public List<PrivilegioUsuario> findUserReembolsoByPrivilegio(String privilegio);

	@Query(value = "select\n" + 
			"pri.id,\n" + 
			"pri.descripcion,\n" + 
			"pri.email,\n" + 
			"pri.id_usuario,\n" + 
			"pri.privilegio,\n" + 
			"pri.user_name\n" + 
			"from boleta bol\n" + 
			"join privilegio_centro_costo cen on cen.id_centro_costo = bol.id_centro_costo\n" + 
			"join privilegio_usuario pri on cen.id_usuario = pri.id_usuario\n" + 
			"where (bol.id_solicitud = ? or ? = 0)\n" + 
			"and pri.privilegio = ?\n" + 
			"group by pri.id,\n" + 
			"pri.descripcion,\n" + 
			"pri.email,\n" + 
			"pri.id_usuario,\n" + 
			"pri.privilegio,\n" + 
			"pri.user_name;", nativeQuery = true)
	public List<PrivilegioUsuario> findUserReembolsoByIdSolicitud(Long idSolicitud1, Long idSolicitud2, String privilegio);
	


}
