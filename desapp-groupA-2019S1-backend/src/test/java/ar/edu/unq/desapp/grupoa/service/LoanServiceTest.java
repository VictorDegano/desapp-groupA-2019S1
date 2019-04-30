package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.NoLoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.exception.UserDefaultException;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.Credit;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.junit.Test;

import static ar.edu.unq.desapp.grupoa.service.LoanService.accountIsInDebt;
import static ar.edu.unq.desapp.grupoa.service.LoanService.getCredit;
import static ar.edu.unq.desapp.grupoa.service.LoanService.payQuota;
import static ar.edu.unq.desapp.grupoa.service.LoanService.takeLoan;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountForUserWithRandomBalance;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountWithDebtAndNoBalance;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.accountWithDefaultedUser;
import static ar.edu.unq.desapp.grupoa.utils.factory.AccountFactory.loanedAccountWithRandomBalance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoanServiceTest {

    @Test(expected = LoanOnCourseException.class)
    public void cantTakeALoadIfTheAccountAlreadyHasOneInCourse() {
        Account account = accountForUserWithRandomBalance();

        takeLoan(takeLoan(account));
    }

    @Test(expected = UserDefaultException.class)
    public void cantTakeALoadIfTheAccountUserHasDefaulted() {
        Account account = accountWithDefaultedUser();

        Account accountLoaned = takeLoan(account);

        assertFalse(accountIsInDebt(accountLoaned));
    }

    @Test
    public void aQuoteOfTheLoanIsPaid() {
        Account account = loanedAccountWithRandomBalance();
        Integer balanceBefore = account.balance();
        Integer debtBefore    = account.debt();


        Account accountWithPayedQuota= payQuota(account);

        assertTrue(accountIsInDebt(accountWithPayedQuota));
        assertEquals(integer(debtBefore - 200), accountWithPayedQuota.debt());
        assertEquals(integer(balanceBefore - 200), accountWithPayedQuota.balance());
    }

    @Test(expected = NoLoanOnCourseException.class)
    public void aQuoteOfTheLoanIsPaidWhenNoLoanIsOnCourse() {
        payQuota(accountForUserWithRandomBalance());
    }

    @Test
    public void lastQuoteOfTheLoanIsPaid() {
        Account account = loanedAccountWithRandomBalance();

        Account accountAfterPayedQuota= payQuota(payUntilOneQuotaIsLeft(account));
        assertFalse(accountIsInDebt(accountAfterPayedQuota));
    }

    @Test(expected = NotEnoughCashToPerformOperation.class)
    public void aQuoteIsPaidWhenAccountDoesntHasEnoughMoneyToCoverTheQuoteAndTheUserDefaults() {
        Account account = accountWithDebtAndNoBalance();
        User accountUser =  account.getUser();
        assertFalse(accountUser.hasDefaulted());

        payQuota(account);

        assertTrue(accountUser.hasDefaulted());
    }

    @Test
    public void returnCreditOnCourseOfNormalUserWith5QuotasToPay() {
        Account account = loanedAccountWithRandomBalance();

        Credit credit = getCredit(account);

        assertEquals(credit.getQuotasToPay(), integer(5));
    }

    private Account payUntilOneQuotaIsLeft(Account account) {
        return payQuota(payQuota(payQuota(payQuota(account))));
    }
}
