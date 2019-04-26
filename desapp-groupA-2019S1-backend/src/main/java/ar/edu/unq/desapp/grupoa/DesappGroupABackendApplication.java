package ar.edu.unq.desapp.grupoa;

import ar.edu.unq.desapp.grupoa.service.BootstrapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DesappGroupABackendApplication {

    @Autowired
    private BootstrapService bootstrapService;

    public static void main(String[] args) {
        SpringApplication.run(DesappGroupABackendApplication.class, args);
    }

    @Bean
    public void load() {
        bootstrapService.createExampleData();
    }

}
