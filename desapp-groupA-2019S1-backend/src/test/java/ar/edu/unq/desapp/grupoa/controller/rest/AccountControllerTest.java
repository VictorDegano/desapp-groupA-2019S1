package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.TestConfig;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WithMockUser(username = "user1", password = "pwd", roles = "USER")
@ContextConfiguration(classes={TestConfig.class})
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
    public void setup(){
        this.mockMvc = standaloneSetup(this.accountController).build();
    }

    @Test
    public void depositMoney() throws Exception {
        User user = getUser();
        Account account = user.getAccount();
        Integer balanceBefore = account.balance();
        Integer amountToDeposit = 100;

        //Test(Then)

        String url = String.format("/account/depositMoney/%s/%s",user.getId(),amountToDeposit);
        this.mockMvc.perform(put(url))
                .andExpect(status().isOk());

        User userAfterUpdate = userDAO.findById(user.getId()).get();


        assertEquals(integer(balanceBefore + amountToDeposit), userAfterUpdate.getAccount().balance());
    }


    private User getUser() {
        User user = randomUser();
        userDAO.save(user);
        return user;
    }

}
