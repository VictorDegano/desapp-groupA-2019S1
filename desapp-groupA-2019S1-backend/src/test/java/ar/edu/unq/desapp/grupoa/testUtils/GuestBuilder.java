package ar.edu.unq.desapp.grupoa.testUtils;

import ar.edu.unq.desapp.grupoa.model.Guest;

public class GuestBuilder {

    private Guest guest;

    private GuestBuilder(){
        this.guest  = new Guest();
    }

    public static GuestBuilder buildAGuest(){
        return new GuestBuilder();
    }

    public GuestBuilder withName(String name){
        this.guest.setName(name);
        return this;
    }

    public Guest build(){
        return this.guest;
    }
}
