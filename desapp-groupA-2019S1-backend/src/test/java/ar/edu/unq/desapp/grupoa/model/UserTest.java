package ar.edu.unq.desapp.grupoa.model;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User newUser;
    @Before
    public void setUp() throws Exception {
        newUser = new User("Tony","Jason","tonyjason@gmail.com","1234", new DateTime(2017,1,1,0,0,0));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void whenICreateANewUserTheDataIsWhatItShould(){

        assertEquals(newUser.getFirstName(),"Tony");
        assertEquals(newUser.getLastName(),"Jason");
        assertEquals(newUser.getEmail(),"tonyjason@gmail.com");
        assertEquals(newUser.getPassword(),"1234");
        assertEquals(newUser.getBornDay().getYear(),2017);
        assertEquals(newUser.getBornDay().getMonthOfYear(),1);
        assertEquals(newUser.getBornDay().getDayOfMonth(),1);
    }
}