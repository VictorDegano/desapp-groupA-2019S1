package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.MovementType;
import org.junit.Before;
import org.junit.Test;



import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountForUserWithRandomBalance;
import static org.junit.Assert.assertEquals;



public class AccountTest {

    Integer randomAmount;

    @Before
    public void setup() {
        randomAmount = randomNumber();
    }

    @Test
    public void accountIsCreatedWithZeroBalanceAndZeroDebt() {
        Account account = new Account(randomUser());

        assertEquals(integer(0), account.balance());
        assertEquals(integer(0), account.debt());
    }

    @Test
    public void depositCashToAccount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();

        account.deposit(randomAmount,MovementType.CASH);

        assertEquals(integer(balanceBefore + randomAmount), account.balance());
    }

    @Test
    public void extractCashFromAccount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();

        Integer amountExtracted = account.extract(randomAmount);

        assertEquals(integer(balanceBefore - randomAmount), account.balance());
        assertEquals(integer(randomAmount),amountExtracted);
    }

    @Test
    public void addsDebtAndBalanceAndDebtGoesUpBySameAmount() {
        Account account = accountForUserWithRandomBalance();
        Integer balanceBefore = account.balance();
        Integer debtBefore = account.debt();

        account.addDebt(randomAmount);

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

        account.addDebt(-randomAmount);

        assertEquals(integer(balanceBefore - randomAmount), account.balance());
        assertEquals(integer(debtBefore - randomAmount), account.debt());

        assertEquals(
                integer(balanceBefore - account.balance()),
                integer(debtBefore - account.debt())
        );
    }

}
