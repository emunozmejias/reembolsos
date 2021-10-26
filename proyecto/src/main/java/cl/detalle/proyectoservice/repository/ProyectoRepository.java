package cl.detalle.proyectoservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cl.detalle.proyectoservice.model.Proyecto;

@RepositoryRestResource
public interface ProyectoRepository extends JpaRepository<Proyecto, Long>{

	@Query(value="select pro.id_proyecto, pro.nombre_proyecto, pro.codigo_proyecto,pro.codigo_centro_costo, pro.presupuesto "
			+ "from proyecto_asignacion pro_a "
			+ "join proyecto pro on pro_a.id_proyecto = pro.id_proyecto "
			+ "where pro_a.id_usuario = ? and pro_a.ind_vigente = 1 and pro.estado in ('Activo') order by pro.nombre_proyecto asc", nativeQuery=true)
	public List<Proyecto> obtieneProyectosByUsuario(int idUsuario);
	
	@Query(value="select pro.id_proyecto, pro.nombre_proyecto, pro.codigo_proyecto,pro.codigo_centro_costo, pro.presupuesto "
			+ "from proyecto_asignacion pro_a "
			+ "join proyecto pro on pro_a.id_proyecto = pro.id_proyecto "
			+ "where pro_a.id_usuario = ? order by pro.nombre_proyecto asc", nativeQuery=true)
	public List<Proyecto> obtieneProyectosByUsuarioTodos(int idUsuario);
	
	@Query(value="select pro.id_proyecto, pro.nombre_proyecto, pro.codigo_proyecto, pro.codigo_centro_costo, pro.presupuesto from proyecto pro where pro.id_proyecto = ?", nativeQuery=true)
	public Proyecto obtieneProyectosByIdProyecto(int idProyecto);
	
	@Query(value = "select pro.id_proyecto, pro.nombre_proyecto, pro.codigo_proyecto, pro.codigo_centro_costo, pro.presupuesto from proyecto pro where estado = 'Activo' order by nombre_proyecto asc;", nativeQuery=true)
	public List<Proyecto> obtieneAllCentroCostos();
	
}
