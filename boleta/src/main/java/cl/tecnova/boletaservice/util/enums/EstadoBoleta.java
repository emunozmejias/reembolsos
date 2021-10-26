package cl.tecnova.boletaservice.util.enums;

public enum EstadoBoleta {

	NO_RENDIDO(1),
	ELIMINADA(2),
	RENDIDO(3),
	APROBADA(4),
	RECHAZADA(5),
	PAGADA(6),
	OBJETADA(7),
	AUTORIZADA(8);
	
	
	private int idEstado;
	
	private EstadoBoleta(int idEstado) {
		this.idEstado = idEstado;
	}
	
	public int getIdEstado() {
		return this.idEstado;
	}
}
