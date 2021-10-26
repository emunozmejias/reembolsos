package cl.detalle.proyectoservice.controller;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.detalle.proyectoservice.model.EstadoServicio;
import cl.detalle.proyectoservice.model.ListaProyectos;
import cl.detalle.proyectoservice.model.Proyecto;
import cl.detalle.proyectoservice.service.ProyectoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@RequestMapping("/api/proyecto")
@Api(value="Detalle de proyectos", description = "Proyecto Rest para obetener detalle de proyectos")
@CrossOrigin
public class ProyectoController {
	
	@Autowired
	private ProyectoService proyectoService;
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	@GetMapping(value = {"/obtieneProyecto/{idUsuario}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los proyectos por usuario",
            notes = "Se encarga de obtener todos los proyectos asignados vigentes con estado activo y id de usuario",
            response = Proyecto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los proyectos exitosa", response = Proyecto.class)
    })
	public ResponseEntity<Collection<Proyecto>> obtieneProyectosByUsuario(@PathVariable("idUsuario") int idUsuario){
		logger.info("Entrando a metodo obtieneProyectosByUsuario");
		response = proyectoService.obtieneProyectosUsuario(idUsuario);
		logger.info("Salgo de metodo obtieneProyectosByUsuario");
		return response;
	}
	
	@GetMapping(value = {"/obtieneProyectoByIdProyecto/{idProyecto}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene proyectos por id de proyecto",
            notes = "Se encarga de obtener un proyecto en base a id de proyecto",
            response = Proyecto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de proyecto exitosa", response = Proyecto.class)
    })
	public ResponseEntity<Proyecto> obtieneProyectosByIdProyecto(@PathVariable("idProyecto") int idProyecto){
		logger.info("Entrando a metodo obtieneProyectosByIdProyecto");
		response = proyectoService.obtieneProyectosByIdProyecto(idProyecto);
		logger.info("Salgo de metodo obtieneProyectosByIdProyecto");
		return response;
	}
		
	@PutMapping(value = {"/actualizaPresupuesto"}, 
			produces = "application/json")
	@ApiOperation(value="Actualiza presupuesto de proyecto",
				notes = "Se encarga de actualizar presupuesto de proyecto en base a su id de proyecto",
				response = EstadoServicio.class)
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Registro de proyecto actualizado existosamente", response = EstadoServicio.class)
	})
	public  ResponseEntity<EstadoServicio> actualizaPresupuesto(@RequestBody ListaProyectos proyectoPresupuesto){
		logger.info("Entrando a metodo actualizaPresupuesto");
		response =proyectoService.actualizaPrespuesto(proyectoPresupuesto);
		logger.info("Salgo de metodo actualizaPresupuesto");
		return response;
	}
	
	
	@GetMapping(value = {"/obtieneAllCentroCostos"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los proyectos activos",
            notes = "Se encarga de obtener todos los proyectos vigentes con estado activo",
            response = Proyecto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los proyectos exitosa", response = Proyecto.class)
    })
	public ResponseEntity<Collection<Proyecto>> obtieneAllCentroCostos(){
		logger.info("Entrando a metodo obtieneAllCentroCostos");
		response = proyectoService.obtieneAllCentroCostos();
		logger.info("Salgo de metodo obtieneAllCentroCostos");
		return response;
	}
	
	
	@GetMapping(value = {"/obtieneProyectoTodos/{idUsuario}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los proyectos por usuario, incluso los no asociados o vigentes",
            notes = "Se encarga de obtener todos los proyectos que alguna vez estuvieron asignados ",
            response = Proyecto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los proyectos exitosa", response = Proyecto.class)
    })
	public ResponseEntity<Collection<Proyecto>> obtieneProyectosByUsuarioTodos(@PathVariable("idUsuario") int idUsuario){
		logger.info("Entrando a metodo obtieneProyectosByUsuarioTodos");
		response = proyectoService.obtieneProyectosUsuarioTodos(idUsuario);
		logger.info("Salgo de metodo obtieneProyectosByUsuarioTodos");
		return response;
	}

}
