package cl.privilegio.centrocostoservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CentroCostoApplication {

Logger logger = LogManager.getLogger(getClass());
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		logger.info("");
        return builder.sources(CentroCostoApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CentroCostoApplication.class, args);
	}
}
