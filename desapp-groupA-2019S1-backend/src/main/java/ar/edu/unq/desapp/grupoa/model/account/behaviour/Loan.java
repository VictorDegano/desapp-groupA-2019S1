package ar.edu.unq.desapp.grupoa.model.account.behaviour;

import ar.edu.unq.desapp.grupoa.exception.account.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.account.NoLoanOnCourseException;
import ar.edu.unq.desapp.grupoa.exception.account.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.exception.account.UserDefaultException;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.Credit;

public class Loan {

    private static final Logger log = LoggerFactory.getLogger(Loan.class);

    private Loan(){}

    public static void takeLoan(Account account) {
       validateLoan(account);
       account.debt(1000);
    }

    public static void payQuota(Account account){
        if (validateQuota(account)){
            log.info("User:"+ account.getUser().getFirstName()+" "+account.getUser().getLastName() +" paid a quota of his debt");
            account.payDebt(200);
            if (!accountIsInDebt(account)){
                log.info("User:" + account.getUser().getFirstName()+" "+account.getUser().getLastName() + "paid all his quotas and is no longer in debt");
            }
            log.info("User:" + account.getUser().getFirstName()+" "+account.getUser().getLastName() + "'s account balance is now: "+ account.balance());
        }
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

    private static boolean validateQuota(Account account) {
        if (!accountIsInDebt(account))
            throw new NoLoanOnCourseException();
        if (account.balance() < 200){
            defaultUser(account);
            return false;
        }
        return true;
    }

    private static void defaultUser(Account account) {
        if (!account.getUser().hasDefaulted()){
            log.info("User: "+ account.getUser().getFirstName()+" "+account.getUser().getLastName() + " doesn't has enough cash to pay the quota and has defaulted");
            account.getUser().defaultUser();
        }
    }

    private static Integer quotasToPay(Integer debt) {
        return debt/200;
    }
}
