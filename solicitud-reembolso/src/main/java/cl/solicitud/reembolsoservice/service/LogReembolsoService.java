package cl.solicitud.reembolsoservice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.solicitud.reembolsoservice.model.LogReembolsoEntity;
import cl.solicitud.reembolsoservice.repository.LogReembolsoRepository;

@Service
public class LogReembolsoService {

	@Autowired
	private LogReembolsoRepository logRepository;
	private Logger logger = LogManager.getLogger(getClass());
	
	public void logReembolso(LogReembolsoEntity log) {
		try {
			logRepository.save(log);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public List<LogReembolsoEntity> obtieneHistorialSolicitud(Long idSolicitud){
		return logRepository.obtieneHistorialSolicitud(idSolicitud);
	}
}
