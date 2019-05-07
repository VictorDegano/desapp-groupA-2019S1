package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class Guest {

    private User user;
    private InvitationState confirmAsistance;

    public void confirmAsistance(){
        this.setConfirmAsistance(InvitationState.ACCEPTED);
    }

    public boolean areThatGuest (Guest guestToAssist){
        return this == guestToAssist;
    }

    public boolean isTheUser(User userToInvite) {
        return userToInvite == this.user;
    }

    public boolean isInvitationPending() {
        return this.getConfirmAsistance().equals(InvitationState.PENDING);
    }

    public void cancelInvitation() {
        this.setConfirmAsistance(InvitationState.CANCELLED);
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Guest(User user) {
        this.user = user;
        this.confirmAsistance = InvitationState.PENDING;
    }

    public Guest() {    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/

    public InvitationState getConfirmAsistance() {  return this.confirmAsistance;    }
    public void setConfirmAsistance(InvitationState confirmAsistance) { this.confirmAsistance = confirmAsistance;   }

    public User getUser() { return user;    }
    public void setUser(User user) {    this.user = user;   }

    public void ownAGood(CanastaGood good) {
        good.setUserThatOwnsTheGood(this.user);
    }
}
