package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.account.Account;

import java.util.function.Function;

import static ar.edu.unq.desapp.grupoa.model.account.Account.newAccount;

import static ar.edu.unq.desapp.grupoa.utils.ComposeFunctions.compose;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.takeLoan;

import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;

public class AccountBuilder {


    @SafeVarargs
    public static Account newAccountForRandomUser(Function<Account, Account>... functions) {
        return compose(functions).apply(newAccount(randomUser()));
    }

    public static Function<Account, Account> withRandomBalance() {
        return (account) -> account.deposit(randomNumber());
    }

    public static Function<Account, Account> withDefaultedUser() {
       return (account) -> {
           account.getUser().defaultUser();
           return account;
       };
    }

    public static Function<Account, Account> withLoanAndNoBalance() {
        return (account) -> takeLoan(account.extract(1000));
    }

    public static Function<Account, Account> withLoan() {
        return (account) -> takeLoan(account);
    }
}
