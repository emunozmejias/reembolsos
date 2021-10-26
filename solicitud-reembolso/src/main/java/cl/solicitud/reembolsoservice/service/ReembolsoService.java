package cl.solicitud.reembolsoservice.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.solicitud.reembolsoservice.enums.EstadoSolicitud;
import cl.solicitud.reembolsoservice.model.ErrorApi;
import cl.solicitud.reembolsoservice.model.Estado;
import cl.solicitud.reembolsoservice.model.FiltroBusqueda;
import cl.solicitud.reembolsoservice.model.LogReembolsoEntity;
import cl.solicitud.reembolsoservice.model.Solicitud;
import cl.solicitud.reembolsoservice.model.SolicitudReembolso;
import cl.solicitud.reembolsoservice.repository.ReembolsoDao;
import cl.solicitud.reembolsoservice.repository.ReembolsoRepository;
import cl.solicitud.reembolsoservice.util.converter.ConverterReembolso;

@Service
public class ReembolsoService {

	@Autowired
	private ReembolsoRepository reembolsoRepository;
	
	@Autowired
	private ReembolsoDao dao;
	
	@Autowired
	private LogReembolsoService logReembolsoService;
	
	private Logger logger = LogManager.getLogger(getClass());

	
	private ResponseEntity response = null;
	
