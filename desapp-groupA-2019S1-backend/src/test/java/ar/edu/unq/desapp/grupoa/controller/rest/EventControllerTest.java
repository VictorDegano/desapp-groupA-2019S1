package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.FiestaDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaGoodBuilder;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup(){
        this.mockMvc = standaloneSetup(this.eventController).build();
    }

    @Test
    public void whenITryToGetAFiestaAndThisOne_TheServiceReturnsIt() throws Exception {
        //Setup(Given)
        Fiesta fiesta = randomCreatedFiesta();
        eventDAO.save(fiesta);
        String fiestaDTOJson = json(FiestaDTO.from(fiesta));

        //Test(Then)
        ResultMatcher responseExpected = content().json(fiestaDTOJson);

        this.mockMvc.perform(get("/event/1"))
                    .andExpect(status().isOk())
                    .andExpect(responseExpected);
    }

    @Test
    public void createsAFiesta() throws Exception {

        FiestaDTO fiesta          = FiestaDTO.from(randomFiestaToCreate());
        String  jsonFiesta       = json(fiesta);
        Integer fiestaId         = postAndReturnId(jsonFiesta);
        Event fiestaRetrieved    = eventDAO.findById(fiestaId).get();

        assertNotNull(fiestaRetrieved);
    }


    private Fiesta randomFiestaToCreate() {
        User user = randomUser();

        userDAO.save(user);

        Guest firstGuest = GuestBuilder.buildAGuest()
                .withUser(user)
                .withConfirmation(InvitationState.PENDING)
                .build();

        User organizer = randomUser();

        userDAO.save(organizer);

        Good fiestaGood = FiestaGoodBuilder.buildAGood()
                .withName("Beer")
                .withPricesPerUnit(50)
                .withQuantityForPerson(1)
                .withFinalQuantity(1)
                .build();

        List<Good> fiestaGoods = new ArrayList<>();
        fiestaGoods.add(fiestaGood);


        return   FiestaBuilder.buildAFiesta()
                .withLimitConfirmationDateTime(LocalDateTime.now())
                .addGuest(firstGuest)
                .withOpenState()
                .withName("RandomFiesta")
                .withOrganizer(organizer)
                .withGoods(fiestaGoods)
                .build();
    }

    private Fiesta randomCreatedFiesta() {
        User user = randomUser();

        userDAO.save(user);

        Guest firstGuest = GuestBuilder.buildAGuest()
                .withUser(user)
                .withConfirmation(InvitationState.ACCEPTED)
                .build();

        User organizer = randomUser();

        userDAO.save(organizer);
        Good fiestaGood = FiestaGoodBuilder.buildAGood()
                .withName("Beer")
                .withPricesPerUnit(50)
                .withQuantityForPerson(1)
                .withFinalQuantity(1)
                .build();

        List<Good> fiestaGoods = new ArrayList<>();
        fiestaGoods.add(fiestaGood);


        return   FiestaBuilder.buildAFiesta()
                .withLimitConfirmationDateTime(LocalDateTime.now())
                .withCreationDate(LocalDateTime.now().minusDays(2))
                .withConfirmations(1)
                .addGuest(firstGuest)
                .withOpenState()
                .withName(randomString())
                .withOrganizer(organizer)
                .withGoods(fiestaGoods)
                .build();
    }

    private Integer postAndReturnId(String json) throws Exception {
        MvcResult mvcResult = performPost(json);
        return integer(mvcResult);
    }

    private MvcResult performPost(String json) throws Exception {
        return mockMvc
                .perform(post("/event/").contentType(MediaType.APPLICATION_JSON).content(json))
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