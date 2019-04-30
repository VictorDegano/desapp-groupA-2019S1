package ar.edu.unq.desapp.grupoa.utils.builder;


import ar.edu.unq.desapp.grupoa.model.user.User;

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

    public User build(){
        return this.user;
    }
}
