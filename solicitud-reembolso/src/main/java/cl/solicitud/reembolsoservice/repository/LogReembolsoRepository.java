package cl.solicitud.reembolsoservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.solicitud.reembolsoservice.model.LogReembolsoEntity;



@RepositoryRestResource
public interface LogReembolsoRepository extends JpaRepository<LogReembolsoEntity, Long>{
	
	@Query(value="select * from log_reembolso where id_solicitud = ? and id_boleta is null;", nativeQuery=true)
	List<LogReembolsoEntity> obtieneHistorialSolicitud(Long idSolicitud);

}
