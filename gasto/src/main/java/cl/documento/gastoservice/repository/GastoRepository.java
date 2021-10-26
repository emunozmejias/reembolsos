package cl.documento.gastoservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.documento.gastoservice.model.Documento;

@RepositoryRestResource
public interface GastoRepository extends JpaRepository<Documento, Long>{
	
	Documento findByIdDocumento(Long idDocumento);
	
	@Query(value = "Select * from documento where id_boleta = ? limit 1;", nativeQuery=true)
	Documento obtieneDocumento(Long idBoleta);

}
