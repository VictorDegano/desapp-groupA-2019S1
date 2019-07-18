package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.GoodDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.FiestaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.GoodDAO;
import ar.edu.unq.desapp.grupoa.service.GoodService;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaBuilder;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ContextConfiguration(classes = {TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class GoodControllerTest {


    @Autowired
    private GoodService goodService;
    @Autowired
    private GoodController goodController;
    @Autowired
    private GoodDAO goodDAO;
    @Autowired
    private EventDAO eventDAO;


    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.goodController)
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void getGoodById() throws Exception {
        Good good = getFiestaGood();
        goodDAO.save(good);

        String json = json(GoodDTO.fromFiestaGood(good));
        ResultMatcher responseExpected = content().json(json);


        String url = String.format("/good/%s", good.getId());
        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(responseExpected);
    }

    @Test
    public void modifyGoodAndCheckThatItsModified() throws Exception {
        FiestaGood good = getFiestaGood();

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(2))
                .withCreationDate(LocalDateTime.now().minusDays(2))
                .withConfirmations(1)
                .addGuest(getGuest())
                .withOpenState()
                .withName("pepeFiesta")
                .withOrganizer(getUser())
                .withGoods(asList(good))
                .build();

        eventDAO.save(fiesta);

        good.setFinalQuantity(200);
        String json = json(GoodDTO.fromFiestaGood(good));


        String url = String.format("/good");
        this.mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Event eventRetrieved = eventDAO.findById(fiesta.getId()).get();

        assertEquals(eventRetrieved.getGoodsForGuest().stream().findFirst().get().getId(), good.getId());
        assertEquals(200, (int) eventRetrieved.getGoodsForGuest().stream().findFirst().get().getFinalQuantity());
    }


    @Test
    public void deleteGoodAndCheckItsDeleted() throws Exception {
        FiestaGood good = getFiestaGood();

        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                .withLimitConfirmationDateTime(LocalDateTime.now().plusDays(2))
                .withCreationDate(LocalDateTime.now().minusDays(2))
                .withConfirmations(1)
                .addGuest(getGuest())
                .withOpenState()
                .withName("pepeFiesta")
                .withOrganizer(getUser())
                .withGoods(asList(good))
                .build();

        eventDAO.save(fiesta);

        String url = String.format("/good/delete/%s/%s",fiesta.getId(),good.getId());
        this.mockMvc.perform(delete(url))
                .andExpect(status().isOk());


        Event eventRetrieved = eventDAO.findById(fiesta.getId()).get();

        assertTrue(eventRetrieved.getGoodsForGuest().isEmpty());}


    private FiestaGood getFiestaGood() {
        return FiestaGoodBuilder.buildAGood()
                .withName("Beer")
                .withPricesPerUnit(50)
                .withQuantityForPerson(1)
                .withFinalQuantity(1)
                .build();
    }

    private String json(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
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
        return user;
    }

}
