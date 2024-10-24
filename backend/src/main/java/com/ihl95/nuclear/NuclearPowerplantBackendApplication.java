package com.ihl95.nuclear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.logging.Logger;

@SpringBootApplication
public class NuclearPowerplantBackendApplication {

	private static final Logger logger = Logger.getLogger(NuclearPowerplantBackendApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(NuclearPowerplantBackendApplication.class, args);
		logger.info("La aplicación se ha levantado en el puerto: 8080");
	}

}