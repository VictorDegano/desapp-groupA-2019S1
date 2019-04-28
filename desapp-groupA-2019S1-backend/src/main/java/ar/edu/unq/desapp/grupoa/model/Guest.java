package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.user.User;

public class Guest {

    private User user;
    private Boolean confirmAsistance;

    public void confirmAsistance(){
        this.setConfirmAsistance(true);
    }

    public String name() {
        return this.getUser().getFirstName() + this.getUser().getLastName();
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
