package ar.edu.unq.desapp.grupoa.utils.builder;


import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;

public class UserBuilder {

    private User user;

    private UserBuilder(){
        this.user  = new User();
    }

    public static UserBuilder buildAUser(){
        return new UserBuilder();
    }

    public UserBuilder withFirstName(String firstName){
        this.user.setFirstName(firstName);
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.user.setLastName(lastName);
        return this;
    }

    public UserBuilder withBornDay (LocalDateTime bornDay){
        this.user.setBornDay(bornDay);
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder withMail(String email) {
        this.user.setEmail(email);
        return this;
    }

    public User build(){
        return this.user;
    }

}
