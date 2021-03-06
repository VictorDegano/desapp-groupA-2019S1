package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.*;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Enumerated(EnumType.STRING)
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

    public Boolean isCanceled() {
        return this.getConfirmAsistance().equals(InvitationState.CANCELLED);
    }

    public Boolean isconfirmed() {
        return this.getConfirmAsistance().equals(InvitationState.ACCEPTED);
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

    public Integer getId() {
        return id;
    }
    public void setConfirmAsistance(InvitationState confirmAsistance) { this.confirmAsistance = confirmAsistance;   }

    public User getUser() { return user;    }
    public void setUser(User user) {    this.user = user;   }

    public void ownAGood(CanastaGood good) {
        good.setUserThatOwnsTheGood(this.user);
    }


    public Boolean isInvitationAccepted() {
        return this.getConfirmAsistance() == InvitationState.ACCEPTED;
    }

}
