package ar.edu.unq.desapp.grupoa.model;

public class Guest {

    private User user;
    private InvitationState confirmAsistance;

    public void confirmAsistance(){
        this.setConfirmAsistance(InvitationState.ACCEPTED);
    }

    public String name() {
        // TODO: 26/4/2019 La responsabilidad de dar su nombre completo tiene que ser del usuario
        return this.getUser().getFirstName() + ' ' + this.getUser().getLastName();
    }

    public boolean areThatGuest (Guest guestToAssist){
        return this == guestToAssist;
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

}
