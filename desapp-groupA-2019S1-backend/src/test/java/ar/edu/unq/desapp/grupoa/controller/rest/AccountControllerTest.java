package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.CreditDTO;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;

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

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.getCredit;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.takeLoan;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes = {TestConfig.class})
@DataJpaTest
@RunWith(SpringRunner.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AccountController accountController;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(this.accountController).build();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JodaModule());
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void depositMoney() throws Exception {
        User user = getUser();
        Account account = user.getAccount();
        Integer balanceBefore = account.balance();
        Integer amountToDeposit = 100;

        //Test(Then)

        String url = String.format("/account/depositMoney/%s/%s", user.getId(), amountToDeposit);
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk());

        User userAfterUpdate = userDAO.findById(user.getId()).get();


        assertEquals(integer(balanceBefore + amountToDeposit), userAfterUpdate.getAccount().balance());
    }


    @Test
    public void extractMoney() throws Exception {
        User user = getUser();
        user.getAccount().deposit(100);
        userDAO.save(user);

        Integer balanceBefore = user.getAccount().balance();

        Integer amountToExtract = 33;

        //Test(Then)

        String url = String.format("/account/extractMoney/%s/%s", user.getId(), amountToExtract);
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk());

        User userAfterUpdate = userDAO.findById(user.getId()).get();


        assertEquals(integer(balanceBefore - amountToExtract), userAfterUpdate.getAccount().balance());
    }


    @Test
    public void loan() throws Exception {
        User user = getUser();

        Integer balanceBefore = user.getAccount().balance();


        //Test(Then)

        String url = String.format("/account/takeLoan/%s", user.getId());
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk());

        User userAfterUpdate = userDAO.findById(user.getId()).get();


        assertEquals(integer(balanceBefore + 1000), userAfterUpdate.getAccount().balance());
    }


    @Test
    public void creditsOnCourse() throws Exception {
        User user = getUser();
        takeLoan(user.getAccount());
        userDAO.save(user);

        CreditDTO creditDTO = CreditDTO.from(getCredit(user.getAccount()));
        //Test(Then)
        String dtoJson = json(creditDTO);

        //Test(Then)

        String url = String.format("/account/creditsOnCourse/%s", user.getId());
        String json = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CreditDTO creditRetrieved = objectMapper.readValue(json, CreditDTO.class);

        assertEquals(creditRetrieved.getHasDefaulted(), creditDTO.getHasDefaulted());
        assertEquals(creditRetrieved.getQuotasToPay(), creditDTO.getQuotasToPay());
        assertEquals(creditRetrieved.getUser().id, creditDTO.getUser().id);
    }

    @Test
    public void getMovements() throws Exception {
        User user = getUser();
        takeLoan(user.getAccount());
        user.getAccount().deposit(100);
        user.getAccount().extract(50);
        userDAO.save(user);

        List<Movement> movements = user.getAccount().getMovements();

        //Test(Then)

        String url = String.format("/account/movements/%s", user.getId());
        String json = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Movement> movementsRetrieved = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Movement.class));

        assertTrue(movementsRetrieved.stream().allMatch(movement -> movements.stream().anyMatch(movs -> movs.getId().equals(movement.getId()))));

    }

    @Test
    public void getUserBalance() throws Exception {
        User user = getUser();
        takeLoan(user.getAccount());
        user.getAccount().deposit(100);
        user.getAccount().extract(50);
        userDAO.save(user);

        Integer balance = user.getAccount().balance();

        //Test(Then)

        String url = String.format("/account/userBalance/%s", user.getId());
        String json = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Integer balanceRetrieved = objectMapper.readValue(json, Integer.class);

        assertEquals(balance,balanceRetrieved);

    }

    private User getUser() {
        User user = randomUser();
        userDAO.save(user);
        return user;
    }


    private String json(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }


}
