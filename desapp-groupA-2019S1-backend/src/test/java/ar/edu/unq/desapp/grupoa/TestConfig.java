package ar.edu.unq.desapp.grupoa;

import ar.edu.unq.desapp.grupoa.controller.rest.EventController;
import ar.edu.unq.desapp.grupoa.controller.rest.UserController;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.service.EventService;
import ar.edu.unq.desapp.grupoa.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
    public EventController eventController(){
        return new EventController();
    }

    @Bean
    public EventService eventService(){
        return new EventService();
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JodaModule());
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }


}