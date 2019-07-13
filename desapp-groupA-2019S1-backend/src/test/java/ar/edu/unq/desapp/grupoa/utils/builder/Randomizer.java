package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

import static ar.edu.unq.desapp.grupoa.utils.ComposeFunctions.compose;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.newAccountForUser;
import static ar.edu.unq.desapp.grupoa.utils.builder.AccountBuilder.withBalance;

public class Randomizer {

    private Randomizer() {
    }

    public static User randomUser(Function<User, User>... functions) {
        return compose(functions).apply(new User("random", "random", randomString()+"@gmail.com", "random", randomDate()));
    }

    public static Function<User, User> withAccountBalance(Integer amount) {
         return (user) -> {
             user.updateAccount(newAccountForUser(user, withBalance(amount)));
             return user;
         };
    }


    public static User randomUserWithName(String name) {
        return new User(name, "random", randomString()+"@random.com", "random", randomDate());
    }

    public static Integer randomNumber(){
        return new Double(Math.random()).intValue();
    }

    public static String randomString() {
        return RandomStringUtils.random(50);
    }

    public static LocalDateTime randomDate() {
        long beginTime = Date.valueOf("2000-01-01").getTime();
        long endTime = Date.valueOf("3000-12-31").getTime();
        long diff = endTime - beginTime + 1;
        long randomtime = beginTime + (long) (Math.random() * diff);
        LocalDateTime time = LocalDateTime.ofEpochSecond(randomtime,0, ZoneOffset.UTC);
        return  time;
    }



}
