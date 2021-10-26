package com.tecnova.reembolso.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tecnova.reembolso.converter.ConverterEmail;
import com.tecnova.reembolso.entity.PrivilegioUsuario;
import com.tecnova.reembolso.model.Solicitud;
import com.tecnova.reembolso.model.Usuario;

@Service
public class MailService {

	private Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PrivilegioUsuarioService privilegioService;

	@Autowired
	private SolicitudService solicitudService;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private Environment env;

	public void correoIngresoReembolso(String usuario) throws MailException {

		List<PrivilegioUsuario> list = privilegioService.findUserReembolsoByPrivilegio("Administracion");
		if (list != null && !list.isEmpty()) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(ConverterEmail.getDestino(list));
			mail.setFrom(ConverterEmail.getRemitente());
			mail.setSubject(ConverterEmail.getTituloIngreso());
			mail.setText(ConverterEmail.getTextoReembolso(usuario));
			javaMailSender.send(mail);
		}
	}

	public void correoRevisarReembolso(String nombreUsuario) {
		List<PrivilegioUsuario> list = privilegioService.findUserReembolsoByPrivilegio("Gerente");
		if (list != null && !list.isEmpty()) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(ConverterEmail.getDestino(list));
			mail.setFrom(ConverterEmail.getRemitente());
			mail.setSubject(ConverterEmail.getTituloRevisar());
			mail.setText(ConverterEmail.getTextoRevisarReembolso() + nombreUsuario);
			javaMailSender.send(mail);
		}
	}

	public void correoRevisarReembolsoSolicitud(String nombreUsuario, Long idSolicitud) {
		List<PrivilegioUsuario> list = privilegioService.findUserReembolsoByIdSolicitud(idSolicitud, "Gerente");
		if (list != null && !list.isEmpty()) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(ConverterEmail.getDestino(list));
			mail.setFrom(ConverterEmail.getRemitente());
			mail.setSubject(ConverterEmail.getTituloRevisar());
			mail.setText(ConverterEmail.getTextoRevisarReembolso() + nombreUsuario);
			javaMailSender.send(mail);
		}
	}

	public void correoAutorizarReembolso(String nombreUsuario, Long idSolicitud) {
		List<PrivilegioUsuario> list = privilegioService.findUserReembolsoByPrivilegio("Administracion");
		if (list != null && !list.isEmpty()) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(ConverterEmail.getDestino(list));
			mail.setFrom(ConverterEmail.getRemitente());
			mail.setSubject(ConverterEmail.getTituloAutorizado());
			mail.setText(ConverterEmail.getTxtSolicitud() + idSolicitud + ConverterEmail.getTextoAutorizadoReembolso() + nombreUsuario);
			javaMailSender.send(mail);
		}
	}

	public void correoRechazarReembolso(String email) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(ConverterEmail.getRemitente());
		mail.setSubject(ConverterEmail.getTituloRechazar());
		mail.setText(ConverterEmail.getTextoRechazarReembolso());
		javaMailSender.send(mail);

	}

	public void correoPagadoReembolso(String email) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(ConverterEmail.getRemitente());
		mail.setSubject(ConverterEmail.getTituloPagado());
		mail.setText(ConverterEmail.getTextoPagadoReembolso());
		javaMailSender.send(mail);
	}

	public void correoSolicitudesReembolso(String tipo) throws MailException {
		String estados = this.env.getProperty("properties.notificar.tipo" + tipo + ".estados");
		String privilegio = this.env.getProperty("properties.notificar.tipo" + tipo + ".privilegio");
		List<Integer> idsEstado = Arrays.asList(estados.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		List<PrivilegioUsuario> destinos = null;
		if ("1".equals(tipo)) {
			destinos = privilegioService.findUserReembolsoByPrivilegio(privilegio);
		} else if ("2".equals(tipo)) {
			destinos = privilegioService.findUserReembolsoByIdSolicitud(0L, privilegio);
		}
		List<Solicitud> solicitudes = solicitudService.findSolicitudesByEstado(idsEstado);
		String idsUsuarios = solicitudes.stream().map(Solicitud::getIdUsuario).distinct().map(Object::toString).collect(Collectors.joining(","));
		List<Usuario> usuarios = new Gson().fromJson(this.callService(this.env.getProperty("properties.url.obtieneUsuariosByIds") + idsUsuarios), new TypeToken<ArrayList<Usuario>>() {
		}.getType());
		if (destinos != null && !destinos.isEmpty() && solicitudes != null && !solicitudes.isEmpty()) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(ConverterEmail.getDestino(destinos));
			mail.setFrom(ConverterEmail.getRemitente());
			mail.setSubject(this.env.getProperty("properties.notificar.tipo" + tipo + ".asunto"));
			StringBuilder texto = new StringBuilder();
			if (usuarios != null && !usuarios.isEmpty()) {
				for (Usuario usuario : usuarios) {
					texto.append(this.env.getProperty("properties.notificar.tipo" + tipo + ".cuerpo") + usuario.getNombreCompleto() + "\n\n");
				}
			}
			mail.setText(texto.toString());
			javaMailSender.send(mail);
		}
	}

	private HttpEntity<String> definirHeadersGet() {
		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(acceptableMediaTypes);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return entity;
	}

	private String callService(String urlService) {
		String resultado = "";
		try {
			URI uri = UriComponentsBuilder.fromUriString(urlService).build().encode().toUri();
			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, definirHeadersGet(), String.class);
			if (result.getBody() != null) {
				resultado = result.getBody().toString();
			}
		} catch (Exception e) {
			logger.info("error: " + e.getCause().getMessage().toString());
		}
		return resultado;
	}

	public void correoFormularioIngreso(Map<String, String> usuario) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(this.env.getProperty("properties.formulario.ingreso.para"));
		mail.setCc(this.env.getProperty("properties.formulario.ingreso.copia"));
		mail.setFrom(ConverterEmail.getRemitente());
		mail.setSubject(this.env.getProperty("properties.formulario.ingreso.asunto"));
		String cuerpo1 = this.env.getProperty("properties.formulario.ingreso.cuerpo.1");
		String cuerpo2 = this.env.getProperty("properties.formulario.ingreso.cuerpo.2");
		cuerpo2 = cuerpo2.replace("{usuario}", usuario.get("usuario"));
		cuerpo2 = cuerpo2.replace("{nombres}", usuario.get("nombres"));
		cuerpo2 = cuerpo2.replace("{apellidos}", usuario.get("apellidos"));
		cuerpo2 = cuerpo2.replace("{correo}", usuario.get("correo"));
		cuerpo2 = cuerpo2.replace("{empresa}", usuario.get("empresa"));
		cuerpo2 = cuerpo2.replace("{cargo}", usuario.get("cargo"));
		mail.setText(cuerpo1 + "\n\n" + cuerpo2);
		javaMailSender.send(mail);
	}
}
