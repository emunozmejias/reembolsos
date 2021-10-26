package cl.tecnova.boletaservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BoletaServiceApplication {
	
	private Logger logger = LogManager.getLogger(getClass());
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		logger.info("Probando log");
        return builder.sources(BoletaServiceApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(BoletaServiceApplication.class, args);
	}
}
