package ar.edu.unq.desapp.grupoa.model.account;

import org.junit.Before;
import org.junit.Test;


import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.newAccountForRandomUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.withRandomBalance;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static org.junit.Assert.assertEquals;



public class AccountTest {

    private Integer randomAmount;

    @Before
    public void setup() {
        randomAmount = randomNumber();
    }

    @Test
    public void accountIsCreatedWithZeroBalanceAndZeroDebt() {
        Account account = newAccountForRandomUser();

        assertEquals(integer(0), account.balance());
        assertEquals(integer(0), account.debt());
    }

    @Test
    public void depositCashToAccount() {
        Account account = newAccountForRandomUser(withRandomBalance());
        Integer balanceBefore = account.balance();

        account.deposit(randomAmount);

        assertEquals(integer(balanceBefore + randomAmount), account.balance());
    }

    @Test
    public void extractCashFromAccount() {
        Account account = newAccountForRandomUser(withRandomBalance());
        Integer balanceBefore = account.balance();

        account.extract(randomAmount);

        assertEquals(integer(balanceBefore - randomAmount), account.balance());
    }

    @Test
    public void addsDebtAndBalanceAndDebtGoesUpBySameAmount() {
        Account account = newAccountForRandomUser(withRandomBalance());
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
        Account account = newAccountForRandomUser(withRandomBalance());
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
