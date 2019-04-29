package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.MovementType;

public class PaymentService {

    private PaymentService(){ }

    public static Integer extractCash(Account account, Integer amount){
        if (account.balance()-amount <= 0)
                throw new NotEnoughCashToPerformOperation("Not enough cash to perform operation");
       return account.extract(amount);
    }

    public static void depositCash(Account account, Integer amount){
        account.deposit(amount, MovementType.CASH);
    }

    public static void depositCredit(Account account, Integer amount){
        account.deposit(amount, MovementType.CREDIT);
    }
}
