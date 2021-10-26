package cl.solicitud.reembolsoservice.enums;

public enum EstadoSolicitud {

	NO_RENDIDO(1),
	ELIMINADA(2),
	RENDIDO(3),
	AROBADA(4),
	RECHAZADA(5),
	PAGADA(6),
	OBJETADA(7),
	AUTORIZADA(8);
	
	private int idEstado;
	
	private EstadoSolicitud(int idEstado) {
		this.idEstado = idEstado;
	}
	
	public int getIdestado() {
		return this.idEstado;
	}
}
