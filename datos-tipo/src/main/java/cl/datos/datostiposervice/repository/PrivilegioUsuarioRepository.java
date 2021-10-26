package cl.datos.datostiposervice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.datos.datostiposervice.model.PrivilegioUsuario;

@Repository
public interface PrivilegioUsuarioRepository extends JpaRepository<PrivilegioUsuario, Integer>{

	@Query(value = "select * from privilegio_usuario pri where pri.id_usuario = ?", nativeQuery=true)
	public PrivilegioUsuario obtienePrivilegio(Integer usuario);
	
	@Query(value = "select * from privilegio_usuario", nativeQuery=true)
	public List<PrivilegioUsuario> obtieneAllPrivilegioUsuario();
}
