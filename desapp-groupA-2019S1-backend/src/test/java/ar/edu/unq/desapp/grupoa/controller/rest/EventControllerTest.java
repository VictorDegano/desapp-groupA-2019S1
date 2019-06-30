package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.service.EventService;
import ar.edu.unq.desapp.grupoa.service.UserService;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.GuestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;


import java.io.UnsupportedEncodingException;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomString;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static org.junit.Assert.assertNotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes={TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class EventControllerTest {

    private MockMvc mockMvc;


    @Autowired
    private EventController eventController;

    @Autowired
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(this.eventController).build();
    }

    @Test
    public void whenITryToGetAFiestaAndThisOne_TheServiceReturnsIt() throws Exception {
        //Setup(Given)
        Fiesta fiesta = randomFiesta();
        eventService.create(fiesta);

        //Test(Then)
        ResultMatcher responseExpected = content().json(json(fiesta));

        this.mockMvc.perform(get("/event/1"))
                    .andExpect(status().isOk())
                    .andExpect(responseExpected);
    }

    @Test
    public void createsAFiesta() throws Exception {

        EventDTOService fiesta          = EventDTOService.from(randomFiesta());
        String  jsonFiesta       = json(fiesta);
        Integer fiestaId         = postAndReturnId(jsonFiesta);
        Event fiestaRetrieved    = this.eventService.getById(fiestaId);

        assertNotNull(fiestaRetrieved);
    }

    private Fiesta randomFiesta() {
        User user = randomUser();

        userService.create(user);

        Guest firstGuest = GuestBuilder.buildAGuest()
                .withUser(user)
                .withConfirmation(InvitationState.ACCEPTED)
                .build();

        User organizer = randomUser();

        userService.create(organizer);

        return   FiestaBuilder.buildAFiesta()
                .addGuest(firstGuest)
                .withOpenState()
                .withName(randomString())
                .withOrganizer(organizer)
                .build();
    }

    private Integer postAndReturnId(String json) throws Exception {
        MvcResult mvcResult = performPost(json);
        return integer(mvcResult);
    }

    private MvcResult performPost(String json) throws Exception {
        return mockMvc
                .perform(post("/event/create_fiesta").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated()).andReturn();
    }

    private Integer integer(MvcResult mvcResult) throws UnsupportedEncodingException {
        String result = mvcResult.getResponse().getContentAsString();
        return Integer.valueOf(result);
    }

    private String json(Object object) throws JsonProcessingException {
        return  objectMapper.writeValueAsString(object);
    }


}