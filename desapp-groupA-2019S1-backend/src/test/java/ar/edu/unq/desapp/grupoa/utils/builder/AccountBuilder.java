package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import java.util.Arrays;
import java.util.function.Function;

import static ar.edu.unq.desapp.grupoa.model.account.Account.newAccount;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.takeLoan;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomNumber;
import static ar.edu.unq.desapp.grupoa.utils.builder.Randomizer.randomUser;

public class AccountBuilder {

    //Composes all the functions given
    @SafeVarargs
    private static <T> Function<T, T> combineF(Function<T, T>... funcs) {
        return Arrays.stream(funcs).reduce(Function.identity(), Function::andThen);
    }

    @SafeVarargs
    public static Account accountForRandomUser(Function<Account, Account>... functions) {
        return combineF(functions).apply(newAccount(randomUser()));
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
