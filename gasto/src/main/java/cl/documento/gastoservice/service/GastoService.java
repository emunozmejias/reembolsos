package cl.documento.gastoservice.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.documento.gastoservice.model.Documento;
import cl.documento.gastoservice.model.DocumentoOut;
import cl.documento.gastoservice.model.ErrorApi;
import cl.documento.gastoservice.model.EstadoServicio;
import cl.documento.gastoservice.repository.GastoRepository;
import cl.documento.gastoservice.util.converter.ConverterGasto;

@Service
public class GastoService {

	@Autowired
	private GastoRepository gastoRepository;
	
	@Autowired
	private LogReembolsoService logReembolsoService;
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	public ResponseEntity<?> registraDocumento(MultipartFile file, String tipoDocumento, Integer idBoleta){
		Documento docu = new Documento();
		EstadoServicio estadoServicio = new EstadoServicio();
		byte[] fileByte =  null;
		try {
			fileByte = Base64.encodeBase64(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			logger.info(e.getCause().toString());
		}
		//System.out.println("Archivo::::::::  " + Arrays.toString(fileByte));
		docu.setTipoDocumento(tipoDocumento);
		docu.setDocumento(fileByte);
		docu.setIdBoleta(idBoleta);

		try{
			Documento docuBD = gastoRepository.save(docu);
			logReembolsoService.logReembolso(ConverterGasto.gastoToLogRegistrar(docuBD));
			estadoServicio.setEstado(true);
			estadoServicio.setDescripcion("Imagen registrada exitosamente");
		}catch (Exception e){
			estadoServicio.setEstado(false);
			estadoServicio.setDescripcion("Error el tamaño de la imagen es muy extenso");
		}
		
		return new ResponseEntity<>(estadoServicio, HttpStatus.CREATED);
	}
	
	public ResponseEntity actualizaDocumento(Documento documento, Long idDocumento) {
		Documento antesBD = gastoRepository.findByIdDocumento(idDocumento);
		Documento documentoBD = gastoRepository.findByIdDocumento(idDocumento);
		documentoBD.setTipoDocumento(documento.getTipoDocumento());
		try {
			response = new ResponseEntity<>(gastoRepository.save(documentoBD), HttpStatus.OK);
			logReembolsoService.logReembolso(ConverterGasto.gastoToLogActualizar(antesBD, documentoBD));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorApi error = new ErrorApi();
			error.setEstado(false);
			error.setDescripcion("Error al actualizar el documento");
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		}
		
		return response;
	}
		
	
	public ResponseEntity eliminaDocumento(Long idDocumento) {
		Documento antes = gastoRepository.findByIdDocumento(idDocumento);
		Documento documentoBD = gastoRepository.findByIdDocumento(idDocumento);
		documentoBD.setIdBoleta(null);
		try {
			response = new ResponseEntity<>(gastoRepository.save(documentoBD), HttpStatus.OK);
			logReembolsoService.logReembolso(ConverterGasto.gastoToLogEliminar(antes, documentoBD));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorApi error = new ErrorApi();
			error.setEstado(false);
			error.setDescripcion("Error al eliminar el documento");
			response = new ResponseEntity<>(error, HttpStatus.valueOf(404));
		}
		return response;
	}
	
	public ResponseEntity<DocumentoOut> obtieneDocumento(Long idBoleta){
		Documento docu = gastoRepository.obtieneDocumento(idBoleta);
		DocumentoOut salidaDoc = new DocumentoOut();
		if (docu != null) {
			String strDoc = new String(docu.getDocumento());						
			salidaDoc.setDocumento(strDoc);
			salidaDoc.setIdBoleta(docu.getIdBoleta());
			salidaDoc.setIdDocumento(docu.getIdDocumento());
			salidaDoc.setTipoDocumento(docu.getTipoDocumento());
			response = new ResponseEntity<>(salidaDoc, HttpStatus.OK);	
		}else {
			// controlar que no tenga documento...
			response = new ResponseEntity<>(salidaDoc, HttpStatus.OK);
		}
		
	
		return response;
	}
}
