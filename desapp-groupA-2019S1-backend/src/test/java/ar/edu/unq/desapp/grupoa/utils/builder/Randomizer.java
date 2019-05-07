package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.user.User;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Randomizer {

    private Randomizer() {
    }

    public static User randomUser() {
        return new User(randomString(), randomString(), randomString(), randomString(), randomDate());
    }

    public static User randomUserWithName(String name) {
        return new User(name, randomString(), randomString(), randomString(), randomDate());
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
