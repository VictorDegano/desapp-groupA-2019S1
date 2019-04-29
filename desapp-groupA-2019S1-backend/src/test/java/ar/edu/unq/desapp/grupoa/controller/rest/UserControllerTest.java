package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.exception.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.service.UserService;
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


import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUserWithName;
import static ar.edu.unq.desapp.grupoa.utils.throwing.Rethrowing.rethrow;
import static org.aspectj.runtime.internal.Conversions.intValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ContextConfiguration(classes = {TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.userController)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void postUserAndReturnId() {
        User user = randomUser();
        Integer userId = postAndReturnId(user);
        User userRetrieved = this.userService.getById(userId);

        assertEqualsUser(user, userRetrieved);
    }

    @Test
    public void getUser() {
        User user = randomUser();
        userService.create(user);

        User userRetrieved = getUser(user.getId());

        assertEqualsUser(user, userRetrieved);
    }


    @Test
    public void putUser() {
        User user = randomUserWithName("Jose");
        Integer userId= userService.create(user);

        user.setFirstName("Pepe");
        putUser(user);

        User userRetrieved = this.userService.getById(userId);

        assertEquals("Pepe",userRetrieved.getFirstName());
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserInvalidId() {
        Integer randomId = intValue(Math.random());
        this.userService.getById(randomId);
    }

    private void assertEqualsUser(User user, User userRetrieved) {
        assertEquals(user.getFirstName(), userRetrieved.getFirstName());
        assertEquals(user.getBornDay(), userRetrieved.getBornDay());
        assertEquals(user.getEmail(), userRetrieved.getEmail());
        assertEquals(user.getLastName(), userRetrieved.getLastName());
        assertEquals(user.getPassword(), userRetrieved.getPassword());
    }

    private Integer postAndReturnId(User user) {
        String returnedId = performPost(json(user));
        return Integer.valueOf(returnedId);
    }

    private User getUser(Integer id) {
        String userJson = rethrow(() ->
                mockMvc.perform(get("/user/" + id))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString()
        );
        return user(userJson);
    }

    private void putUser( User user) {
        rethrow(() ->
                mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON).content(json(user)))
                .andExpect(status().isOk())
                );
    }

    private String performPost(String json) {
        return rethrow(() ->
                mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString()
        );
    }

    private User user(String json) {
        return rethrow(() -> objectMapper.readValue(json, User.class));
    }

    private String json(Object object) {
        return rethrow(() -> objectMapper.writeValueAsString(object));
    }

}
