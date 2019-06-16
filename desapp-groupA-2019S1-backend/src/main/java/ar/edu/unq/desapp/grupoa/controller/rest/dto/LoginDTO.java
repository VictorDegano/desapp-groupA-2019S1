package ar.edu.unq.desapp.grupoa.controller.rest.dto;

public class LoginDTO {

    public String accessToken;
    public String expireAt;
    public String firstName;
    public String familyName;
    public String email;
//    public String bornDate; De momento no se recibe la fecha de naciemiento

    public LoginDTO(String firstName, String familyName, String email, String accessToken, String expires_at) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.expireAt = expires_at;
        this.accessToken = accessToken;
//        this.bornDate = bornDate;
    }
}
