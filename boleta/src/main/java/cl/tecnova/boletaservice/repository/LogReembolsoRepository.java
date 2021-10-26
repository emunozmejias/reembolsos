package cl.tecnova.boletaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.tecnova.boletaservice.model.LogReembolsoEntity;



@RepositoryRestResource
public interface LogReembolsoRepository extends JpaRepository<LogReembolsoEntity, Long>{
	


}
