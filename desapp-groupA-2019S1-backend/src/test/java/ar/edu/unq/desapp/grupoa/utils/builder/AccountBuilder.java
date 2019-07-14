package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.user.User;

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

    @SafeVarargs
    public static Account newAccountForUser(User user, Function<Account, Account>... functions) {
        return compose(functions).apply(newAccount(user));
    }

    public static Function<Account, Account> withRandomBalance() {
        return (account) -> {
            account.deposit(randomNumber());
            return account;
        };
    }

    public static Function<Account, Account> withBalance(Integer amount) {
        return (account) -> {
            account.deposit(amount);
            return account;
        };
    }

    public static Function<Account, Account> withDefaultedUser() {
        return (account) -> {
            account.getUser().defaultUser();
            return account;
        };
    }

    public static Function<Account, Account> withLoanAndNoBalance() {
        return (account) -> {
            account.extract(1000);
            takeLoan(account);
            return account;
        };
    }

    public static Function<Account, Account> withLoan() {
        return (account) -> {
            takeLoan(account);
            return account;
        };
    }
}
