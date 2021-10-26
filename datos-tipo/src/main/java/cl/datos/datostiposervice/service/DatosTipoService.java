package cl.datos.datostiposervice.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.datos.datostiposervice.model.Estado;
import cl.datos.datostiposervice.model.Parametros;
import cl.datos.datostiposervice.model.PrivilegioUsuario;
import cl.datos.datostiposervice.model.TipoBoleta;
import cl.datos.datostiposervice.repository.DatosTipoRepository;
import cl.datos.datostiposervice.repository.ParametrosRepository;
import cl.datos.datostiposervice.repository.PrivilegioUsuarioRepository;
import cl.datos.datostiposervice.repository.TipoRepository;

@Service
public class DatosTipoService {

	@Autowired
	private DatosTipoRepository datosTipoRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private PrivilegioUsuarioRepository privilegioRepository;

	@Autowired
	private ParametrosRepository parametrosRepository;

	public ResponseEntity<Collection<Estado>> obtieneAllEstados() {
		return new ResponseEntity<>(datosTipoRepository.findAll(Sort.by(Sort.Direction.ASC, "idEstado")), HttpStatus.OK);
	}

	public ResponseEntity<Collection<TipoBoleta>> obtieneAllTipos() {
		return new ResponseEntity<>(tipoRepository.findAll(Sort.by(Sort.Direction.ASC, "idTipo")), HttpStatus.OK);
	}

	public ResponseEntity<PrivilegioUsuario> obtienePrivilegioUsuario(Integer idUsuario) {
		return new ResponseEntity<>(privilegioRepository.obtienePrivilegio(idUsuario), HttpStatus.OK);
	}

	public ResponseEntity<Collection<PrivilegioUsuario>> obtieneAllPrivilegioUsuario() {
		List<PrivilegioUsuario> listaPrivilegios = privilegioRepository.obtieneAllPrivilegioUsuario();
		return new ResponseEntity<>(listaPrivilegios, HttpStatus.OK);
	}

	public ResponseEntity<Collection<Parametros>> obtieneParametros(String tipo, String codigo) {
		return new ResponseEntity<>(parametrosRepository.obtieneParametros(tipo, codigo, codigo), HttpStatus.OK);
	}

}
