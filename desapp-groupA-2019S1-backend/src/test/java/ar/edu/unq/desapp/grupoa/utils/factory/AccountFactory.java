package ar.edu.unq.desapp.grupoa.utils.factory;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.user.User;

import static ar.edu.unq.desapp.grupoa.model.account.Account.newAccount;
import static ar.edu.unq.desapp.grupoa.service.LoanService.takeLoan;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;

public class AccountFactory {

    private AccountFactory(){}

    public static Account accountForUserWithRandomBalance() {
        Account account = newAccount(randomUser());
        return account.deposit(randomNumber());
    }

    public static Account accountWithDefaultedUser() {
        User user = randomUser();
        user.defaultUser();
        return newAccount(user);
    }

    public static Account accountWithNoBalance() {
        return newAccount(randomUser());
    }
    public static Account accountWithDebtAndNoBalance() {
        return takeLoan(accountWithNoBalance()).extract(1000);
    }

    public static Account loanedAccountWithRandomBalance(){
        return takeLoan(accountForUserWithRandomBalance());
    }

}
