package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import org.junit.Test;

import static ar.edu.unq.desapp.grupoa.service.PaymentService.extractCash;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountWithNoBalance;
import static org.junit.Assert.assertEquals;

public class PaymentServiceTest {


    @Test(expected = NotEnoughCashToPerformOperation.class)
    public void testCantTakeMoneyIfNoMoneyOnAccount() {
        Account account = accountWithNoBalance();

        extractCash(account,200);
        assertEquals(account.balance(), integer(0));
    }


}
