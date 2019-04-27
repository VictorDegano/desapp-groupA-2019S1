package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.user.account.Loan;
import org.junit.Before;
import org.junit.Test;

import static org.apache.commons.collections.CollectionUtils.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoanTest {

    @Test
    public void aLoanIsCreatedWithSixQuotasOfTwoHundredAndtotalDebtOf1000(){
       Loan loan = new Loan();

       assertEquals(5,size(loan.quotas()));
       assertEquals(integer(1000),loan.debt());
       assertTrue  (allQuotasHavetheAmount(loan,200));
    }

    private boolean allQuotasHavetheAmount(Loan loan, Integer amount) {
        return loan.quotas().stream().allMatch(quota -> quota.amount().equals(amount));
    }


    private Integer integer(int number) {
        return number;
    }
}
