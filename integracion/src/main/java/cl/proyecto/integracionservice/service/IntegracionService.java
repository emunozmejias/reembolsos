package cl.proyecto.integracionservice.service;

import java.lang.reflect.Type;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cl.proyecto.integracionservice.constantes.Constantes;
import cl.proyecto.integracionservice.model.Boleta;
import cl.proyecto.integracionservice.model.BoletaOut;
import cl.proyecto.integracionservice.model.Estado;
import cl.proyecto.integracionservice.model.EstadoServicio;
import cl.proyecto.integracionservice.model.FiltroBusqueda;
import cl.proyecto.integracionservice.model.LogReembolsoEntity;
import cl.proyecto.integracionservice.model.LogSolicitud;
import cl.proyecto.integracionservice.model.Notificacion;
import cl.proyecto.integracionservice.model.Notificaciones;
import cl.proyecto.integracionservice.model.Notification;
import cl.proyecto.integracionservice.model.Proyecto;
import cl.proyecto.integracionservice.model.ResultadoMensaje;
import cl.proyecto.integracionservice.model.Solicitud;
import cl.proyecto.integracionservice.model.SolicitudReembolso;
import cl.proyecto.integracionservice.model.TokenSeguridad;
import cl.proyecto.integracionservice.model.User;
import cl.proyecto.integracionservice.model.Usuario;

@Service
public class IntegracionService {

	private Logger logger = LogManager.getLogger(getClass());
	private RestTemplate restTemplate = new RestTemplate();
	private ResponseEntity response = null;

	@Autowired
	private Environment env;

	public ResponseEntity<Collection<Boleta>> obtieneBoletaByIdSolicitud(Long idSolicitud) {
		String urlObtenerListaBoletas = this.env.getProperty("properties.url.obtieneBoletaSolicitud") + idSolicitud;
		List<Boleta> listaUpdate = new ArrayList<Boleta>();
		Type listType = new TypeToken<ArrayList<BoletaOut>>() {
		}.getType();
		List<BoletaOut> listaBoletas = new Gson().fromJson(this.callService(urlObtenerListaBoletas), listType);
		Gson gson = new Gson();
		for (BoletaOut bol : listaBoletas) {
			Boleta boletaUpdate = new Boleta();
			Proyecto proyect = new Proyecto();

			if (bol.getIdProyecto() != null && bol.getIdProyecto() > 0) {
				proyect = gson.fromJson(obtenerProyectoByIdProyecto(bol.getIdProyecto()), Proyecto.class);
				if (proyect != null) {
					boletaUpdate.setNombreProyecto(proyect.getNombreProyecto());
				}
			} else {
				boletaUpdate.setNombreProyecto("");
			}
			boletaUpdate.setDetalleBoleta(bol.getDetalleBoleta());
			boletaUpdate.setFechaBoleta(bol.getFechaBoleta());
			boletaUpdate.setFechaIngreso(bol.getFechaIngreso());
			boletaUpdate.setEstadoBoleta(bol.getEstadoBoleta());
			boletaUpdate.setIdBoleta(bol.getIdBoleta());
			boletaUpdate.setIdCentroCosto(bol.getIdCentroCosto());
			boletaUpdate.setIdSolicitud(bol.getIdSolicitud());
			boletaUpdate.setIdUsuario(bol.getIdUsuario());
			boletaUpdate.setMontoBoleta(bol.getMontoBoleta());
			boletaUpdate.setObservacion(bol.getObservacion());
			boletaUpdate.setTipoBoleta(bol.getTipoBoleta());
			boletaUpdate.setIdProyecto(bol.getIdProyecto());
			if (proyect != null) {
				boletaUpdate.setPresupuestoProyecto(proyect.getPresupuesto());
			}

			boletaUpdate.setNumeroDocumento(bol.getNumeroDocumento());

			listaUpdate.add(boletaUpdate);
		}
		response = new ResponseEntity<>(listaUpdate, HttpStatus.OK);
		return response;
	}

