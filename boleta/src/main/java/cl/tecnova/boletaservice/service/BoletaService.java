package cl.tecnova.boletaservice.service;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cl.tecnova.boletaservice.model.Boleta;
import cl.tecnova.boletaservice.model.BoletaJson;
import cl.tecnova.boletaservice.model.BoletaOut;
import cl.tecnova.boletaservice.model.BoletaUpdate;
import cl.tecnova.boletaservice.model.ErrorApi;
import cl.tecnova.boletaservice.model.Parametros;
import cl.tecnova.boletaservice.repository.BoletaRepository;
import cl.tecnova.boletaservice.util.converter.ConverterBoleta;
import cl.tecnova.boletaservice.util.enums.EstadoBoleta;

@Service
public class BoletaService {

	@Autowired
	private BoletaRepository boletaRepository;

	@Autowired
	private LogReembolsoService logReembolsoService;

	private Logger logger = LogManager.getLogger(getClass());

	private ResponseEntity response = null;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private Environment env;

	public ResponseEntity<Collection<Boleta>> obtieneAllBoletas() {
		return new ResponseEntity<>(boletaRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> registraBoleta(Boleta boleta) {
		response = validarBoleta(boleta);
		if(response != null) {
			return response;
		}

		boleta.setIdEstadoBoleta(EstadoBoleta.NO_RENDIDO.getIdEstado());// indica que el estado de la boleta es "creada"

		// validar campos
		boleta.setFechaIngreso(new Date());
		boleta.setIdSolicitud(null);
		boleta.setIdBoleta(null);
		Boleta boletaBD = boletaRepository.save(boleta);
		logReembolsoService.logReembolso(ConverterBoleta.boletaToLogRegistrar(boletaBD));

		return new ResponseEntity<>(boletaBD, HttpStatus.CREATED);

	}

	public ResponseEntity<Boleta> obtieneBoletaById(Long idBoleta) {
		Boleta boleta = boletaRepository.findByIdBoleta(idBoleta);
		BoletaOut boletaOut = new BoletaOut();
		if (boleta != null) {
			boletaOut.setFechaBoleta(formatearFecha(boleta.getFechaBoleta()));
			boletaOut.setFechaIngreso(formatearFecha(boleta.getFechaIngreso()));
			boletaOut.setDetalleBoleta(boleta.getDetalleBoleta());
			boletaOut.setIdBoleta(boleta.getIdBoleta());
			boletaOut.setIdCentroCosto(boleta.getIdCentroCosto());
			boletaOut.setEstadoBoleta(boleta.getEstado().getDescripcion());
			boletaOut.setIdProyecto(boleta.getIdProyecto());
			boletaOut.setIdSolicitud(boleta.getIdSolicitud());
			boletaOut.setTipoBoleta(boleta.getTipoBoleta().getDescripcion());
			boletaOut.setIdUsuario(boleta.getIdUsuario());
			boletaOut.setMontoBoleta(formatearMonto(boleta.getMontoBoleta()));
			boletaOut.setObservacion(boleta.getObservacion());
			boletaOut.setNumeroDocumento(boleta.getNumeroDocumento());
			boletaOut.setIdTipoBoleta(boleta.getIdTipoBoleta());
			response = new ResponseEntity<>(boletaOut, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<Collection<Boleta>> obtieneBoletaByIdUsuario(Long idUsuario, Integer estado) {

		if (estado == null) {
			response = new ResponseEntity<>(boletaRepository.findByIdUsuario(idUsuario), HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(boletaRepository.findByIdUsuarioEstado(idUsuario, estado), HttpStatus.OK);
		}
		return response;
	}

	public ResponseEntity<Collection<BoletaOut>> obtieneBoletaByIdSolicitud(Long idSolicitud) {
		List<Boleta> listaBoletas = boletaRepository.findByIdSolicitud(idSolicitud);
		List<BoletaOut> listaBol = new ArrayList<BoletaOut>();
		for (Boleta bol : listaBoletas) {
			BoletaOut boletaUpdate = new BoletaOut();
			boletaUpdate.setFechaBoleta(formatearFecha(bol.getFechaBoleta()));
			boletaUpdate.setFechaIngreso(formatearFecha(bol.getFechaIngreso()));
			boletaUpdate.setDetalleBoleta(bol.getDetalleBoleta());
			boletaUpdate.setIdBoleta(bol.getIdBoleta());
			boletaUpdate.setIdCentroCosto(bol.getIdCentroCosto());
			boletaUpdate.setEstadoBoleta(bol.getEstado().getDescripcion());
			boletaUpdate.setIdProyecto(bol.getIdProyecto());
			boletaUpdate.setIdSolicitud(bol.getIdSolicitud());
			boletaUpdate.setTipoBoleta(bol.getTipoBoleta().getDescripcion());
			boletaUpdate.setIdUsuario(bol.getIdUsuario());
			boletaUpdate.setMontoBoleta(formatearMonto(bol.getMontoBoleta()));
			boletaUpdate.setObservacion(bol.getObservacion());
			boletaUpdate.setNumeroDocumento(bol.getNumeroDocumento());
			listaBol.add(boletaUpdate);
		}
		response = new ResponseEntity<>(listaBol, HttpStatus.OK);
		return response;
	}

	public ResponseEntity<Collection<BoletaOut>> obtieneBoletaEliminadaByIdSolicitud(Long idSolicitud) {
		List<Boleta> listaBoletas = boletaRepository.findEliminadaByIdSolicitud(idSolicitud);
		List<BoletaOut> listaBol = new ArrayList<BoletaOut>();
		for (Boleta bol : listaBoletas) {
			BoletaOut boletaUpdate = new BoletaOut();
			boletaUpdate.setFechaBoleta(formatearFecha(bol.getFechaBoleta()));
			boletaUpdate.setFechaIngreso(formatearFecha(bol.getFechaIngreso()));
			boletaUpdate.setDetalleBoleta(bol.getDetalleBoleta());
			boletaUpdate.setIdBoleta(bol.getIdBoleta());
			boletaUpdate.setIdCentroCosto(bol.getIdCentroCosto());
			boletaUpdate.setEstadoBoleta(bol.getEstado().getDescripcion());
			boletaUpdate.setIdProyecto(bol.getIdProyecto());
			boletaUpdate.setIdSolicitud(bol.getIdSolicitud());
			boletaUpdate.setTipoBoleta(bol.getTipoBoleta().getDescripcion());
			boletaUpdate.setIdUsuario(bol.getIdUsuario());
			boletaUpdate.setMontoBoleta(formatearMonto(bol.getMontoBoleta()));
			boletaUpdate.setObservacion(bol.getObservacion());
			boletaUpdate.setNumeroDocumento(bol.getNumeroDocumento());
			listaBol.add(boletaUpdate);
		}
		response = new ResponseEntity<>(listaBol, HttpStatus.OK);
		return response;
	}

	public String formatearMonto(Integer value) {
		Locale locale = new Locale("es", "CL");
		DecimalFormat formatoMiles = (DecimalFormat) NumberFormat.getNumberInstance(locale); // ("###,###.##");
		String resultado = formatoMiles.format(value);
		return resultado;
	}

	private String formatearFecha(Date fecha) {
		String fechaFormat = new SimpleDateFormat("dd-MM-yyyy").format(fecha);
		return fechaFormat;
	}

	public boolean existeBoleta(Long idBoleta) {
		return boletaRepository.existsById(idBoleta);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity eliminarBoleta(Long idBoleta) {
		ErrorApi error = new ErrorApi();
		Boleta boleta = boletaRepository.findByIdBoleta(idBoleta);
		if (boleta != null && boleta.getIdEstadoBoleta() != EstadoBoleta.ELIMINADA.getIdEstado()) {
			Boleta antes = boletaRepository.findByIdBoleta(idBoleta);
			boleta.setIdEstadoBoleta(EstadoBoleta.ELIMINADA.getIdEstado());// eliminacion logica para cambio de estado de boleta
			boletaRepository.save(boleta);
			error.setEstado(true);
			error.setDescripcion("Boleta eliminada!");
			response = new ResponseEntity(error, HttpStatus.OK);
			logReembolsoService.logReembolso(ConverterBoleta.boletaToLogEliminar(antes, boleta));
		} else {
			error.setEstado(false);
			error.setDescripcion("Error, Boleta ya se encuentra con estado eliminada!");
			response = new ResponseEntity(error, HttpStatus.valueOf(404));
		}

		return response;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public ResponseEntity actualizaBoleta(Boleta boleta, Long idBoleta) {
		response = validarBoleta(boleta);
		if(response != null) {
			return response;
		}
		ErrorApi error = new ErrorApi();
		Boleta bolUpdate = boletaRepository.findByIdBoleta(idBoleta);

		if (bolUpdate.getIdEstadoBoleta() == EstadoBoleta.ELIMINADA.getIdEstado()) {// se valida que el estado de la boleta no sea eliminada...
			error.setEstado(false);
			error.setDescripcion("Error, el estado de la boleta es eliminada, no se puede actualizar");
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		} else {
			bolUpdate.setDetalleBoleta(boleta.getDetalleBoleta());
			bolUpdate.setMontoBoleta(boleta.getMontoBoleta());
			bolUpdate.setObservacion(boleta.getObservacion());
			bolUpdate.setIdTipoBoleta(boleta.getIdTipoBoleta());
			bolUpdate.setIdEstadoBoleta(boleta.getIdEstadoBoleta());
			bolUpdate.setFechaBoleta(boleta.getFechaBoleta());
			bolUpdate.setNumeroDocumento(boleta.getNumeroDocumento());
			try {
				// Boleta antes = boletaRepository.findByIdBoleta(idBoleta);
				Boleta boletaBD = boletaRepository.save(bolUpdate);
				response = new ResponseEntity<>(boletaBD, HttpStatus.OK);
				// logReembolsoService.logReembolso(ConverterBoleta.boletaToLogActualizar(antes,
				// boletaBD));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				error.setEstado(false);
				error.setDescripcion("Error, inconcistencia de claves foraneas, dato tipoBoleta no existe: " + boleta.getIdTipoBoleta());
				response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
			}
		}

		return response;
	}

	public ResponseEntity asociarSolicitud(Long idSolicitud, Long idUsuario, String fechaSolicitud) {
		ErrorApi error = new ErrorApi();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Integer idSolicitudInt = idSolicitud.intValue();
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fechaSolicitud);
			List<Boleta> bol = boletaRepository.findByfechaSol(idUsuario, fechaDate);

			for (int i = 0; i < bol.size(); i++) {
				bol.get(i).setIdSolicitud(idSolicitudInt);
				bol.get(i).setIdEstadoBoleta(3);// identifica que la boleta esta rendida...
				boletaRepository.save(bol.get(i));
			}
			error.setEstado(true);
			error.setDescripcion("Id de solicitud asociada a boletas");
			response = new ResponseEntity<>(error, HttpStatus.OK);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			error.setEstado(false);
			error.setDescripcion("Error al asociar id de solicitud a boleta id:" + e.getCause().toString());
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		}

		return response;
	}

	// metodo stand by para su pronta modificacion
	public ResponseEntity rechazarSolicitud(List<Boleta> listaBoleta, Long idSolicitud) {
		boolean estado = false;
		ErrorApi error = new ErrorApi();
		try {
			for (Boleta boleta : listaBoleta) {
				if (!boletaRepository.rechazarSolcitud(idSolicitud, boleta.getIdBoleta())) {
					error.setEstado(estado);
					error.setDescripcion("Error al asociar id de solicitud a boleta id:" + boleta.getIdBoleta());
					// response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
					break;
				} else {
					error.setEstado(true);
					error.setDescripcion("Solicitud asociada a boletas");
					response = new ResponseEntity<>(error, HttpStatus.OK);
				}
			}
		} catch (Exception e) {

			error.setDescripcion("Error al asociar id de solicitud a boleta id:" + e.getCause().toString());
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		}

		return response;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public ResponseEntity actualizaEstadoBoleta(BoletaJson listBoleta) {
		ErrorApi error = new ErrorApi();

		for (BoletaUpdate boleta : listBoleta.getListBoleta()) {
			Boleta bolUpdate = boletaRepository.findByIdBoleta(boleta.getIdBoleta());
			if (bolUpdate.getIdEstadoBoleta() == EstadoBoleta.ELIMINADA.getIdEstado()) {
				error.setEstado(false);
				error.setDescripcion("Error, el estado de la boleta es eliminada, no se puede actualizar");
				response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
			} else {
				bolUpdate.setIdEstadoBoleta(boleta.getIdEstadoBoleta());
				if (EstadoBoleta.OBJETADA.getIdEstado() == boleta.getIdEstadoBoleta() || EstadoBoleta.APROBADA.getIdEstado() == boleta.getIdEstadoBoleta()) {
					bolUpdate.setObservacion(boleta.getObservacion());
				}

				try {
					Boleta antes = boletaRepository.findByIdBoleta(boleta.getIdBoleta());
					Boleta boletaBD = boletaRepository.save(bolUpdate);
					response = new ResponseEntity<>(boletaBD, HttpStatus.OK);
					logReembolsoService.logReembolso(ConverterBoleta.boletaToLogActualizar(antes, boletaBD));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					error.setEstado(false);
					error.setDescripcion("Error, inconcistencia de claves foraneas, dato tipoBoleta no existe: " + boleta.getIdBoleta());
					response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
				}
			}
		}
		return response;
	}

	private List<Parametros> obtenerParametros(String tipo, String codigo) {
		List<Parametros> parametros = null;
		try {
			URI uri = UriComponentsBuilder.fromUriString(this.env.getProperty("properties.url.obtenerParametros") + tipo + "/" + codigo).build().encode().toUri();
			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, definirHeadersGet(), String.class);
			if (result.getBody() != null) {
				logger.info(result.getBody().toString());
				parametros = new Gson().fromJson(result.getBody().toString(), new TypeToken<ArrayList<Parametros>>() {
				}.getType());
			}
		} catch (Exception e) {
			logger.info("error: " + e.getCause().getMessage().toString());
		}
		return parametros;
	}

	private HttpEntity<String> definirHeadersGet() {
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(acceptableMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}
	
	private ResponseEntity validarBoleta(Boleta boleta) {
		Calendar hoy = Calendar.getInstance();
		hoy.set(Calendar.MILLISECOND, 0);
		hoy.set(Calendar.SECOND, 0);
		hoy.set(Calendar.MINUTE, 0);
		hoy.set(Calendar.HOUR, 0);
		int dias = (int) ((hoy.getTime().getTime() - boleta.getFechaBoleta().getTime()) / 86400000);
		List<Parametros> parametros = this.obtenerParametros("BOLETA", null);
		if (parametros != null && !parametros.isEmpty()) {
			Parametros reglaDias = parametros.stream().filter(p -> "REGLA_DIAS".equals(p.getId().getCodigo()) && "S".equals(p.getVigencia())).findFirst().orElse(null);
			Parametros diasCantidad = parametros.stream().filter(p -> "REGLA_DIAS_CANTIDAD".equals(p.getId().getCodigo()) && "S".equals(p.getVigencia())).findFirst().orElse(null);
			if (reglaDias != null && diasCantidad != null && "S".equals(reglaDias.getDescripcion()) && dias > Integer.parseInt(diasCantidad.getDescripcion())) {
				ErrorApi error = new ErrorApi();
				error.setEstado(true);
				error.setDescripcion("fechaFail");				
				return new ResponseEntity<>(error, HttpStatus.valueOf(500));
			}
			Parametros reglaUltima = parametros.stream().filter(p -> "REGLA_ULTIMA".equals(p.getId().getCodigo()) && "S".equals(p.getVigencia())).findFirst().orElse(null);
			if (reglaUltima != null && "S".equals(reglaDias.getDescripcion())) {
				List<Boleta> ultimas = boletaRepository.findUltimaBoleta(boleta.getIdUsuario(), boleta.getFechaBoleta());
				if(ultimas != null && !ultimas.isEmpty()) {
					ErrorApi error = new ErrorApi();
					error.setEstado(true);
					error.setDescripcion("fechaUltima");				
					return new ResponseEntity<>(error, HttpStatus.valueOf(500));
				}
			}
		}
		return null;
	}
}
