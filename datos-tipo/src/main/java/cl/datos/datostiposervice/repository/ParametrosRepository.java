package cl.datos.datostiposervice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.datos.datostiposervice.model.Parametros;
import cl.datos.datostiposervice.model.ParametrosPK;

public interface ParametrosRepository extends JpaRepository<Parametros, ParametrosPK> {

	@Query(value = "select * from parametros where par_tipo = ? and (par_codigo = ? or ? = 'null') order by par_orden", nativeQuery = true)
	public List<Parametros> obtieneParametros(String tipo, String codigo1, String codigo2);

}
