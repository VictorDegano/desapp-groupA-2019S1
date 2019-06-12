package ar.edu.unq.desapp.grupoa.controller.rest.dto;

public class LoginDTO {

    public String firstName;
    public String familyName;
    public String email;
    public String bornDate;

    public LoginDTO(String firstName, String familyName, String email, String bornDate) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.bornDate = bornDate;
    }
}
