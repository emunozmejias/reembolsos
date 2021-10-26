package cl.privilegio.centrocostoservice.controller;

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

import cl.privilegio.centrocostoservice.model.ListaCentroCosto;
import cl.privilegio.centrocostoservice.model.PrivilegioCentroCosto;
import cl.privilegio.centrocostoservice.service.CentroCostoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@RequestMapping("/api/centroCosto")
@Api(value="Centro costo CRUD", description = "Proyecto Rest para realizar funcionalidades genericas")
@CrossOrigin
public class CentroCostoController {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	@Autowired
	private CentroCostoService centroCostoService;

	@GetMapping(value = {"/obtienePrivilegios"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los privilegios",
            notes = "Se encarga de obtener todos los privilegios desde base de datos",
            response = PrivilegioCentroCosto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los privilegios exitosa", response = PrivilegioCentroCosto.class)
    })
	public ResponseEntity<Collection<PrivilegioCentroCosto>> obtienePrivilegios(){
		logger.info("Entrando a metodo obtienePrivilegios");		
		response = centroCostoService.obtienePrivilegios();
		logger.info("Salgo de metodo obtienePrivilegios");
		return response;
	}
	
	@PostMapping(value = {"/registraPrivilegio"}, produces = "application/json")
    @ApiOperation(value = "Ingresa un nuevo privilegio",
            notes = "Se encarga de registrar un nuevo privilegio",
            response = PrivilegioCentroCosto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Privilegio registrado exitosamente", response = PrivilegioCentroCosto.class),
            @ApiResponse(code = 204, message = "Error al crear registro de privilegio", response = ResponseEntity.class)
    })
	public ResponseEntity<?> registraPrivilegio(@Valid @RequestBody PrivilegioCentroCosto privilegio) {
		logger.info("Entrando a metodo registraPrivilegio");
		response = centroCostoService.registraCentroCosto(privilegio);
		logger.info("Salgo de metodo registraPrivilegio");
		return response;
	}
	
	@PostMapping(value = {"/registraVariosPrivilegios"}, produces = "application/json")
    @ApiOperation(value = "Ingresa varios privilegios nuevos",
            notes = "Se encarga de registrar various privilegios de centro de costo de un usuarios",
            response = PrivilegioCentroCosto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Privilegio registrado exitosamente", response = PrivilegioCentroCosto.class),
            @ApiResponse(code = 204, message = "Error al crear registro de privilegio", response = ResponseEntity.class)
    })
	public ResponseEntity<?> registraVariosPrivilegios(@Valid @RequestBody ListaCentroCosto lista) {
		logger.info("Entrando a metodo registraPrivilegio");
		response = centroCostoService.registraVariosPrivilegios(lista);
		logger.info("Salgo de metodo registraPrivilegio");
		return response;
	}
	
	@DeleteMapping(value ="/eliminarPrivilegio/{id}", produces ="application/json")
	@ApiOperation(value = "Elimina un Privilegio", 
				notes = "Se encarga de eliminar un privilegio",
				response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Eliminacion de privilegio exitosa"),
			@ApiResponse(code = 204, message = "Error al eliminar privilegio")
	})
	public ResponseEntity eliminarPrivilegio(@PathVariable("id") Long id) {
		logger.info("Entrando a metodo eliminarPrivilegio");
		response = centroCostoService.eliminarPrivilegio(id);
		logger.info("Salgo de metodo eliminarPrivilegio");
		return response;		
	}
	
	@PostMapping(value ="/eliminarAllPrivilegios", produces ="application/json")
	@ApiOperation(value = "Elimina varios Privilegios", 
				notes = "Se encarga de eliminar varios o todos los privilegios de un usuario",
				response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Eliminacion de privilegios exitosa"),
			@ApiResponse(code = 204, message = "Error al eliminar privilegio")
	})
	public ResponseEntity eliminarAllPrivilegios(@Valid @RequestBody ListaCentroCosto lista) {
		logger.info("Entrando a metodo eliminarPrivilegio");
		response = centroCostoService.eliminarAllPrivilegios(lista);
		logger.info("Salgo de metodo eliminarPrivilegio");
		return response;		
	}
	
	
	@GetMapping(value = {"/obtieneCentroCosto/{idUsuario}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene todos los privilegios",
            notes = "Se encarga de obtener todos los privilegios desde base de datos",
            response = PrivilegioCentroCosto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de todos los privilegios exitosa", response = PrivilegioCentroCosto.class)
    })
	public ResponseEntity<Collection<PrivilegioCentroCosto>> obtieneCentroCosto(@PathVariable("idUsuario") Long idUsuario){
		logger.info("Entrando a metodo obtienePrivilegios");		
		response = centroCostoService.obtieneCentroCosto(idUsuario);
		logger.info("Salgo de metodo obtienePrivilegios");
		return response;
	}
}
