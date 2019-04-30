package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.NoLoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.exception.UserDefaultException;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.Credit;
import org.junit.Test;

import static ar.edu.unq.desapp.grupoa.service.LoanService.getCredit;
import static ar.edu.unq.desapp.grupoa.service.LoanService.payQuota;
import static ar.edu.unq.desapp.grupoa.service.LoanService.takeLoan;
import static ar.edu.unq.desapp.grupoa.model.utils.StreamUtils.repeat;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountForUserWithRandomBalance;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountWithDebtAndNoBalance;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountWithDefaultedUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoanServiceTest {

    @Test(expected = LoanOnCourseException.class)
    public void cantTakeALoadIfTheAccountAlreadyHasOneInCourse() {
        Account account = accountForUserWithRandomBalance();

        takeLoan(account);
        takeLoan(account);
    }

    @Test(expected = UserDefaultException.class)
    public void cantTakeALoadIfTheAccountUserHasDefaulted() {
        Account account = accountWithDefaultedUser();

        takeLoan(account);

        assertFalse(LoanService.accountIsInDebt(account));
    }

    @Test
    public void aQuoteOfTheLoanIsPaid() {
        Account account = accountForUserWithRandomBalance();
        takeLoan(account);
        Integer balanceBefore = account.balance();
        Integer debtBefore = account.debt();


        payQuota(account);

        assertTrue(LoanService.accountIsInDebt(account));
        assertEquals(integer(debtBefore-200), account.debt());
        assertEquals(integer(balanceBefore - 200), account.balance());
    }

    @Test(expected = NoLoanOnCourseException.class)
    public void aQuoteOfTheLoanIsPaidWhenNoLoanIsOnCourse() {
        Account account = accountForUserWithRandomBalance();

        payQuota(account);
    }

    @Test
    public void lastQuoteOfTheLoanIsPaid() {
        Account account = accountForUserWithRandomBalance();
        takeLoan(account);

        payUntilOneQuotaIsLeft(account);
        payQuota(account);
        assertFalse(LoanService.accountIsInDebt(account));
    }

    @Test(expected = NotEnoughCashToPerformOperation.class)
    public void aQuoteIsPaidWhenAccountDoesntHasEnoughMoneyToCoverTheQuoteAndTheUserDefaults(){
        Account account = accountWithDebtAndNoBalance();
        assertFalse(account.getUser().hasDefaulted());

        payQuota(account);

        assertTrue(account.getUser().hasDefaulted());
    }

    @Test
    public void returnCreditOnCourseOfNormalUserWith5QuotasToPay(){
        Account account = accountForUserWithRandomBalance();
        takeLoan(account);

        Credit credit = getCredit(account);

        assertEquals(credit.getQuotasToPay(), integer(5));
    }

    private void payUntilOneQuotaIsLeft(Account account) {
        repeat(4,()-> payQuota(account));
    }
}
