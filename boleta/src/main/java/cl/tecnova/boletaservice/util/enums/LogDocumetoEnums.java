package cl.tecnova.boletaservice.util.enums;

public enum LogDocumetoEnums {

	REGISTRO_BOLETA("Se registra boleta: --ID--"),
	ACTUALIZA_BOLETA("Se actualiza boleta: --ID--"),
	ELIMINAR_BOLETA("Se elimina boleta: --ID--"), ;
	
	private String log;

	private LogDocumetoEnums(String log) {
		this.log = log;
	}

	public String getLog() {
		return this.log;
	}

}
