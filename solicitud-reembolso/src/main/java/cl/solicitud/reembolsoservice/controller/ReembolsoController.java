 package cl.solicitud.reembolsoservice.controller;

import java.text.ParseException;
import java.util.Collection;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.solicitud.reembolsoservice.model.FiltroBusqueda;
import cl.solicitud.reembolsoservice.model.LogReembolsoEntity;
import cl.solicitud.reembolsoservice.model.Solicitud;
import cl.solicitud.reembolsoservice.model.SolicitudReembolso;
import cl.solicitud.reembolsoservice.service.ReembolsoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Component
@RequestMapping("/api/solicitud")
@Api(value="Solicitud Reembolso CRUD", description = "Proyecto Rest para el registro de solicictudes de reembolso")
@CrossOrigin
public class ReembolsoController {

	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	@Autowired
	private ReembolsoService reembolsoService;
	
	@PostMapping(value = {"/registraSolicitud"}, produces = "application/json")
    @ApiOperation(value = "Ingresa una nueva solicitud",
            notes = "Se encarga de registrar una nueva solicitud de reembolso",
            response = SolicitudReembolso.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Solicitud registrada exitosamente", response = SolicitudReembolso.class),
            @ApiResponse(code = 204, message = "Error al crear registro de solicitud", response = ResponseEntity.class)
    })
	public ResponseEntity<?> registraSolicitud(@Valid @RequestBody SolicitudReembolso solicitud){
		logger.info("Entrando a metodo registraSolicitud");
		response =  reembolsoService.registraSolicitud(solicitud);
		logger.info("Salgo de metodo registraSolicitud");
		return response;
	}
	
	@GetMapping(value = {"/obtieneAllSolicitudes"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todas las solicitudes",
            notes = "Se encarga de obtener todas las solicitudes desde base de datos",
            response = SolicitudReembolso.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todas las solicitudes exitosa", response = SolicitudReembolso.class)
    })
	public ResponseEntity<Collection<SolicitudReembolso>> obtieneAllSolicitudes(){
		logger.info("Entrando a metodo obtieneAllSolicitudes");
		response = reembolsoService.obtieneAllSolicitudes();
		logger.info("Salgo de metodo obtieneAllSolicitudes");
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizaSolicitud/{idSolicitud}/{idUsuario}",
            method = RequestMethod.PUT)
	@ApiOperation(value="Actualiza registro de solicitud",
				notes = "Se encarga de actualizar registro de una solicitud")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Registro de solicitud actualizado existosamente", response = SolicitudReembolso.class),
			@ApiResponse(code = 204, message = "Registro de solicitud no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class),  //ApiError.class crear clase generica para retornar errores...
	})
	public ResponseEntity<SolicitudReembolso> actualizaSolicitud(@Valid @RequestBody SolicitudReembolso solicitud,
			@PathVariable("idSolicitud") Long idSolicitud,
			@PathVariable("idUsuario") Integer idUsuario) {
		logger.info("Entrando a metodo actualizaBoleta");
		response = reembolsoService.actualizaSolicitud(solicitud, idSolicitud, idUsuario);
		logger.info("Salgo de metodo actualizaBoleta");
		return response;		
	}
	
	@DeleteMapping(value ="/eliminaSolicitud/{idSolicitud}", produces ="application/json")
	@ApiOperation(value = "Elimina una solicitud", 
				notes = "Se encarga de eliminar una solicitud logicamente (cambio de estado)",
				response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Eliminacion de solicitud exitosa"),
			@ApiResponse(code = 204, message = "Error al eliminar solicitud")
	})
	public ResponseEntity eliminaBoleta(@PathVariable("idSolicitud") Long idSolicitud) {
		logger.info("Entrando a metodo eliminaBoleta");
		response = reembolsoService.eliminaSolicitud(idSolicitud);
		logger.info("Salgo de metodo eliminaBoleta");
		return response;		
	}
	
	@GetMapping(value = {"/obtieneSolicitudesByUsuario/{idUsuario}/{fecha}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todas las solicitudes",
            notes = "Se encarga de obtener todas las solicitudes desde base de datos",
            response = Solicitud.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todas las solicitudes exitosa", response = Solicitud.class)
    })
	public ResponseEntity<Collection<Solicitud>> obtieneSolicitudesByUsuario(@PathVariable("idUsuario") Long idUsuario,
			@PathVariable("fecha") String fecha) throws ParseException{
		logger.info("Entrando a metodo obtieneSolicitudesByUsuario");
		response = reembolsoService.obtieneSolicitudesByUsuario(idUsuario, fecha);
		logger.info("Salgo de metodo obtieneSolicitudesByUsuario");
		return response;		
	}
	
	@PostMapping(value = {"/obtieneSolicitudesByFiltro"},
			produces = "application/json")
    @ApiOperation(value = "Obtiene todas las solicitudes por filtro determinado",
            notes = "Se encarga de obtener todas las solicitudes desde base de datos",
            response = Solicitud.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todas las solicitudes exitosa", response = Solicitud.class)
    })
	public ResponseEntity<Collection<Solicitud>> obtieneSolicitudesByFiltro(@RequestBody FiltroBusqueda filtro) throws ParseException{
		logger.info("Entrando a metodo obtieneSolicitudesByFiltro");
		response = reembolsoService.obtieneSolicitudesByFiltro(filtro);
		logger.info("Salgo de metodo obtieneSolicitudesByFiltro");
		return response;		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizaEstadoSolicitud/{idSolicitud}/{idEstado}/{motivo}",
            method = RequestMethod.PUT)
	@ApiOperation(value="Actualiza registro de solicitud",
				notes = "Se encarga de actualizar registro de una solicitud")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Registro de solicitud actualizado existosamente", response = SolicitudReembolso.class),
			@ApiResponse(code = 204, message = "Registro de solicitud no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class),  //ApiError.class crear clase generica para retornar errores...
	})
	public ResponseEntity<SolicitudReembolso> actualizaEstadoSolicitud(@PathVariable("idSolicitud") Long idSolicitud,
			@PathVariable("idEstado") Integer idEstado,
			@PathVariable("motivo") String motivo) {
		logger.info("Entrando a metodo actualizaEstadoSolicitud");
		response = reembolsoService.actualizaEstadoSolicitud(idSolicitud, idEstado, motivo);
		logger.info("Salgo de metodo actualizaEstadoSolicitud");
		return response;		
	}
	
	@GetMapping(value = {"/obtieneSolicitud/{idSolicitud}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todas las solicitudes",
            notes = "Se encarga de obtener una solicitud en base a su id desde base de datos",
            response = Solicitud.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de solicitud exitosa", response = Solicitud.class)
    })
	public ResponseEntity<Solicitud> obtieneSolicitud(@PathVariable("idSolicitud") Long idSolicitud){
		logger.info("Entrando a metodo obtieneSolicitud");
		response = reembolsoService.obtieneSolicitud(idSolicitud);
		logger.info("Salgo de metodo obtieneSolicitud");
		return response;		
	}
	
	
	@GetMapping(value = {"/obtieneHistorialSolicitud/{idSolicitud}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene historial de solicitud",
            notes = "Se encarga de obtener historial de solicitud en base a su id desde base de datos",
            response = LogReembolsoEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de historial de solicitud exitosa", response = Solicitud.class)
    })
	public ResponseEntity<Collection<LogReembolsoEntity>> obtieneHistorialSolicitud(@PathVariable("idSolicitud") Long idSolicitud){
		logger.info("Entrando a metodo obtieneHistorialSolicitud");
		response = reembolsoService.obtieneHistorialSolicitud(idSolicitud);
		logger.info("Salgo de metodo obtieneHistorialSolicitud");
		return response;		
	}
	
}
