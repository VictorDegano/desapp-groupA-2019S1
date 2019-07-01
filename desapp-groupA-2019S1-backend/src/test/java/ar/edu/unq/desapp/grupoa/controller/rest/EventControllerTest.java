package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.CanastaDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.FiestaDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaGoodBuilder;
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
import java.util.function.Supplier;

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
    public void getFiesta() throws Exception {
        testGet(this::buildFiestaCreated,new FiestaDTO());
    }

    @Test
    public void getCanasta() throws Exception {
        testGet(this::buildCanasta,new CanastaDTO());
    }

    @Test
    public void createsAFiesta() throws Exception {
        testCreate(this::buildFiestaToCreate,new FiestaDTO());
    }

    @Test
    public void createsACanasta() throws Exception {
        testCreate(this::buildCanasta,new CanastaDTO());
    }


    //Precondition: The EventDTO has to be for the given event.
    public void testCreate(Supplier<Event> eventsupplier,EventDTO eventDTO) throws Exception {
        //Setup(Given)
        eventDTO      = eventDTO.from(eventsupplier.get());
        String  jsonEvent       = json(eventDTO);

        //Test(Then)
        Integer eventId         = postAndReturnId(jsonEvent);
        Event eventRetrieved    = eventDAO.findById(eventId).get();

        assertNotNull(eventRetrieved);
    }

    //Precondition: The EventDTO has to be for the given event.
    public void testGet(Supplier<Event> createEvent, EventDTO dto) throws Exception {
        //Setup(Given)
        Event event = createEvent.get();
        eventDAO.save(event);
        String dtoJson = json(dto.fromEvent(event));

        //Test(Then)
        ResultMatcher responseExpected = content().json(dtoJson);

        String url = "/event/"+event.getId();
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(responseExpected);
    }


    private Canasta buildCanasta() {
        Guest firstGuest = getGuest();
        User organizer = getUser();
        List<Good> canastaGoods = getCanastaGoods(firstGuest);

        return getCreatedCanasta(firstGuest, organizer, canastaGoods);
    }


    private Fiesta buildFiestaToCreate() {
        Guest firstGuest = getGuest();
        User organizer = getUser();
        List<Good> fiestaGoods = getFiestaGoods();

        return getFiestaToCreate(firstGuest, organizer, fiestaGoods);
    }

    private Fiesta buildFiestaCreated() {
        Guest firstGuest = getGuest();
        User organizer = getUser();
        List<Good> fiestaGoods = getFiestaGoods();

        return getCreatedFiesta(firstGuest, organizer, fiestaGoods);
    }


    private List<Good> getCanastaGoods(Guest firstGuest) {
        Good CanastaGood = CanastaGoodBuilder.buildAGood()
                .withName("Beer")
                .withPricesPerUnit(50)
                .withQuantityForPerson(1)
                .withUserThatOwnsTheGood(firstGuest)
                .build();

        List<Good> CanastaGoods = new ArrayList<>();
        CanastaGoods.add(CanastaGood);
        return CanastaGoods;
    }




    private List<Good> getFiestaGoods() {
        Good fiestaGood = FiestaGoodBuilder.buildAGood()
                .withName("Beer")
                .withPricesPerUnit(50)
                .withQuantityForPerson(1)
                .withFinalQuantity(1)
                .build();

        List<Good> fiestaGoods = new ArrayList<>();
        fiestaGoods.add(fiestaGood);
        return fiestaGoods;
    }

    private Guest getGuest() {
        User user = getUser();
        return GuestBuilder.buildAGuest()
                .withUser(user)
                .withConfirmation(InvitationState.PENDING)
                .build();
    }

    private User getUser() {
        User user = randomUser();
        userDAO.save(user);
        return user;
    }

    private Canasta getCreatedCanasta(Guest firstGuest, User organizer, List<Good> canastaGoods) {
        return CanastaBuilder.buildCanasta()
                .withName("pepeCanasta")
                .withOrganizer(organizer)
                .addGuest(firstGuest)
                .withOpenState()
                .withCreationDate(LocalDateTime.now())
                .withGoods(canastaGoods)
                .build();
    }

    private Fiesta getCreatedFiesta(Guest firstGuest, User organizer, List<Good> fiestaGoods) {
        return FiestaBuilder.buildAFiesta()
                .withLimitConfirmationDateTime(LocalDateTime.now())
                .withCreationDate(LocalDateTime.now().minusDays(2))
                .withConfirmations(1)
                .addGuest(firstGuest)
                .withOpenState()
                .withName("pepeFiesta")
                .withOrganizer(organizer)
                .withGoods(fiestaGoods)
                .build();
    }


    private Fiesta getFiestaToCreate(Guest firstGuest, User organizer, List<Good> fiestaGoods) {
        return FiestaBuilder.buildAFiesta()
                .withLimitConfirmationDateTime(LocalDateTime.now())
                .addGuest(firstGuest)
                .withOpenState()
                .withName("RandomFiesta")
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

    private String json(EventDTO object) throws JsonProcessingException {
        return  objectMapper.writeValueAsString(object);
    }


}