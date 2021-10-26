package cl.tecnova.notificaciones.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.tecnova.notificaciones.service.model.Notificaciones;

public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long>{
		
	@Query(value = "Select * from notificaciones where id_usuario = ?", nativeQuery=true)
	List<Notificaciones> obtieneToken(Integer usuario);
	
	@Query(value = "Select * from notificaciones where token = ?", nativeQuery=true)
	Notificaciones obtieneTokens(String token);

}
