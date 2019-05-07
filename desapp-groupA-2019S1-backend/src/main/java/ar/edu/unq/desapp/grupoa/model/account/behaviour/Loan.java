package ar.edu.unq.desapp.grupoa.model.account.behaviour;

import ar.edu.unq.desapp.grupoa.exception.account.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.account.NoLoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.account.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.exception.account.UserDefaultException;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.Credit;

public class Loan {

    private Loan(){}

    public static Account takeLoan(Account account) {
       validateLoan(account);
       return account.debt(1000);
    }

    public static Account payQuota(Account account){
        validateQuota(account);
        return account.payDebt(200);
    }

    public static Credit getCredit(Account account){
        return new Credit(account.getUser(),quotasToPay(account.debt()));
    }


    public static boolean accountIsInDebt(Account account) {
        return account.debt() > 0;
    }

    private static void validateLoan(Account account) {
        if (accountIsInDebt(account))
            throw new LoanOnCourseException();
        if (account.getUser().hasDefaulted())
            throw new UserDefaultException();
    }

    private static void validateQuota(Account account) {
        if (!accountIsInDebt(account))
            throw new NoLoanOnCourseException();
        if (account.balance() <= 0){
            account.getUser().defaultUser();
            throw new NotEnoughCashToPerformOperation("User doesn't has enough cash to pay the quota");
        }
    }
    private static Integer quotasToPay(Integer debt) {
        return debt/200;
    }
}
