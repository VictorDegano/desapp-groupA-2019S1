package ar.edu.unq.desapp.grupoa.model.account;

import org.junit.Before;
import org.junit.Test;


import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.accountForRandomUser;
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
        Account account = accountForRandomUser();

        assertEquals(integer(0), account.balance());
        assertEquals(integer(0), account.debt());
    }

    @Test
    public void depositCashToAccount() {
        Account account = accountForRandomUser(withRandomBalance());
        Integer balanceBefore = account.balance();

        account.deposit(randomAmount);

        assertEquals(integer(balanceBefore + randomAmount), account.balance());
    }

    @Test
    public void extractCashFromAccount() {
        Account account = accountForRandomUser(withRandomBalance());
        Integer balanceBefore = account.balance();

        account.extract(randomAmount);

        assertEquals(integer(balanceBefore - randomAmount), account.balance());
    }

    @Test
    public void addsDebtAndBalanceAndDebtGoesUpBySameAmount() {
        Account account = accountForRandomUser(withRandomBalance());
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
        Account account = accountForRandomUser(withRandomBalance());
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
