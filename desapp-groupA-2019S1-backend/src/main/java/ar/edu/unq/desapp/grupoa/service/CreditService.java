package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.NoLoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.exception.UserDefaultException;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.Credit;

public class CreditService {

    private CreditService(){}

    public static void takeLoan(Account account) {
       validateLoan(account);
       account.addDebt(1000);

    }

    private static void validateLoan(Account account) {
        if (accountIsInDebt(account))
            throw new LoanOnCourseException();
        if (account.getUser().hasDefaulted())
            throw new UserDefaultException();
    }

    public static void payQuota(Account account){
        validateQuota(account);
        account.addDebt(-200);
    }

    private static void validateQuota(Account account) {
        if (!accountIsInDebt(account))
            throw new NoLoanOnCourseException();
        if (account.balance() <= 0){
            account.getUser().defaultUser();
            throw new NotEnoughCashToPerformOperation("User doesn't has enough cash to pay the quota");
        }
    }

    public static Credit getCredit(Account account){
        return new Credit(account.getUser(),quotasToPay(account.debt()));
    }

    private static Integer quotasToPay(Integer debt) {
        return debt/200;
    }

    public static boolean accountIsInDebt(Account account) {
        return account.debt() > 0;
    }
}
