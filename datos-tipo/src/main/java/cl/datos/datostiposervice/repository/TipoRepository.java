package cl.datos.datostiposervice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.datos.datostiposervice.model.TipoBoleta;

public interface TipoRepository extends JpaRepository<TipoBoleta, Long>{

}
