package cl.detalle.proyectoservice.service;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cl.detalle.proyectoservice.model.EstadoServicio;
import cl.detalle.proyectoservice.model.ListaProyectos;
import cl.detalle.proyectoservice.model.Proyecto;
import cl.detalle.proyectoservice.repository.ProyectoRepository;

@Service
public class ProyectoService {

	@Autowired
	private ProyectoRepository proyectoRepository;

	private ResponseEntity response = null;

	private Logger logger = LogManager.getLogger(getClass());

	public ResponseEntity<Collection<Proyecto>> obtieneProyectosUsuario(int idUsuario) {
		return new ResponseEntity<>(proyectoRepository.obtieneProyectosByUsuario(idUsuario), HttpStatus.OK);
	}

	public ResponseEntity<Collection<Proyecto>> obtieneAllCentroCostos() {
		return new ResponseEntity<>(proyectoRepository.obtieneAllCentroCostos(), HttpStatus.OK);
	}
	
	public ResponseEntity<Proyecto> obtieneProyectosByIdProyecto(int idProyecto) {
		return new ResponseEntity<>(proyectoRepository.obtieneProyectosByIdProyecto(idProyecto), HttpStatus.OK);
	}
	
	public ResponseEntity<Collection<Proyecto>> obtieneProyectosUsuarioTodos(int idUsuario) {
		return new ResponseEntity<>(proyectoRepository.obtieneProyectosByUsuarioTodos(idUsuario), HttpStatus.OK);
	}

	public ResponseEntity<EstadoServicio> actualizaPrespuesto(ListaProyectos listaPresupuesto) {
		boolean estadoActualizacion = true;
		EstadoServicio estado = new EstadoServicio();		
		if (listaPresupuesto.getProyecto() != null) {
			for (Proyecto proyecto : listaPresupuesto.getProyecto()) {
				logger.info("Proyecto entrada: " +proyecto.getNombreProyecto() + " Presupuesto de entrada a descontar" + proyecto.getPresupuesto());
				Proyecto proyectoBD = proyectoRepository.obtieneProyectosByIdProyecto(proyecto.getIdProyecto());
				if (proyectoBD.getPresupuesto() != null) {
					if (proyecto.getPresupuesto() < 0) {
						proyectoBD.setPresupuesto(0);
					} else {
						proyectoBD.setPresupuesto(proyecto.getPresupuesto());
					}
				} else {
					proyectoBD.setPresupuesto(0);
				}				
				
				try {
					proyectoRepository.save(proyectoBD);
				} catch (Exception e) {
					estadoActualizacion = false;
				}
			}
		} else {
			System.out.println("Error");
		}

		if (estadoActualizacion) {
			estado.setEstado(true);
			estado.setDescripcion("Presupuesto actualizado!");
			response = new ResponseEntity<>(estado, HttpStatus.OK);
		} else {
			estado.setEstado(false);
			estado.setDescripcion("Error al actualizar presupuesto de proyecto");
			response = new ResponseEntity<>(estado, HttpStatus.EXPECTATION_FAILED);
		}
		return response;
	}

}
