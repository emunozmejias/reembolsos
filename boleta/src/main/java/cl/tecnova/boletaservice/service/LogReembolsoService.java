package cl.tecnova.boletaservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.tecnova.boletaservice.model.LogReembolsoEntity;
import cl.tecnova.boletaservice.repository.LogReembolsoRepository;

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
}
