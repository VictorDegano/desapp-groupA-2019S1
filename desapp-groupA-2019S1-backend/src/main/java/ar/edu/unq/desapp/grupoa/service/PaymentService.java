package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.account.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.model.account.Account;

public class PaymentService {

    private PaymentService(){ }

    public static Account extract(Account account, Integer amount){
        if (account.balance()-amount <= 0)
                throw new NotEnoughCashToPerformOperation("Not enough cash to perform operation");
        return account.extract(amount);
    }

    public static Account deposit(Account account, Integer amount){
       return account.deposit(amount);
    }


}
