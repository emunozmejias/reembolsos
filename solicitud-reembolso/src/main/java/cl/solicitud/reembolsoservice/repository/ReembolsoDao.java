package cl.solicitud.reembolsoservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import cl.solicitud.reembolsoservice.model.SolicitudReembolso;

@Repository
public interface ReembolsoDao {

	public List<SolicitudReembolso> consultaSolicitudbyFiltro(Integer idUsuario, Integer idEstado,
			  Date fechaDesde, Date fechaHasta);
	
}
