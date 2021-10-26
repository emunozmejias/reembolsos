package cl.tecnova.notificaciones.service.service;

import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.tecnova.notificaciones.service.model.Notificaciones;
import cl.tecnova.notificaciones.service.repository.NotificacionesRepository;

@Service
public class NotificacionesService {

	@Autowired
	private NotificacionesRepository notificacionesRepository;
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	public ResponseEntity<Collection<Notificaciones>> obtieneTokenNotificaciones(){
		return new ResponseEntity<>(notificacionesRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> registraTokenNotificacion(Notificaciones notificacionToken){
		
		Notificaciones notifica = notificacionesRepository.obtieneTokens(notificacionToken.getToken());
		
		if (notifica != null) {
			notifica.setIdUsuario(notificacionToken.getIdUsuario());
			notifica.setFechaToken(new Date());
			response = new ResponseEntity<>(notificacionesRepository.save(notifica), HttpStatus.OK);
		} else {
			notificacionToken.setFechaToken(new Date());
			response = new ResponseEntity<>(notificacionesRepository.save(notificacionToken), HttpStatus.OK);
		}
		return response;
	}
	
	public ResponseEntity<Collection<Notificaciones>> obtieneToken(Integer idUsuario){
		response = new ResponseEntity<>(notificacionesRepository.obtieneToken(idUsuario), HttpStatus.OK);
		return response;
	}
}
