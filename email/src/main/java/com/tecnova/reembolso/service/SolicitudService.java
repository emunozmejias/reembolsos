package com.tecnova.reembolso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecnova.reembolso.model.Solicitud;
import com.tecnova.reembolso.repository.SolicitudRepository;

@Service
public class SolicitudService {
	@Autowired
	private SolicitudRepository solicitudRepository;

	public List<Solicitud> findSolicitudesByEstado(List<Integer> idsEstado) {
		return this.solicitudRepository.findSolicitudesByEstado(idsEstado);
	}
}