	public ResponseEntity<Collection<Boleta>> obtieneBoletaEliminadaByIdSolicitud(Long idSolicitud) {
		String urlObtenerListaBoletas = this.env.getProperty("properties.url.obtieneBoletaEliminadaSolicitud")
				+ idSolicitud;
		List<Boleta> listaUpdate = new ArrayList<Boleta>();
		Type listType = new TypeToken<ArrayList<BoletaOut>>() {
		}.getType();
		List<BoletaOut> listaBoletas = new Gson().fromJson(this.callService(urlObtenerListaBoletas), listType);
		Gson gson = new Gson();
		for (BoletaOut bol : listaBoletas) {
			Boleta boletaUpdate = new Boleta();
			Proyecto proyect = new Proyecto();

			if (bol.getIdProyecto() != null && bol.getIdProyecto() > 0) {
				proyect = gson.fromJson(obtenerProyectoByIdProyecto(bol.getIdProyecto()), Proyecto.class);
				boletaUpdate.setNombreProyecto(proyect.getNombreProyecto());
			} else {
				boletaUpdate.setNombreProyecto("");
			}
			boletaUpdate.setDetalleBoleta(bol.getDetalleBoleta());
			boletaUpdate.setFechaBoleta(bol.getFechaBoleta());
			boletaUpdate.setFechaIngreso(bol.getFechaIngreso());
			boletaUpdate.setEstadoBoleta(bol.getEstadoBoleta());
			boletaUpdate.setIdBoleta(bol.getIdBoleta());
			boletaUpdate.setIdCentroCosto(bol.getIdCentroCosto());
			boletaUpdate.setIdSolicitud(bol.getIdSolicitud());
			boletaUpdate.setIdUsuario(bol.getIdUsuario());
			boletaUpdate.setMontoBoleta(bol.getMontoBoleta());
			boletaUpdate.setObservacion(bol.getObservacion());
			boletaUpdate.setTipoBoleta(bol.getTipoBoleta());
			boletaUpdate.setIdProyecto(bol.getIdProyecto());
			boletaUpdate.setPresupuestoProyecto(proyect.getPresupuesto());

			boletaUpdate.setNumeroDocumento(bol.getNumeroDocumento());

			listaUpdate.add(boletaUpdate);
		}
		response = new ResponseEntity<>(listaUpdate, HttpStatus.OK);
		return response;
	}

	public String obtenerProyectoByIdProyecto(Integer idProyecto) {
		String urlObtenerProyecto = this.env.getProperty("properties.url.obtieneProyectoByIdProyecto") + idProyecto;
		return this.callService(urlObtenerProyecto);
	}

