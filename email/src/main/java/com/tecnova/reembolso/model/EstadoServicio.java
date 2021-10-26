package com.tecnova.reembolso.model;

import lombok.Data;

@Data
public class EstadoServicio {

	private boolean estado;
	
	private String descripcion;

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
