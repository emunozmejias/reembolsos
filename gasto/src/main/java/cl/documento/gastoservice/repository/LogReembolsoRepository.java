package cl.documento.gastoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.documento.gastoservice.model.LogReembolsoEntity;

@RepositoryRestResource
public interface LogReembolsoRepository extends JpaRepository<LogReembolsoEntity, Long>{
	


}
