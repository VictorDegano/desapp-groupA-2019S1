package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.GuestDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.TemplateDTO;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.TemplateDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.service.TemplateService;
import ar.edu.unq.desapp.grupoa.utils.builder.TemplateBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = {TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class TemplateControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TemplateController templateController;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private TemplateDAO templateDAO;

    @Autowired
    private TemplateService templateService;


    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.templateController).build();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JodaModule());
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void givenAnEventTypeReturnsTemplates() throws Exception {
        Template asadoConLosPibes = getAsadoConLosPibes();
        Template picadaConLosPibes = getPicadaConLosPibes();
        Template picadaSolo = getPreviaBajonera();
        Template fiestaLoca = getFiestaLoca();

        List<Template> templates = asList(asadoConLosPibes,picadaConLosPibes,picadaSolo,fiestaLoca);

        String url = String.format("/templates/%s", EventType.FIESTA.toString());

        String json = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<TemplateDTO> templatesRetrieved = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, TemplateDTO.class));
        assertTrue(templateListsAreEqual(templates, templatesRetrieved));
    }

    @Test
    public void anUserUsesATemplate() throws Exception {
        User user = getUser();
        User pepe = getUser();
        Template template = getAsadoConLosPibes();

        List<GuestDTO> guests = asList(GuestDTO.from(new Guest(pepe)));
        String json = json(guests);


        String url = String.format("/templates/%s/%s", template.getId(), user.getId(),user.getId());
                mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(eventDAO.findAll().stream().anyMatch(it -> it.getName().equals(template.getName())));
    }



    private User getUser() {
        User user = randomUser();
        userDAO.save(user);
        return user;
    }
    private Template getAsadoConLosPibes() {
        Template template =  TemplateBuilder.buildATemplate()
                .withName("Asado De Los Pibes")
                .withEventType(EventType.FIESTA)
                .addGood(getCerveza())
                .addGood(getAsado())
                .build();

        templateDAO.save(template);
        return template;
    }

    private Template getFiestaLoca() {
        Template template = TemplateBuilder.buildATemplate()
                .withName("Fiesta loca")
                .withEventType(EventType.FIESTA)
                .addGood(getAsado())
                .addGood(getAsado())
                .build();

        templateDAO.save(template);
        return template;
    }

    private Template getPreviaBajonera() {
        Template template = TemplateBuilder.buildATemplate()
                .withName("Previa Bajonera")
                .withEventType(EventType.FIESTA)
                .addGood(getPapitas())
                .addGood(getPapitas())
                .addGood(getCerveza())
                .addGood(getCerveza())
                .build();

        templateDAO.save(template);
        return template;
    }

    private Template getPicadaConLosPibes() {
        Template template =  TemplateBuilder.buildATemplate()
                .withName("Picada Con Los Pibes")
                .withEventType(EventType.FIESTA)
                .addGood(getPapitas())
                .addGood(getPicada())
                .build();

        templateDAO.save(template);
        return template;
    }

    private Good getCerveza() {
        Good cerveza = new Good();
        cerveza.setPricePerUnit(50);
        cerveza.setQuantityForPerson(1);
        cerveza.setName("Beer");
        return cerveza;
    }

    private Good getAsado() {
        Good goodCarne = new Good();
        goodCarne.setPricePerUnit(70);
        goodCarne.setQuantityForPerson(5);
        goodCarne.setName("Asado");
        return goodCarne;
    }


    private Good getPicada() {
        Good picada = new Good();
        picada.setPricePerUnit(2);
        picada.setQuantityForPerson(6);
        picada.setName("Picada");
        return picada;
    }

    private Good getPapitas() {
        Good papitas = new Good();
        papitas.setPricePerUnit(15);
        papitas.setQuantityForPerson(3);
        papitas.setName("papitas");
        return papitas;
    }

    private boolean templateListsAreEqual(List<Template> templates, List<TemplateDTO> templatesRetrieved) {
        return templatesRetrieved.stream().allMatch(templateRetrieved -> templates.stream().anyMatch(template -> template.getId().equals(templateRetrieved.getId())));
    }

    private String json(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
