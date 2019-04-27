package ar.edu.unq.desapp.grupoa.model;

public class Guest {

    private User user;
    private Boolean confirmAsistance;

    public void confirmAsistance(){
        this.setConfirmAsistance(true);
    }

    public String name() {
        // TODO: 26/4/2019 La responsabilidad de dar su nombre completo tiene que ser del usuario
        return this.getUser().getFirstName() + ' ' + this.getUser().getLastName();
    }

    public boolean areThatGuest (Guest guestToAssist){
        return this == guestToAssist;
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Guest(User user) {
        this.user = user;
        this.confirmAsistance = false;
    }

    public Guest() {    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/

    public Boolean getConfirmAsistance() {  return confirmAsistance;    }
    public void setConfirmAsistance(Boolean confirmAsistance) { this.confirmAsistance = confirmAsistance;   }

    public User getUser() { return user;    }
    public void setUser(User user) {    this.user = user;   }

}
