package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class GuestBuilder {

    private Guest guest;

    private GuestBuilder(){
        this.guest  = new Guest();
    }

    public static GuestBuilder buildAGuest(){
        return new GuestBuilder();
    }

    public GuestBuilder withUser(User user){
        this.guest.setUser(user);
        return this;
    }

    public Guest build(){
        return this.guest;
    }

    public GuestBuilder withConfirmation(InvitationState invitation) {
        this.guest.setConfirmAsistance(invitation);
        return this;
    }


}
