package ar.edu.unq.desapp.grupoa.model;

public class Guest {

    // TODO: 23/4/2019 A futuro se va a reemplazar por el usuario.
    private String name;
    private Boolean confirmAsistance;

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Guest(String name) {
        this.name = name;
        this.confirmAsistance = false;
    }

    public Guest() {    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public Boolean getConfirmAsistance() {  return confirmAsistance;    }
    public void setConfirmAsistance(Boolean confirmAsistance) { this.confirmAsistance = confirmAsistance;   }
}
