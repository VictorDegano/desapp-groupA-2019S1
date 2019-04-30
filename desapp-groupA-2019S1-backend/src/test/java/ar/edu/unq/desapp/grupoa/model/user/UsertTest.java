package ar.edu.unq.desapp.grupoa.model.user;

import org.junit.Test;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsertTest {

    @Test
    public void aUserStartsWithNormalState(){
        User user = randomUser();
        assertFalse(user.hasDefaulted());
        assertFalse(user.hasBeenDutiful());
        assertTrue(user.hasNormalState());
    }
}
