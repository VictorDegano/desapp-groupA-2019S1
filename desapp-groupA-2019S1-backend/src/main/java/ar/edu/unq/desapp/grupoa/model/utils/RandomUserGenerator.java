package ar.edu.unq.desapp.grupoa.model.utils;

import ar.edu.unq.desapp.grupoa.model.User;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Date;

public class RandomUserGenerator {

    private RandomUserGenerator() {
    }

    public static User randomUser() {
        return new User(randomString(), randomString(), randomString(), randomString(), getRandomTime());
    }

    public static User randomUserWithName(String name) {
        return new User(name, randomString(), randomString(), randomString(), getRandomTime());
    }

    private static String randomString() {
        return RandomStringUtils.random(50);
    }

    private static Date getRandomTime() {
        long beginTime = Date.valueOf("2000-01-01").getTime();
        long endTime = Date.valueOf("3000-12-31").getTime();
        long diff = endTime - beginTime + 1;
        long randomtime = beginTime + (long) (Math.random() * diff);

        return  new Date(randomtime);
    }

}
