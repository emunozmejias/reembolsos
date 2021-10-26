package cl.documento.gastoservice.util.enums;

public enum LogDocumetoEnums {

	REGISTRO_DOCUMENTO("Se registra el documento --ID--"),
	ACTUALIZA_DOCUMENTO("Se actualiza el documento --ID--"),
	ELIMINAR_DOCUMENTO("Se elimina el documento --ID--");
	
	private String log;

	private LogDocumetoEnums(String log) {
		this.log = log;
	}

	public String getLog() {
		return this.log;
	}

}
