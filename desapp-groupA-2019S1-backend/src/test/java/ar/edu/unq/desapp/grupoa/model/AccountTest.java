package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import org.junit.Before;
import org.junit.Test;


import static ar.edu.unq.desapp.grupoa.model.account.Account.newAccount;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountForUserWithRandomBalance;
import static org.junit.Assert.assertEquals;



public class AccountTest {

    private Integer randomAmount;

    @Before
    public void setup() {
        randomAmount = randomNumber();
    }

    @Test
    public void accountIsCreatedWithZeroBalanceAndZeroDebt() {
        Account account = newAccount(randomUser());

        assertEquals(integer(0), account.balance());
        assertEquals(integer(0), account.debt());
    }

    @Test
    public void depositCashToAccount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();

        account.deposit(randomAmount);

        assertEquals(integer(balanceBefore + randomAmount), account.balance());
    }

    @Test
    public void extractCashFromAccount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();

        account.extract(randomAmount);

        assertEquals(integer(balanceBefore - randomAmount), account.balance());
    }

    @Test
    public void addsDebtAndBalanceAndDebtGoesUpBySameAmount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();
        Integer debtBefore = account.debt();

        account.debt(randomAmount);

        assertEquals(integer(balanceBefore + randomAmount), account.balance());
        assertEquals(integer(debtBefore + randomAmount), account.debt());

        assertEquals(
                integer(balanceBefore - account.balance()),
                integer(debtBefore - account.debt())
        );
    }

    @Test
    public void takeDebtAndBalanceAndDebtGoesDownBySameAmount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();
        Integer debtBefore = account.debt();

        account.debt(-randomAmount);

        assertEquals(integer(balanceBefore - randomAmount), account.balance());
        assertEquals(integer(debtBefore - randomAmount), account.debt());

        assertEquals(
                integer(balanceBefore - account.balance()),
                integer(debtBefore - account.debt())
        );
    }

}
