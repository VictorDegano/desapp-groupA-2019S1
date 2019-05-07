package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.exception.account.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.account.NoLoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.account.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.exception.account.UserDefaultException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.junit.Test;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.accountIsInDebt;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.getCredit;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.payQuota;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.takeLoan;
import static ar.edu.unq.desapp.grupoa.utils.Integer.integer;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.accountForRandomUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.withDefaultedUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.withLoan;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.withLoanAndNoBalance;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.withRandomBalance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Loan {

    @Test(expected = LoanOnCourseException.class)
    public void cantTakeALoadIfTheAccountAlreadyHasOneInCourse() {
        Account account = accountForRandomUser(withRandomBalance());

        takeLoan(takeLoan(account));
    }

    @Test(expected = UserDefaultException.class)
    public void cantTakeALoadIfTheAccountUserHasDefaulted() {
        Account account = accountForRandomUser(withDefaultedUser());

        Account accountLoaned = takeLoan(account);

        assertFalse(accountIsInDebt(accountLoaned));
    }

    @Test
    public void aQuoteOfTheLoanIsPaid() {
        Account account = accountForRandomUser(withLoan(),withRandomBalance());

        Integer balanceBefore = account.balance();
        Integer debtBefore    = account.debt();


        Account accountWithPayedQuota= payQuota(account);

        assertTrue(accountIsInDebt(accountWithPayedQuota));
        assertEquals(integer(debtBefore - 200), accountWithPayedQuota.debt());
        assertEquals(integer(balanceBefore - 200), accountWithPayedQuota.balance());
    }

    @Test(expected = NoLoanOnCourseException.class)
    public void aQuoteOfTheLoanIsPaidWhenNoLoanIsOnCourse() {
        Account account= accountForRandomUser(withRandomBalance());

        payQuota(account);
    }

    @Test
    public void lastQuoteOfTheLoanIsPaid() {
        Account account = accountForRandomUser(withLoan(),withRandomBalance());

        Account accountAfterPayedQuota= payQuota(payUntilOneQuotaIsLeft(account));
        assertFalse(accountIsInDebt(accountAfterPayedQuota));
    }

    @Test(expected = NotEnoughCashToPerformOperation.class)
    public void aQuoteIsPaidWhenAccountDoesntHasEnoughMoneyToCoverTheQuoteAndTheUserDefaults() {
        Account account = accountForRandomUser(withLoanAndNoBalance());

        User accountUser =  account.getUser();
        assertFalse(accountUser.hasDefaulted());

        payQuota(account);

        assertTrue(accountUser.hasDefaulted());
    }

    @Test
    public void returnCreditOnCourseOfNormalUserWith5QuotasToPay() {
        Account account = accountForRandomUser(withLoan(),withRandomBalance());

        Credit credit = getCredit(account);

        assertEquals(credit.getQuotasToPay(), integer(5));
    }

    private Account payUntilOneQuotaIsLeft(Account account) {
        return payQuota(payQuota(payQuota(payQuota(account))));
    }
}
