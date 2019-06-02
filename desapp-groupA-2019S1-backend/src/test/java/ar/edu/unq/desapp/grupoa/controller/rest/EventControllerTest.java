package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.service.EventService;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void whenITryToGetAFiestaAndThisOne_TheServiceReturnsIt() throws Exception {
        //Setup(Given)
        Fiesta fiesta = FiestaBuilder.buildAFiesta()
                                     .withName("La Fiesta loca")
                                     .build();

        given(eventService.getById(1)).willReturn(fiesta);
        ResultMatcher responseExpected = content().json("{\"id\":null,\"name\":\"La Fiesta loca\",\"organizer\":null,\"guest\":[],\"goodsForGuest\":[],\"limitConfirmationDateTime\":null,\"confirmations\":null}");

        //Exercise(When)

        //Test(Then)
        this.mockMvc.perform(get("/event/1"))
                    .andExpect(status().isOk())
                    .andExpect(responseExpected);
    }

    @Test
    public void whenTryToGetTheRoot_GetHelloWorld() throws Exception {
        //Setup(Given)
        ResultMatcher responseExpected = content().string("Hello World");

        //Exercise(When)

        //Test(Then)
        this.mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(responseExpected);
    }
}