	public String transformToJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.info("Exception: " + e.getMessage().toString());
		}
		return null;
	}

	public ResponseEntity<Collection<SolicitudReembolso>> obtenerListaSolicitdes(FiltroBusqueda filtro) {
		// GsonJsonParser json = new GsonJsonParser();
		List<SolicitudReembolso> listaJson = new ArrayList<SolicitudReembolso>();
		String obtieneSolicitudesByFiltro = this.env.getProperty("properties.url.obtieneSolicitudesByFiltro");

		Type listType = new TypeToken<ArrayList<Solicitud>>() {
		}.getType();
		List<Solicitud> lista = new Gson()
				.fromJson(this.callServicePost(obtieneSolicitudesByFiltro, transformToJsonString(filtro)), listType);
		Gson gson = new Gson();

		// List<Object> list =
		// json.parseList(this.callService(obtieneSolicitudesByFiltro));
		if (lista != null && !lista.isEmpty()) {
			for (Solicitud sol : lista) {
				SolicitudReembolso solUpdate = new SolicitudReembolso();
				Usuario user = null;
				if (sol.getIdUsuario() > 0) {
					user = gson.fromJson(obtenerUsuarioByIdUsuario(String.valueOf(sol.getIdUsuario())), Usuario.class);
					solUpdate.setNombreUsuario(user.getNombreCompleto());
				} else {
					solUpdate.setNombreUsuario("usuario de prueba desa");
				}
				solUpdate.setFechaSolicitud(sol.getFechaSolicitud());
				solUpdate.setIdSolicitud(sol.getIdSolicitud());
				solUpdate.setMonto(sol.getMonto());
				solUpdate.setObservacion(sol.getObservacion());
				solUpdate.setEstadoReembolso(sol.getEstadoReembolso());
				listaJson.add(solUpdate);
			}
		}
		// this.obtenerUsuarioByIdUsuario("940");
		response = new ResponseEntity<>(listaJson, HttpStatus.OK);
		return response;
	}

	public String obtenerUsuarioByIdUsuario(String idUsuario) {
		String urlObtenerUsuario = this.env.getProperty("properties.url.obtieneUsuarioByIdUsuario") + idUsuario;
		return this.callService(urlObtenerUsuario);
	}

	private static HttpEntity<String> definirHeadersPost() {
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(acceptableMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;
	}

	public String callService(String urlService) {
		String resultado = "";
		try {
			URI uri = UriComponentsBuilder.fromUriString(urlService).build().encode().toUri();
			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, definirHeadersGet(),
					String.class);
			if (result.getBody() != null) {
				logger.info(result.getBody().toString());
				resultado = result.getBody().toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getCause().getMessage().toString());
		}
		return resultado;
	}

	public String callServicePost(String urlService, String json) {
		logger.info("JsonConsulta: " + json);
		HttpEntity<String> entity = definirHeadersPost(json);
		String resultado = "";
		try {
			URI uri = UriComponentsBuilder.fromUriString(urlService).build().encode().toUri();
			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			if (result.getBody() != null) {
				logger.info(result.getBody().toString());
				resultado = result.getBody().toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getCause().getMessage().toString());
		}
		return resultado;
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

	private HttpEntity<String> definirHeadersPost(String json) {
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		// no se necesitan credenciales...
		String auth = "";// ;env.getProperty("userAdmin") + ":" + env.getProperty("passAdmin");
		final byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
		final String encoded = Base64.getEncoder().encodeToString(authBytes);
		String credenciales = "key=" + new String(
				"AAAAsOcrkyk:APA91bH8NLVArYVPVHD7DO_XtYFvy9xEKU2ZtZe-D-aGrlunuar4F6Iz8skEEF_RwcJIunb4_tL9hOSWPihFUUp8Mu_CerMWVQUuRZohwKTpX-TcOBWmU4qpRJpK1TTqGl_IrMXebPH7");
		// Prepare header
		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", credenciales);
		headers.setAccept(acceptableMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		return entity;
	}

	public static Object parseStringObj(String json, Class<?> obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			// mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
			// false);
			return mapper.readValue(json, obj);
		} catch (Exception e) {
			return null;
		}
	}

	public ResponseEntity<TokenSeguridad> obtenerTokenByUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		String urlKeyCloak = this.env.getProperty("properties.url.keycloack");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// creacion del request
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

		body.add("client_secret", this.env.getProperty("properties.client.secret"));
		body.add("username", user.getUsuario());
		body.add("password", user.getPassword());
		body.add("client_id", this.env.getProperty("properties.client.id"));
		body.add("grant_type", this.env.getProperty("properties.grant.type"));

		// Seteo header y body
		HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

		ResponseEntity<String> resp = null;
		try {
			resp = restTemplate.exchange(urlKeyCloak, HttpMethod.POST, httpEntity, String.class);
			if (resp.getStatusCodeValue() == 200) {
				Type type = new TypeToken<TokenSeguridad>() {
				}.getType();
				TokenSeguridad tokenSeguridad = new Gson().fromJson(resp.getBody().toString(), type);
				tokenSeguridad.setVersion(this.env.getProperty("properties.version.app"));
				response = new ResponseEntity(tokenSeguridad, HttpStatus.OK);
			}
		} catch (HttpClientErrorException e) {
			response = new ResponseEntity(e.getStatusCode());
		}

		return response;
	}

	public ResponseEntity<Collection<LogSolicitud>> obtieneHistorialSolicitud(Long idSolicitud) {
		String urlObtieneHistorialSolicitud = this.env.getProperty("properties.url.obtieneHistorialSolicitud")
				+ idSolicitud;
		String urlObtieneAllEstados = this.env.getProperty("properties.url.obtieneAllEstados");
		Type listType = new TypeToken<ArrayList<LogReembolsoEntity>>() {
		}.getType();
		Type listTypeEstado = new TypeToken<ArrayList<Estado>>() {
		}.getType();
		logger.info("invoco a servicio: " + urlObtieneHistorialSolicitud);
		List<LogReembolsoEntity> listaHistorial = new Gson().fromJson(this.callService(urlObtieneHistorialSolicitud),
				listType);

		List<LogSolicitud> listaHistoriaOut = new ArrayList<LogSolicitud>();
		List<Estado> listEstados = new Gson().fromJson(this.callService(urlObtieneAllEstados), listTypeEstado);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Gson gson = new Gson();

		for (LogReembolsoEntity log : listaHistorial) {
			LogSolicitud solLog = new LogSolicitud();
			for (Estado estado : listEstados) {
				if (String.valueOf(estado.getIdEstado()).equals(log.getLogDespues().trim())) {
					solLog.setEstadoSolicitud(estado.getDescripcion());
				}
				Usuario user = null;
				if (log.getUsuario() > 0) {
					user = gson.fromJson(obtenerUsuarioByIdUsuario(String.valueOf(log.getUsuario())), Usuario.class);
					solLog.setNombreUsuario(user.getNombreCompleto());
				}
				if (log.getHoraLog() != null) {
					solLog.setFechaLog(sdf.format(log.getFecha()) + " " + log.getHoraLog());
				} else {
					solLog.setFechaLog(sdf.format(log.getFecha()));
				}
			}
			listaHistoriaOut.add(solLog);
		}

		return new ResponseEntity<>(listaHistoriaOut, HttpStatus.OK);
	}

	public ResponseEntity<?> enviarNotificacion(Integer idUsuario, Integer idEstado, Integer idSolicitud) {
		// GsonJsonParser json = new GsonJsonParser();
		EstadoServicio estadoServicio = new EstadoServicio();
		List<SolicitudReembolso> listaJson = new ArrayList<SolicitudReembolso>();
		String estadoSolicitud = "";
		Gson gson = new Gson();
		String obtieneToken = this.env.getProperty("properties.url.obtieneToken") + idUsuario;
		// List<Boleta> listaUpdate = new ArrayList<Boleta>();
		Type tipoNotificaciones = new TypeToken<ArrayList<Notificaciones>>() {
		}.getType();
		List<Notificaciones> listTokenUsuario = new Gson().fromJson(this.callService(obtieneToken), tipoNotificaciones);

		String urlObtieneAllEstados = this.env.getProperty("properties.url.obtieneAllEstados");
		Type listTypeEstado = new TypeToken<ArrayList<Estado>>() {
		}.getType();
		List<Estado> listEstados = new Gson().fromJson(this.callService(urlObtieneAllEstados), listTypeEstado);
		for (Estado estado : listEstados) {
			if (estado.getIdEstado() == idEstado) {
				estadoSolicitud = Constantes.BODY + idSolicitud + Constantes.FINAL_BODY + estado.getDescripcion();
				break;
			}

		}
		// enviarNotificacion
		for (Notificaciones notifica : listTokenUsuario) {
			String enviarNotificacion = this.env.getProperty("properties.url.enviarNotificacion");
			Type listTypePost = new TypeToken<ResultadoMensaje>() {
			}.getType();
			Notificacion notificacion = new Notificacion();
			Notification notificarMensaje = new Notification();

			notificarMensaje.setBody(estadoSolicitud);
			notificarMensaje.setClick_action(Constantes.CLICK_ACTION);
			notificarMensaje.setIcon(Constantes.ICON);
			notificarMensaje.setSound(Constantes.SOUND);
			notificarMensaje.setTitle(Constantes.TITLE);

			notificacion.setNotification(notificarMensaje);
			notificacion.setPriority(Constantes.PRIORITY);
			notificacion.setRestricted_package_name("");
			notificacion.setTo(notifica.getToken());

			ResultadoMensaje result = new Gson().fromJson(
					this.callServicePost(enviarNotificacion, transformToJsonString(notificacion)), listTypePost);
		}
		estadoServicio.setEstado(true);
		estadoServicio.setDescripcion("Notificacion Enviada");
		// enviarNotificacion

		response = new ResponseEntity<>(estadoServicio, HttpStatus.OK);
		return response;
	}
}