	public ResponseEntity<?> registraSolicitud(SolicitudReembolso solicitud){
		solicitud.setIdEstadoReembolso(3);
		
		solicitud.setFechaSolicitud(new Date());
		
		SolicitudReembolso solicitudBD = reembolsoRepository.save(solicitud);
		logReembolsoService.logReembolso(ConverterReembolso.reembolsoToLogRegistrar(solicitudBD));
		return new ResponseEntity<>(solicitudBD, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Collection<SolicitudReembolso>> obtieneAllSolicitudes(){
		return new ResponseEntity<>(reembolsoRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Collection<Solicitud>> obtieneSolicitudesByUsuario(Long idUsuario, String fecha) throws ParseException{
		List<Solicitud> solicitudesJson = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateDesde = null;
		Date dateHasta = null;
		
		System.out.println("fecha desde: " + fecha);
		if (!fecha.equals("undefined")) {
			dateDesde = formatter.parse(fecha);
			System.out.println("fecha desde: " + dateDesde.toString());
		}

		List<SolicitudReembolso> solicitudes = reembolsoRepository.consultaSolicitudbyUsuario(idUsuario, dateDesde);
		for (SolicitudReembolso solicitud : solicitudes) {
			Solicitud sol = new Solicitud();
			Integer monto = reembolsoRepository.montoSolicitud(solicitud.getIdSolicitud());
			sol.setMonto(formatearMonto(monto));
			sol.setFechaSolicitud(formatearFecha(solicitud));
			sol.setEstadoReembolso(solicitud.getEstado().getDescripcion());
			sol.setIdSolicitud(solicitud.getIdSolicitud());
			sol.setIdUsuario(solicitud.getIdUsuario());
			sol.setObservacion(solicitud.getObservacion());
			solicitudesJson.add(sol);
		}
		
		return new ResponseEntity<>(solicitudesJson, HttpStatus.OK);
	}
		
	public ResponseEntity<Collection<Solicitud>> obtieneSolicitudesByFiltro(FiltroBusqueda filtro) throws ParseException{
		
		System.out.println("usuario: " + filtro.getIdUsuario());		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateDesde = null;
		Date dateHasta = null;
		
		System.out.println("fecha desde: " + filtro.getFechaDesde());
		System.out.println("fecha hasta: " + filtro.getFechaHasta());
		if (!filtro.getFechaDesde().equals("undefined")) {
			dateDesde = formatter.parse(filtro.getFechaDesde());
			System.out.println("fecha desde: " + dateDesde.toString());
		}
		
		if (!filtro.getFechaHasta().equals("undefined")) {
			dateHasta = formatter.parse(filtro.getFechaHasta());
			System.out.println("fecha hasta: " + dateHasta.toString());
		}
		
		List<Solicitud> solicitudesJson = new ArrayList<>();
		for (Estado estado: filtro.getEstado()) {
			List<SolicitudReembolso> listSolicitudes = dao.consultaSolicitudbyFiltro(Integer.parseInt(filtro.getIdUsuario()),
					estado.getIdEstado(), 
					dateDesde, dateHasta);
			System.out.println("tamaño: " + listSolicitudes.size());
			/*List<SolicitudReembolso> solicitudes = reembolsoRepository.consultaSolicitudbyFiltro(idUsuario, idEstado,
					dateDesde, dateHasta);*/
			for (SolicitudReembolso solicitud : listSolicitudes) {
				Solicitud sol = new Solicitud();			
				sol.setFechaSolicitud(formatearFecha(solicitud));
				sol.setEstadoReembolso(solicitud.getEstado().getDescripcion());
				sol.setIdSolicitud(solicitud.getIdSolicitud());
				sol.setObservacion(solicitud.getObservacion());
				sol.setIdUsuario(solicitud.getIdUsuario());
				Integer monto = reembolsoRepository.montoSolicitud(solicitud.getIdSolicitud());
				sol.setMonto(formatearMonto(monto));
				solicitudesJson.add(sol);
			}
		}
		
		
		return new ResponseEntity<>(solicitudesJson, HttpStatus.OK);
	}

	private String formatearFecha(SolicitudReembolso solicitud) {
		String fechaFormat = new SimpleDateFormat("dd-MM-yyyy").format(solicitud.getFechaSolicitud());
		return fechaFormat;
	}
	
	
	public ResponseEntity actualizaSolicitud(SolicitudReembolso solicitud, Long idSolicitud, Integer idUsuario) {
		SolicitudReembolso solUpdate = reembolsoRepository.findByIdSolicitud(idSolicitud);
		if (solUpdate != null) {			
			solUpdate.setObservacion(solicitud.getObservacion());
			solUpdate.setIdEstadoReembolso(solicitud.getIdEstadoReembolso());
			try {
				SolicitudReembolso antes = reembolsoRepository.findByIdSolicitud(idSolicitud);
				SolicitudReembolso solBD = reembolsoRepository.save(solUpdate);
				response = new ResponseEntity<>(solBD, HttpStatus.OK);
				logReembolsoService.logReembolso(ConverterReembolso.reembolsoToLogActualizar(antes, solBD, idUsuario));
			}catch (Exception e) {
				System.out.println("Error "+ e.getMessage());
				ErrorApi error = new ErrorApi();
				error.setEstado(false);
				error.setDescripcion("Error al actualizar la solicitud");
				response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
			}
		}else {
			ErrorApi error = new ErrorApi();
			error.setEstado(false);
			error.setDescripcion("Error al actualizar la solicitud " + solicitud.getIdSolicitud());
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		}
		
		return response;
	}
	
	public ResponseEntity eliminaSolicitud(Long idSolicitud) {
		SolicitudReembolso solicitud = reembolsoRepository.findByIdSolicitud(idSolicitud);
		if (solicitud != null) {
			solicitud.setIdEstadoReembolso(2);
			SolicitudReembolso antes = reembolsoRepository.findByIdSolicitud(idSolicitud);
			SolicitudReembolso solBD = reembolsoRepository.save(solicitud);
			response = new ResponseEntity(HttpStatus.OK);
			logReembolsoService.logReembolso(ConverterReembolso.reembolsoToLogEliminar(antes, solBD));
		}else {
			response = ResponseEntity.noContent().build();
		}
		return response;
	}
	
	public ResponseEntity actualizaEstadoSolicitud(Long idSolicitud, Integer idEstado, String motivo) {
		SolicitudReembolso solUpdate = reembolsoRepository.findByIdSolicitud(idSolicitud);
		solUpdate.setIdEstadoReembolso(idEstado);
		solUpdate.setObservacion(motivo);
		try {
			response = new ResponseEntity<>(reembolsoRepository.save(solUpdate), HttpStatus.OK);
		}catch (Exception e) {
			System.out.println("Error "+ e.getMessage());
			ErrorApi error = new ErrorApi();
			error.setEstado(false);
			error.setDescripcion("Error al actualizar la solicitud");
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		}
		return response;
	}
	
	public String formatearMonto(Integer value) {
		Locale locale  = new Locale("es","CL");
		DecimalFormat formatoMiles = (DecimalFormat) NumberFormat.getNumberInstance(locale); //("###,###.##");
		String resultado = formatoMiles.format(value);
		return resultado;
	}
	
	public ResponseEntity obtieneSolicitud(Long idSolicitud){
		SolicitudReembolso solicitud = reembolsoRepository.findByIdSolicitud(idSolicitud);
		Solicitud sol = new Solicitud();
		sol.setEstadoReembolso(solicitud.getEstado().getDescripcion());
		sol.setFechaSolicitud(formatearFecha(solicitud));
		sol.setIdSolicitud(solicitud.getIdSolicitud());
		sol.setObservacion(solicitud.getObservacion());
		return new ResponseEntity<>(sol, HttpStatus.OK);
	}
		
	public ResponseEntity<Collection<LogReembolsoEntity>> obtieneHistorialSolicitud(Long idSolicitud){
		List<LogReembolsoEntity> listaHistoria = logReembolsoService.obtieneHistorialSolicitud(idSolicitud);
		List<LogReembolsoEntity> listaHistoriaOut = new ArrayList<LogReembolsoEntity>();
		for (LogReembolsoEntity log: listaHistoria) {
			LogReembolsoEntity logReemboso = log;
			String estadoSolicitud = log.getLogDespues().split("idEstadoReembolso=")[1].split(",")[0];

			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");			
			System.out.println(sdf.format(logReemboso.getFecha()));
			
			logReemboso.setLogDespues(estadoSolicitud);
			listaHistoriaOut.add(logReemboso);			
		}
		return new ResponseEntity<>(listaHistoriaOut, HttpStatus.OK);
	}
	
	
}
