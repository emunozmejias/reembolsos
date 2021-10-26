package cl.solicitud.reembolsoservice.util.enums;

public enum LogEnums {

	REGISTRO_REEMBOLSO("Se registra reembolso: --ID--"),
	ACTUALIZA_REEMBOLSO("Se actualiza reembolso: --ID--"),
	ELIMINAR_REEMBOLSO("Se elimina boleta: --ID--"), ;
	
	private String log;

	private LogEnums(String log) {
		this.log = log;
	}

	public String getLog() {
		return this.log;
	}

}
