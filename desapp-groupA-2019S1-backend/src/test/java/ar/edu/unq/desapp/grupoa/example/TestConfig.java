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
    public PersonajeDAO personajeDao;

    @Bean
    public PersonajeService personajeService(){
        return new PersonajeService();
    }

    @Bean
    public PersonajeController personajeController(){
        return new PersonajeController();
    }

}

