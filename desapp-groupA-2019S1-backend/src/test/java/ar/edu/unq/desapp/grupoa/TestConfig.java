package ar.edu.unq.desapp.grupoa;

import ar.edu.unq.desapp.grupoa.controller.rest.AccountController;
import ar.edu.unq.desapp.grupoa.controller.rest.EventController;
import ar.edu.unq.desapp.grupoa.controller.rest.GoodController;
import ar.edu.unq.desapp.grupoa.controller.rest.TemplateController;
import ar.edu.unq.desapp.grupoa.controller.rest.UserController;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.GoodDAO;
import ar.edu.unq.desapp.grupoa.persistence.TemplateDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.service.AccountService;
import ar.edu.unq.desapp.grupoa.service.EmailSenderService;
import ar.edu.unq.desapp.grupoa.service.EventService;
import ar.edu.unq.desapp.grupoa.service.GoodService;
import ar.edu.unq.desapp.grupoa.service.TemplateService;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@TestConfiguration // Esta annotation le dice a los test que es una clase de configuracion y no lo agrega al escaneo.
public class TestConfig {

    @Autowired
    public UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;


    @Autowired
    private GoodDAO goodDAO;

    @Autowired
    private TemplateDAO templateDAO;

    @Bean
    public UserController userController(){
        return new UserController();
    }

    @Bean
    public GoodController goodController(){
        return new GoodController();
    }

    @Bean
    public EventController eventController(){
        return new EventController();
    }

    @Bean
    public AccountController accountController(){
        return new AccountController();
    }

    @Bean
    public TemplateController templateController(){
        return new TemplateController();
    }


    @Bean
    public AccountService accountService(){
        return new AccountService();
    }

    @Bean
    public TemplateService templateService(){
        return new TemplateService();
    }

    @Bean
    public EventService eventService(){
        return new EventService();
    }

    @Bean
    public GoodService goodService(){
        return new GoodService();
    }

    @Bean
    public EmailSenderService emailSenderService(){
        return mock(EmailSenderService.class);
    }

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        return mock(JavaMailSender.class);
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