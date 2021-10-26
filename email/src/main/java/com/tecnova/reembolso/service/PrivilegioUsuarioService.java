package com.tecnova.reembolso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecnova.reembolso.entity.PrivilegioUsuario;
import com.tecnova.reembolso.repository.PrivilegioUsuarioRepository;

@Service
public class PrivilegioUsuarioService {

	@Autowired
	private PrivilegioUsuarioRepository privilegioRepository;

	public List<PrivilegioUsuario> findUserReembolsoByPrivilegio(String privilegio) {
		return this.privilegioRepository.findUserReembolsoByPrivilegio(privilegio);
	}

	public List<PrivilegioUsuario> findUserReembolsoByIdSolicitud(Long idSolicitud, String privilegio) {
		return this.privilegioRepository.findUserReembolsoByIdSolicitud(idSolicitud, idSolicitud, privilegio);
	}
}
