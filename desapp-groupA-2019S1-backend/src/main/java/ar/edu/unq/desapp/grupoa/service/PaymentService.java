package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.NotEnoughCashToPerformOperation;
import ar.edu.unq.desapp.grupoa.model.account.Account;

public class PaymentService {

    private PaymentService(){ }

    public static void extract(Account account, Integer amount){
        if (account.balance()-amount <= 0)
                throw new NotEnoughCashToPerformOperation("Not enough cash to perform operation");
        account.extract(amount);
    }

    public static void deposit(Account account, Integer amount){
        account.deposit(amount);
    }


}
