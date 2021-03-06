package ar.edu.unq.desapp.grupoa.model.account.behaviour;

import ar.edu.unq.desapp.grupoa.exception.account.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.model.account.Account;

public class Payment {

    private Payment(){ }

    public static void extract(Account account, Integer amount){
        if (account.balance()-amount < 0)
                throw new NotEnoughCashToPerformOperation("Not enough cash to perform operation");
         account.extract(amount);
    }

    public static void deposit(Account account, Integer amount){
        account.deposit(amount);
    }


}
