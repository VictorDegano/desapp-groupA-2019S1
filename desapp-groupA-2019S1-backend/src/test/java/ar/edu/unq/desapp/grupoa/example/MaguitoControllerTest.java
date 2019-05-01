package ar.edu.unq.desapp.grupoa.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ContextConfiguration(classes={TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class MaguitoControllerTest {


    @Autowired
    private  PersonajeService personajeService;
    @Autowired
    private  PersonajeController personajeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private Personaje maguito;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.personajeController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());

        maguito = new Personaje("Maguito", 20, new Item("sombrero de mago"));
    }


    @Test
    public void testCreatePersonajeAndReturnId() throws Exception {
        String  json       = json(maguito);
        Integer maguitoId  = postAndReturnId(json);
        Personaje maguitoRetrieved = this.personajeService.getById(maguitoId);

        assertEqualsPersonaje(maguito,maguitoRetrieved);
    }

    @Test
    public void testGivenIdReturnPersonaje() throws Exception {
        Integer maguitoId  = personajeService.create(maguito);
        Personaje maguitoRetrieved = getById(maguitoId);

        assertEqualsPersonaje(maguito,maguitoRetrieved);
    }

    @Test
    public void testGivenPersonajeIdAndItemPickUpItem() throws Exception {
        Integer maguitoId  = personajeService.create(maguito);
        Item baculo = new Item("baculo");
        String  json       = json(baculo);

        pickupItem(json,maguitoId);
        Personaje maguitoRetrieved = this.personajeService.getById(maguitoId);

        assertEqualsPersonaje(maguito,maguitoRetrieved);
        assertTrue(maguito.hasItem(baculo));
    }

    private void assertEqualsPersonaje(Personaje maguito, Personaje maguitoRetrieved) {
        assertEquals(maguito.getName(),maguitoRetrieved.getName());
        assertEquals(maguito.getLife(),maguitoRetrieved.getLife());
    }

    private Integer postAndReturnId(String json) throws Exception {
        MvcResult mvcResult = performPost(json);
        return integer(mvcResult);
    }

    private Personaje getById(Integer maguitoId) throws Exception {
        MvcResult mvcResult = performGetById(maguitoId);
        return personaje(mvcResult);
    }

    private MvcResult performPost(String json) throws Exception {
        return mockMvc.perform(post("/personaje").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn();
    }

    private MvcResult performGetById(Integer maguitoId) throws Exception {
        return mockMvc.perform(get("/personaje/" + maguitoId)).andExpect(status().isOk()).andReturn();
    }

    private void pickupItem(String json, Integer maguitoId) throws Exception {
        mockMvc.perform(put("/personaje/"+maguitoId).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
    }
    private Integer integer(MvcResult mvcResult) throws UnsupportedEncodingException {
        String result = mvcResult.getResponse().getContentAsString();
        return Integer.valueOf(result);
    }

    private Personaje personaje(MvcResult mvcResult) throws IOException {
        String retrievedCharString = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(retrievedCharString, Personaje.class);
    }

    private String json(Object object) throws JsonProcessingException {
       return  objectMapper.writeValueAsString(object);
    }

}

