package cl.solicitud.reembolsoservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
/*@EnableAutoConfiguration(exclude = {
		 ErrorMvcAutoConfiguration.class
		})*/
public class ReembolsoServiceApplication {

	Logger logger = LogManager.getLogger(getClass());
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		logger.info("");
        return builder.sources(ReembolsoServiceApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ReembolsoServiceApplication.class, args);
	}
}
