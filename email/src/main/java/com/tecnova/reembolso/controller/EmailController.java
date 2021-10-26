package com.tecnova.reembolso.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tecnova.reembolso.model.EstadoServicio;
import com.tecnova.reembolso.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@RequestMapping("/api/email/reembolso")
@Api(value = "Email Noticiaciones Reembolsos", description = "Proyecto Rest para el envio de notificaciones del workflow de reembolsos")
@CrossOrigin
public class EmailController {

	Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private MailService notificationService;
	private ResponseEntity<?> response = null;

	@Autowired
	private Environment env;

	@PostMapping(value = "/ingreso/{username}")
	@ApiOperation(value = "Envio de correo al administrador contable", notes = "Se encarga de informar al administrador contable ", response = EstadoServicio.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion solicitud enviada por correo correctamente", response = EstadoServicio.class) })
	public ResponseEntity<EstadoServicio> correoIngresoReembolso(@PathVariable("username") String username) {
		EstadoServicio estado = new EstadoServicio();
		try {

			// notificationService.correoIngresoReembolso(username);
			// notificationService.correoRevisarReembolso(username);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}

	@PostMapping(value = "/revisar/{nombreUsuario}/{idSolicitud}", produces = "application/json")
	@ApiOperation(value = "Envio de correo de aviso para la revision de una solicitud de reembolso", notes = "Se correo de aviso al gerente de desarrollo autorizar o rechazar un reembolso", response = EstadoServicio.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion revision enviada por correo correctamente", response = EstadoServicio.class) })
	public ResponseEntity<EstadoServicio> correoRevisarReembolso(@PathVariable("nombreUsuario") String nombreUsuario, @PathVariable("idSolicitud") Long idSolicitud) {
		EstadoServicio estado = new EstadoServicio();
		try {
			notificationService.correoRevisarReembolsoSolicitud(nombreUsuario, idSolicitud);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<EstadoServicio>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}

	@PostMapping(value = "/autorizar/{nombreUsuario}/{idSolicitud}")
	@ApiOperation(value = "Envio de correo de aviso para realizar el pago del reembolso", notes = "El correo del gerente de desarrollo que informa la autorizacion del pago del reembolso", response = EstadoServicio.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion autorizacion enviada por correo correctamente", response = EstadoServicio.class) })
	public ResponseEntity<EstadoServicio> correoAutorizarReembolso(@PathVariable("nombreUsuario") String nombreUsuario, @PathVariable("idSolicitud") Long idSolicitud) {
		EstadoServicio estado = new EstadoServicio();
		try {
			notificationService.correoAutorizarReembolso(nombreUsuario, idSolicitud);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}

	@PostMapping(value = "/rechazado/{email}")
	@ApiOperation(value = "Envio de correo de aviso de rechazo de solicitud de reembolso", notes = "Se correo de rechazo enviado al usuario que ingreso la solicitud", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion rechazo enviada por correo correctamente", response = String.class) })
	public ResponseEntity<?> correoRechazoReembolso(@PathVariable("email") String email) {
		EstadoServicio estado = new EstadoServicio();
		try {
			notificationService.correoRechazarReembolso(email);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}

	@PostMapping(value = "/pagado/{email}")
	@ApiOperation(value = "Envio de correo de aviso de pago del reembolso", notes = "Se correo notificacion de pago del reembolso", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion de pago enviada por correo correctamente", response = String.class) })
	public ResponseEntity<?> correoPagadoReembolso(@PathVariable("email") String email) {
		EstadoServicio estado = new EstadoServicio();
		try {
			notificationService.correoPagadoReembolso(email);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}

	@GetMapping(value = "/notificar/{tipo}")
	@ApiOperation(value = "Envio de correo segun tipo", notes = "Se encarga de informar segun tipo ", response = EstadoServicio.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion solicitud enviada por correo correctamente", response = EstadoServicio.class) })
	public ResponseEntity<EstadoServicio> notificar(@PathVariable("tipo") String tipo) {
		EstadoServicio estado = new EstadoServicio();
		try {
			notificationService.correoSolicitudesReembolso(tipo);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}

	@PostMapping(value = "/formulario/ingreso")
	@ApiOperation(value = "Envio de correo de nuevo usuario en aplicacion de reembolso", notes = "Se encarga de enviar el correo con los datos del nuevo usuario ", response = EstadoServicio.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Notificacion ingreso enviado por correo correctamente", response = EstadoServicio.class) })
	public ResponseEntity<EstadoServicio> formularioIngreso( @Valid @RequestBody Map<String, String> usuario) {
		EstadoServicio estado = new EstadoServicio();
		try {
			notificationService.correoFormularioIngreso(usuario);
			estado.setDescripcion("Correo Enviado");
			estado.setEstado(true);
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} catch (MailException mailException) {
			estado.setDescripcion("Correo no enviado");
			estado.setEstado(false);
			response = new ResponseEntity<>(estado, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return (ResponseEntity<EstadoServicio>) response;
	}
}
