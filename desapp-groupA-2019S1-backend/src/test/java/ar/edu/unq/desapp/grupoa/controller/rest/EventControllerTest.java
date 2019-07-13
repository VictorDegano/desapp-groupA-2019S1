package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.BaquitaComunitariaDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.BaquitaRepresentativesDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.CanastaDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.EventDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.FiestaDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaComunitary;
import ar.edu.unq.desapp.grupoa.model.event.baquita.BaquitaRepresentatives;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.GuestDAO;
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

import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.newRandomBaquitaComunitaryWithOwner;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.withGood;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaComunitaryBuilder.withUnconfirmedGuest;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.newBaquitaRepresentativesWithOwner;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withConfirmedRepresentative;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withGoodRepresentatives;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withLoadedGoodFrom;
import static ar.edu.unq.desapp.grupoa.utils.builder.BaquitaRepresentativesBuilder.withUnconfirmedGuestForBaquitaRepresentatives;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = {TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class EventControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private EventController eventController;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private GuestDAO guestDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.eventController).build();
    }

    @Test
    public void getFiesta() throws Exception {
        testGet(this::buildFiestaCreated, new FiestaDTO());
    }

    @Test
    public void getCanasta() throws Exception {
        testGet(this::buildCanasta, new CanastaDTO());
    }

    @Test
    public void getBaquitaComunitaria() throws Exception {
        testGet(this::buildBaquitacomunitaria, new BaquitaComunitariaDTO());
    }

    @Test
    public void getBaquitaRepresentatives() throws Exception {
        testGet(this::buildBaquitaRepresentatives, new BaquitaRepresentativesDTO());
    }

    @Test
    public void createsAFiesta() throws Exception {
        testCreate(this::buildFiestaToCreate, new FiestaDTO());
    }

    @Test
    public void createsACanasta() throws Exception {
        testCreate(this::buildCanasta, new CanastaDTO());
    }

    @Test
    public void createsABaquitaComunitaria() throws Exception {
        testCreate(this::buildBaquitacomunitaria, new BaquitaComunitariaDTO());
    }


    @Test
    public void createsBaquitaRepresentatives() throws Exception {
        testCreate(this::buildBaquitaRepresentatives, new BaquitaRepresentativesDTO());
    }


    @Test
    public void confirmAsistenceToFiesta() throws Exception {
        Guest guestToConfirm = getGuest();
        Event event = getCreatedFiesta(guestToConfirm, getUser(), getFiestaGoods());
        eventDAO.save(event);

        testConfirmAsistance(guestToConfirm, event);
    }

    @Test
    public void confirmAsistenceToCanasta() throws Exception {
        Guest guestToConfirm = getGuest();
        Event event = getCreatedCanasta(guestToConfirm, getUser(), getCanastaGoods(guestToConfirm));
        eventDAO.save(event);

        testConfirmAsistance(guestToConfirm, event);
    }

    @Test
    public void confirmAsistenceToBaquitaComunitaria() throws Exception {
        Guest guestToConfirm = getGuest();
        Event event = getCreatedBaquitaComunitary(guestToConfirm, getUser());
        eventDAO.save(event);

        testConfirmAsistance(guestToConfirm, event);
    }

    @Test
    public void confirmAsistenceToBaquitaRepresentatives() throws Exception {
        Guest guestToConfirm = getGuest();
        Event event = getCreatedBaquitaRepresentatives(getGuest(), guestToConfirm, getUser());
        eventDAO.save(event);

        testConfirmAsistance(guestToConfirm, event);
    }

    @Test
    public void testOwnAGoodForCanasta() throws Exception {
        //Setup(Given)
        User owner = getUser();
        Guest firstGuest = getGuest();
        Good canastaGood = CanastaGoodBuilder.buildAGood()
                .withName("Beer")
                .withPricesPerUnit(50)
                .withQuantityForPerson(1)
                .build();

        List<Good> canastaGoods = new ArrayList<>();
        canastaGoods.add(canastaGood);

        Canasta canasta = CanastaBuilder.buildCanasta()
                .withName("pepeCanasta")
                .withOrganizer(owner)
                .addGuest(firstGuest)
                .withOpenState()
                .withCreationDate(LocalDateTime.now())
                .withGoods(canastaGoods)
                .build();

        canasta.confirmUser(firstGuest.getUser());

        eventDAO.save(canasta);

        assertFalse(canasta.getGoodsForGuest().stream().anyMatch(good -> good.getUserThatOwnsTheGood() != null && good.getUserThatOwnsTheGood().getId().equals(firstGuest.getUser().getId())));


        //Test(Then)
        String url = String.format("/event/ownCanastaGood/%s/%s/%s", canasta.getId(), firstGuest.getUser().getId(), canastaGood.getId());
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk());

        Event eventRetrieved = eventDAO.findById(canasta.getId()).get();
        assertTrue(eventRetrieved.getGoodsForGuest().stream().anyMatch(good -> good.getUserThatOwnsTheGood().getId().equals(firstGuest.getUser().getId())));

    }


    @Test
    public void testOwnABaquitaGood() throws Exception {
        //Setup(Given)
        User owner = getUser();
        Guest representative = getGuest();

        Good good = new Good();
        good.setPricePerUnit(50);
        good.setQuantityForPerson(1);
        good.setName("Beer");


        BaquitaRepresentatives baquita = newBaquitaRepresentativesWithOwner(
                owner,
                withConfirmedRepresentative(representative),
                withGoodRepresentatives(good)
        );


        baquita.confirmAsistancesOf(representative);

        eventDAO.save(baquita);

        assertTrue(baquita.getLoadedGoods().isEmpty());


        //Test(Then)
        String url = String.format("/event/ownBaquitaGood/%s/%s/%s", baquita.getId(), representative.getUser().getId(), good.getId());
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk());

        Event eventRetrieved = eventDAO.findById(baquita.getId()).get();

        assertFalse( eventRetrieved.getLoadedGoods().isEmpty());
        assertTrue(eventRetrieved.getLoadedGoods().stream().anyMatch(loadedGood -> loadedGood.getGuest().getId().equals(representative.getId())));

    }

    private void testConfirmAsistance(Guest guestToConfirm, Event event) throws Exception {
        Integer confirmationsBeforeConfirm = event.getConfirmations();

        String url = String.format("/event/confirmAsistance/%s/%s/", event.getId(), guestToConfirm.getId());
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk());

        Event eventUpdated = eventDAO.findById(event.getId()).get();
        Guest guestUpdated = guestDAO.findById(guestToConfirm.getId()).get();

        assertEquals(eventUpdated.getConfirmations(), new Integer(confirmationsBeforeConfirm + 1));
        assertTrue(guestUpdated.isconfirmed());
    }


    //Precondition: The EventDTO has to be for the given event.
    public void testCreate(Supplier<Event> eventsupplier, EventDTO eventDTO) throws Exception {
        //Setup(Given)
        eventDTO = eventDTO.from(eventsupplier.get());
        String jsonEvent = json(eventDTO);

        //Test(Then)
        Integer eventId = postAndReturnId(jsonEvent);
        Event eventRetrieved = eventDAO.findById(eventId).get();

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

        String url = "/event/" + event.getId();
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(responseExpected);
    }


    private Canasta buildCanasta() {
        Guest firstGuest = getGuest();

        return getCreatedCanasta(firstGuest, getUser(), getCanastaGoods(firstGuest));
    }

    private BaquitaComunitary buildBaquitacomunitaria() {
        return getCreatedBaquitaComunitary(getGuest(), getUser());
    }

    private Event buildBaquitaRepresentatives() {
        return getCreatedBaquitaRepresentatives(getGuest(), getGuest(), getUser());
    }


    private Fiesta buildFiestaToCreate() {
        return getFiestaToCreate(getGuest(), getUser(), getFiestaGoods());
    }

    private Fiesta buildFiestaCreated() {
        return getCreatedFiesta(getGuest(), getUser(), getFiestaGoods());
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


    private Good getDefaultGood() {
        Good good = new Good();
        good.setPricePerUnit(50);
        good.setQuantityForPerson(1);
        good.setName("Beer");
        return good;
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

    private Event getCreatedBaquitaRepresentatives(Guest representative, Guest guest, User owner) {
        return newBaquitaRepresentativesWithOwner(
                owner,
                withConfirmedRepresentative(representative),
                withUnconfirmedGuestForBaquitaRepresentatives(guest),
                withLoadedGoodFrom(representative, 70)
        );
    }

    private BaquitaComunitary getCreatedBaquitaComunitary(Guest firstGuest, User organizer) {
        return newRandomBaquitaComunitaryWithOwner(organizer,
                withUnconfirmedGuest(firstGuest),
                withGood(getDefaultGood())
        );
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
                .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(2))
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
        return objectMapper.writeValueAsString(object);
    }


}