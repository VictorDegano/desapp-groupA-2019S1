package ar.edu.unq.desapp.grupoa.utils.factory;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.account.movement.MovementType;
import ar.edu.unq.desapp.grupoa.model.user.User;

import static ar.edu.unq.desapp.grupoa.service.LoanService.takeLoan;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;

public class AccountFactory {

    private AccountFactory(){}

    public static Account accountForUserWithRandomBalance() {
        Account account = new Account(randomUser());
        account.deposit(randomNumber());
        return account;
    }

    public static Account accountWithDefaultedUser() {
        User user = randomUser();
        user.defaultUser();
        return new Account(user);
    }

    public static Account accountWithNoBalance() {
        User user = randomUser();
        return new Account(user);
    }
    public static Account accountWithDebtAndNoBalance() {
        Account account = accountWithNoBalance();
        takeLoan(account);
        account.extract(1000);
        return account;
    }

}
