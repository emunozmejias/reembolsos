package cl.privilegio.centrocostoservice.service;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.privilegio.centrocostoservice.model.ListaCentroCosto;
import cl.privilegio.centrocostoservice.model.PrivilegioCentroCosto;
import cl.privilegio.centrocostoservice.repository.CentroCostoRepository;

@Service
public class CentroCostoService {

	@Autowired
	private CentroCostoRepository centroCostoRepository;
	
	private Logger logger = LogManager.getLogger(getClass());
	
	private ResponseEntity response = null;
	
	public ResponseEntity<Collection<PrivilegioCentroCosto>> obtienePrivilegios(){
		return new ResponseEntity<>(centroCostoRepository.obtienePrivilegios(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> registraCentroCosto(PrivilegioCentroCosto privilegio){
		return new ResponseEntity<>(centroCostoRepository.save(privilegio), HttpStatus.OK);
	}
	
	public ResponseEntity<?> registraVariosPrivilegios(ListaCentroCosto lista){
		for (PrivilegioCentroCosto privilegio : lista.getLista()) {
			centroCostoRepository.save(privilegio);
		}
		response = new ResponseEntity(HttpStatus.OK); 
		return response;
	}
	
	public ResponseEntity eliminarPrivilegio(Long id) {
		PrivilegioCentroCosto privilegio = centroCostoRepository.findByIdCentroCosto(id);
		centroCostoRepository.delete(privilegio);
		response = new ResponseEntity(HttpStatus.OK);
		return response; 
	}
	
	public ResponseEntity eliminarAllPrivilegios(ListaCentroCosto lista) {
		
		for (PrivilegioCentroCosto privilegio : lista.getLista()) {
			PrivilegioCentroCosto privilegioBD = centroCostoRepository.findByIdCentroCosto(privilegio.getId());
			centroCostoRepository.delete(privilegioBD);
		}		
		
		response = new ResponseEntity(HttpStatus.OK);
		return response; 
	}	
	
	public ResponseEntity<Collection<PrivilegioCentroCosto>> obtieneCentroCosto(Long idUsuario){
		return new ResponseEntity<>(centroCostoRepository.obtieneCentroCosto(idUsuario), HttpStatus.OK);
	}
}
