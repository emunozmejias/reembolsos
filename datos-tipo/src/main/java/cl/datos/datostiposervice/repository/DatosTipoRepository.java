package cl.datos.datostiposervice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.datos.datostiposervice.model.Estado;

public interface DatosTipoRepository extends JpaRepository<Estado, Long>{

}
