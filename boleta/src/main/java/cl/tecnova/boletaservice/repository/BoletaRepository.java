package cl.tecnova.boletaservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.tecnova.boletaservice.model.Boleta;

@RepositoryRestResource
public interface BoletaRepository extends JpaRepository<Boleta, Long> {

	Boleta findByIdBoleta(Long idBoleta);

	boolean deleteByIdBoleta(int idBoleta);

	@Query(value = "select * from boleta where id_usuario = ? and fecha_boleta <= ? and id_solicitud is null and id_estado_boleta <> 2", nativeQuery = true)
	List<Boleta> findByfechaSol(Long idUsuario, Date fechaSolicitud);

	@Query(value = "update boleta set id_solicitud=? where id_boleta = ?", nativeQuery = true)
	public boolean rechazarSolcitud(Long idSolicitud, Long idBoleta);

	@Query(value = "update boleta set id_solicitud=? where id_usuario = ? and fecha_boleta <= ?", nativeQuery = true)
	public void asociarSolicitud(Long idSolicitud, Long idUsuario, Date fechaSolicitud);

	@Query(value = "select * from boleta where id_usuario = ? and id_estado_boleta <> 2 order by fecha_boleta desc;", nativeQuery = true)
	List<Boleta> findByIdUsuario(Long idUsuario);

	@Query(value = "select * from boleta where id_usuario = ? and id_estado_boleta = ? and id_solicitud is null order by fecha_boleta desc;", nativeQuery = true)
	List<Boleta> findByIdUsuarioEstado(Long idUsuario, Integer estado);

	@Query(value = "select * from boleta where id_solicitud = ? and id_estado_boleta <> 2 order by fecha_boleta desc ", nativeQuery = true)
	List<Boleta> findByIdSolicitud(Long idUsuario);

	@Query(value = "select * from boleta where id_solicitud = ? and id_estado_boleta = 2 order by fecha_boleta desc ", nativeQuery = true)
	List<Boleta> findEliminadaByIdSolicitud(Long idUsuario);

	@Query(value = "select * from boleta where id_solicitud is not null and id_usuario=? and fecha_boleta>? order by fecha_boleta desc", nativeQuery = true)
	List<Boleta> findUltimaBoleta(Integer idUsuario, Date fechaBoleta);

}
