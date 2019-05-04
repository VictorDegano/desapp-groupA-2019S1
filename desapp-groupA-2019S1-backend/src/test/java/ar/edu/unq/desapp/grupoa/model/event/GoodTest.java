package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.exception.event.GoodTypeException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.utils.builder.CanastaGoodBuilder;
import ar.edu.unq.desapp.grupoa.utils.builder.FiestaGoodBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoodTest {

    @Test(expected = GoodTypeException.class)
    public void whenTryToGetTheUserThatOwnsAFiestaGood_GetAnException() {
        //Setup(Given)
        Good fiestaGood = FiestaGoodBuilder.buildAGood().build();

        //Exercise(When)
        fiestaGood.getUserThatOwnsTheGood();

        //Test(When)
    }

    @Test
    public void whenTryToGetTheUserThatOwnsACanastaGoodAndDontHaveOne_ReturnNull() {
        //Setup(Given)
        Good canastaGood = CanastaGoodBuilder.buildAGood().build();

        //Exercise(When)
        User owner = canastaGood.getUserThatOwnsTheGood();

        //Test(When)
        assertNull(owner);
    }

    @Test
    public void whenMultiplyFinalQuantityOfACanastaGoodByThree_TheTotalCostDontChange() {
        //Setup(Given)
        Good canastaGood = CanastaGoodBuilder.buildAGood().withPricesPerUnit(50).withQuantityForPerson(1).build();
        Integer totalCostBefore = canastaGood.totalCost();

        //Exercise(When)
        canastaGood.multiplyFinalQuantityBy(3);

        //Test(When)
        assertEquals(totalCostBefore, canastaGood.totalCost());
    }

    @Test
    public void whenMultiplyFinalQuantityOfAFiestaGoodByThree_TheTotalCostChange() {
        //Setup(Given)
        Good fiestaGood = FiestaGoodBuilder.buildAGood().withPricesPerUnit(50).withQuantityForPerson(1).withFinalQuantity(1).build();
        Integer totalCostBefore = fiestaGood.totalCost();

        //Exercise(When)
        fiestaGood.multiplyFinalQuantityBy(3);

        //Test(When)
        assertNotEquals(totalCostBefore, fiestaGood.totalCost());
    }

    @Test
    public void ifAskTheFinalQuantityOfAFiestaGoodThatHaveAQuantityPerUnitOfThreeAndMultiplyTheFinalQuantityBy3_ReturnNine() {
        //Setup(Given)
        Good fiestaGood = FiestaGoodBuilder.buildAGood().withQuantityForPerson(3).build();

        //Exercise(When)
        fiestaGood.multiplyFinalQuantityBy(3);

        //Test(When)
        assertEquals(Integer.valueOf(9), fiestaGood.finalQuantity());
    }

    @Test
    public void ifAskTheFinalQuantityOfACanastaGoodThatHaveAQuantityPerUnitOfThreeAndMultiplyTheFinalQuantityBy3_Return3() {
        //Setup(Given)
        Good canastaGood = CanastaGoodBuilder.buildAGood().withQuantityForPerson(3).build();

        //Exercise(When)
        canastaGood.multiplyFinalQuantityBy(3);

        //Test(When)
        assertEquals(Integer.valueOf(3), canastaGood.finalQuantity());
    }
}