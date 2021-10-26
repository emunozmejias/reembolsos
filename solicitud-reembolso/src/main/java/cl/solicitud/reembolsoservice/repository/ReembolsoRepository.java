package cl.solicitud.reembolsoservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.solicitud.reembolsoservice.model.SolicitudReembolso;

@RepositoryRestResource
public interface ReembolsoRepository extends JpaRepository<SolicitudReembolso, Long>{
	
	SolicitudReembolso findByIdSolicitud(Long idSolicitud);	
	
	@Query(value="select sol.id_solicitud, \r\n" + 
			"sol.fecha_solicitud, \r\n" + 
			"sol.observacion,\r\n" +
			"sol.id_estado_reembolso,\r\n" +			
			"sol.id_usuario, \r\n" + 
			"sum(bol.monto_boleta) as monto \r\n" + 
			"from solicitud_reembolso sol \r\n" + 
			"join boleta bol on sol.id_solicitud = bol.id_solicitud\r\n" + 
			"where sol.id_usuario = ? \r\n" +
			"and (sol.fecha_solicitud >= ? or 1=1)\r\n" +
			"group by sol.id_solicitud, \r\n" + 
			"sol.fecha_solicitud, \r\n" + 
			"sol.observacion,\r\n" + 
			"sol.id_usuario \r\n" +
			"order by sol.fecha_solicitud desc \r\n" +
			"limit 5", nativeQuery=true)
	public List<SolicitudReembolso> consultaSolicitudbyUsuario(Long idUsuario, Date fecha);
	
	@Query(value="select * from solicitud_reembolso sol\r\n" + 
			"join estado es on sol.id_estado_reembolso = es.id_estado\r\n" + 
			"where  sol.id_usuario = ?\r\n" + 
			"and es.id_estado = ?\r\n" + 
			"and sol.fecha_solicitud between ? and ?", nativeQuery=true)
	public List<SolicitudReembolso> consultaSolicitudbyFiltro(Integer idUsuario, Integer idEstado,
															  Date fechaDesde, Date fechaHasta);
	
	@Query(value="select sum(bol.monto_boleta) as monto \r\n" + 
			"from boleta bol  \r\n" + 
			"where bol.id_solicitud = ? and id_estado_boleta <> 2 ", nativeQuery=true)
	public Integer montoSolicitud(Long idUsuario);
	

}
