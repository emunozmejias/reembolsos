package cl.solicitud.reembolsoservice.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cl.solicitud.reembolsoservice.model.LogReembolsoEntity;
import cl.solicitud.reembolsoservice.model.SolicitudReembolso;
import cl.solicitud.reembolsoservice.util.enums.LogEnums;

public class ConverterReembolso {
	
	private static final String ID = "--ID--";
	private static Logger logger = LogManager.getLogger(ConverterReembolso.class);
	
	public static LogReembolsoEntity reembolsoToLogRegistrar(SolicitudReembolso solicitud) {
		LogReembolsoEntity log = new LogReembolsoEntity();
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		Date fechaRegistro = new Date();
		//logger.info(sdf.format(fechaRegistro));
		log.setFecha(fechaRegistro);
		log.setHoraLog(sdf2.format(fechaRegistro));
		log.setUsuario(solicitud.getIdUsuario());
		log.setIdSolicitud(solicitud.getIdSolicitud());

		log.setLogDespues(LogEnums.REGISTRO_REEMBOLSO.getLog().replaceAll(ID, solicitud.toString()));

		return log;

	}
	
	public static LogReembolsoEntity reembolsoToLogActualizar(SolicitudReembolso antes, SolicitudReembolso despues, Integer idUsuario) {
		LogReembolsoEntity log = new LogReembolsoEntity();
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		Date fechaRegistro = new Date();
		log.setFecha(fechaRegistro);
		log.setHoraLog(sdf2.format(fechaRegistro));
		
		log.setUsuario(idUsuario);
		log.setIdSolicitud(despues.getIdSolicitud());
		log.setLogAntes(LogEnums.ACTUALIZA_REEMBOLSO.getLog().replaceAll(ID, antes.toString()));
		log.setLogDespues(LogEnums.ACTUALIZA_REEMBOLSO.getLog().replaceAll(ID, despues.toString()));

		return log;

	}
	
	public static LogReembolsoEntity reembolsoToLogEliminar(SolicitudReembolso antes, SolicitudReembolso despues) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		
		log.setUsuario(despues.getIdUsuario());
		log.setIdSolicitud(despues.getIdSolicitud());
		log.setLogAntes(LogEnums.ELIMINAR_REEMBOLSO.getLog().replaceAll(ID, antes.toString()));
		log.setLogDespues(LogEnums.ELIMINAR_REEMBOLSO.getLog().replaceAll(ID, despues.toString()));
		return log;

	}
}
