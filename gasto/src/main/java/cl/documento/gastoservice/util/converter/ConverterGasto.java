package cl.documento.gastoservice.util.converter;

import java.util.Date;

import cl.documento.gastoservice.model.Documento;
import cl.documento.gastoservice.model.LogReembolsoEntity;
import cl.documento.gastoservice.util.enums.LogDocumetoEnums;

public class ConverterGasto {
	
	private static final String ID = "--ID--";

	public static LogReembolsoEntity gastoToLogRegistrar(Documento doc) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		log.setIdBoleta(doc.getIdBoleta());

		log.setLogDespues(LogDocumetoEnums.REGISTRO_DOCUMENTO.getLog().replaceAll(ID, doc.toString()));

		return log;

	}
	
	public static LogReembolsoEntity gastoToLogActualizar(Documento antes, Documento despues) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		log.setIdBoleta(despues.getIdBoleta());
		log.setLogAntes(LogDocumetoEnums.ACTUALIZA_DOCUMENTO.getLog().replaceAll(ID, antes.toString()));
		log.setLogDespues(LogDocumetoEnums.ACTUALIZA_DOCUMENTO.getLog().replaceAll(ID, despues.toString()));

		return log;

	}
	
	public static LogReembolsoEntity gastoToLogEliminar(Documento antes, Documento despues) {
		LogReembolsoEntity log = new LogReembolsoEntity();

		log.setFecha(new Date());
		log.setIdBoleta(antes.getIdBoleta());
		log.setLogAntes(LogDocumetoEnums.ELIMINAR_DOCUMENTO.getLog().replaceAll(ID, antes.toString()));
		log.setLogDespues(LogDocumetoEnums.ELIMINAR_DOCUMENTO.getLog().replaceAll(ID, despues.toString()));
		
		return log;

	}
}
