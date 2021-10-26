package cl.documento.gastoservice.controller;

import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cl.documento.gastoservice.model.Documento;
import cl.documento.gastoservice.model.DocumentoOut;
import cl.documento.gastoservice.service.GastoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Component
@RequestMapping("/api/gasto")
@Api(value = "Documento gasto CRUD", description = "Proyecto Rest para el registro de documentos de gastos")
@CrossOrigin
public class GastoController {

	@Autowired
	private GastoService gastoService;
	
	private Logger logger = LogManager.getLogger(getClass());

	private ResponseEntity response = null;
	
	//@Context
    private ServletContext context;

	@PostMapping(value = "/registraGasto/{idBoleta}/{tipoDocumento}", produces = "application/json")
	@ApiOperation(value = "Ingresa un nuevo documento de gasto",
	notes = "Se encarga de registrar un documento de gasto",
	response = Documento.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Documento de gasto registrada exitosamente", response = Documento.class),
			@ApiResponse(code = 204, message = "Error al crear registro de documento de gasto", response = ResponseEntity.class)
	})
	public ResponseEntity<?> registraDocumento(@RequestParam("file") MultipartFile file, 
			@PathVariable("tipoDocumento") String tipoDocumento, @PathVariable("idBoleta") Integer idBoleta){
		logger.info("Entrando a metodo registraDocumento");
		response = gastoService.registraDocumento(file, tipoDocumento, idBoleta);
		logger.info("Salgo de metodo registraDocumento");
		return response;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/actualizaDocumento/{idDocumento}",
	method = RequestMethod.PUT)
	@ApiOperation(value="Actualiza registro de documento",
	notes = "Se encarga de actualizar registro de un documento")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Registro de documento actualizado existosamente", response = Documento.class),
			@ApiResponse(code = 204, message = "Registro de documento no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class),
	})
	public ResponseEntity<Documento> actualizaBoleta(@Valid @RequestBody Documento documento,
			@PathVariable("idDocumento") Long idDocumento) {
		logger.info("Entrando a metodo actualizaBoleta");
		response = gastoService.actualizaDocumento(documento, idDocumento);
		logger.info("Salgo de metodo actualizaBoleta");
		return response;
	}

	/*@SuppressWarnings("unchecked")
	@RequestMapping(value="/asociarBoleta/{idBoleta}",
	method = RequestMethod.PUT)
	@ApiOperation(value="Actualiza registro de documento",
	notes = "Se encarga de actualizar registro de un documento, asociando un id de boleta")
	@ApiResponses(value= {
			@ApiResponse(code = 200, message = "Registro de documento actualizado existosamente", response = Documento.class),
			@ApiResponse(code = 204, message = "Registro de documento no encontrado", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "Solicitud incorrecta", response = ResponseEntity.class),
	})
	public ResponseEntity<Documento> asociarSolicitud(@PathVariable("idBoleta") Long idBoleta,
			@PathVariable("idUsuario") Long idUsuario,
			@PathVariable("fechaSolicitud") String fechaSolicitud) {

		return gastoService.asociarBoleta(idBoleta);
	}*/
	
	@DeleteMapping(value ="/eliminaDocumento/{idDocumento}", produces ="application/json")
	@ApiOperation(value = "Elimina un documento", 
				notes = "Se encarga de eliminar un documento logicamente (cambio de estado)",
				response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Eliminacion de documento exitosa"),
			@ApiResponse(code = 204, message = "Error al eliminar documento")
	})
	public ResponseEntity eliminaBoleta(@PathVariable("idDocumento") Long idDocumento) {
		logger.info("Entrando a metodo eliminaBoleta");
		response = gastoService.eliminaDocumento(idDocumento);
		logger.info("Salgo de metodo eliminaBoleta");
		return response;
	}
	
	@GetMapping(value = {"/obtieneDocumento/{idBoleta}"}, produces = "application/json")
    @ApiOperation(value = "Obtiene documentos en base a id de boleta",
    		notes = "Obtiene documentos en base a id de solicitud)",            
            response = DocumentoOut.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busqueda de documentos exitosa", response = DocumentoOut.class),
            @ApiResponse(code = 204, message = "Documentos buscados no existente", response = ResponseEntity.class)
    })
	public ResponseEntity<DocumentoOut> obtieneDocumento(@PathVariable("idBoleta") Long idBoleta){
		logger.info("Entrando a metodo obtieneDocumento");
		response = gastoService.obtieneDocumento(idBoleta);
		logger.info("Salgo de metodo obtieneDocumento");
		return response;
	}
	
    /*@GetMapping(value = {"/descargaDocumento/{idSolicitud}"}, produces = "application/json")
    @Produces({"application/json", "application/xml"})    
	public Response downloadFileWithPost(@PathVariable("idSolicitud") int idSolicitud) {
        String path = System.getProperty("user.home") + File.separator + "uploads";
        File fileDownload = new File("C:\\Users\\tecnova\\git\\gasto\\gasto\\src\\main\\resources\\pdf\\pdfSample.pdf");
        ResponseBuilder response = Response.ok((Object) fileDownload);
        response.header("Content-Disposition", "attachment;filename=pdfSample1.pdf");
        return response.build();
    }*/	
}
