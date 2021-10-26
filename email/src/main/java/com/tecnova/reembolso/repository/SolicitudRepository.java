package com.tecnova.reembolso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tecnova.reembolso.model.Solicitud;

@RepositoryRestResource
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

	@Query(value = "select * from solicitud_reembolso where id_estado_reembolso in :idsEstado", nativeQuery = true)
	List<Solicitud> findSolicitudesByEstado(@Param("idsEstado") List<Integer> idsEstado);
}
