package cl.proyecto.integracionservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class IntegracionServiceApplication {

	Logger logger = LogManager.getLogger(getClass());
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		logger.info("");
        return builder.sources(IntegracionServiceApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(IntegracionServiceApplication.class, args);
	}
}
