package ar.edu.unq.desapp.grupoa.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
public class TestConfig {

    @Autowired
    private PersonajeDAO personajeDAO;

    @Bean
    public  PersonajeService personajeService(){
        return new PersonajeService(personajeDAO);
    }

}

