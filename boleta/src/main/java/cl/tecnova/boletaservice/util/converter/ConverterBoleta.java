package cl.tecnova.boletaservice.util.converter;

import java.util.Date;

import cl.tecnova.boletaservice.model.Boleta;
import cl.tecnova.boletaservice.model.LogReembolsoEntity;
import cl.tecnova.boletaservice.util.enums.LogDocumetoEnums;

public class ConverterBoleta {
	
	private static final String ID = "--ID--";

	public static LogReembolsoEntity boletaToLogRegistrar(Boleta boleta) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		log.setIdBoleta(boleta.getIdBoleta());
		log.setUsuario(boleta.getIdUsuario());
		log.setIdSolicitud(boleta.getIdSolicitud());

		log.setLogDespues(LogDocumetoEnums.REGISTRO_BOLETA.getLog().replaceAll(ID, boleta.toString()));

		return log;

	}
	
	public static LogReembolsoEntity boletaToLogActualizar(Boleta antes, Boleta despues) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		log.setIdBoleta(antes.getIdBoleta());
		log.setUsuario(antes.getIdUsuario());
		log.setIdSolicitud(antes.getIdSolicitud());
		log.setLogAntes(LogDocumetoEnums.ACTUALIZA_BOLETA.getLog().replaceAll(ID, antes.toString()));
		log.setLogDespues(LogDocumetoEnums.ACTUALIZA_BOLETA.getLog().replaceAll(ID, despues.toString()));

		return log;

	}
	
	public static LogReembolsoEntity boletaToLogEliminar(Boleta antes, Boleta despues) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		log.setIdBoleta(antes.getIdBoleta());
		log.setUsuario(antes.getIdUsuario());
		log.setIdSolicitud(antes.getIdSolicitud());
		log.setLogAntes(LogDocumetoEnums.ELIMINAR_BOLETA.getLog().replaceAll(ID, antes.toString()));
		log.setLogDespues(LogDocumetoEnums.ELIMINAR_BOLETA.getLog().replaceAll(ID, despues.toString()));
		return log;

	}
}
