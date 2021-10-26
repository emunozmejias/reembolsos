package cl.privilegio.centrocostoservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.privilegio.centrocostoservice.model.PrivilegioCentroCosto;

public interface CentroCostoRepository extends JpaRepository<PrivilegioCentroCosto, Long>{

	@Query(value = "Select * from privilegio_centro_costo", nativeQuery=true)
	List<PrivilegioCentroCosto> obtienePrivilegios();
	
	@Query(value = "Select * from privilegio_centro_costo where id = ?", nativeQuery=true)
	PrivilegioCentroCosto findByIdCentroCosto(Long id);
	
	@Query(value = "Select * from privilegio_centro_costo where id_usuario = ?", nativeQuery=true)
	List<PrivilegioCentroCosto> obtieneCentroCosto(Long idUsuario);
	
}
