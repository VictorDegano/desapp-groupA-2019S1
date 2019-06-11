package ar.edu.unq.desapp.grupoa;

import ar.edu.unq.desapp.grupoa.controller.rest.UserController;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.service.EventService;
import ar.edu.unq.desapp.grupoa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@TestConfiguration // Esta annotation le dice a los test que es una clase de configuracion y no lo agrega al escaneo.
public class TestConfig {

    @Autowired
    public UserDAO userDAO;

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public UserController userController(){
        return new UserController();
    }

    @Bean
    public EventService eventService(){
        return new EventService();
    }

}