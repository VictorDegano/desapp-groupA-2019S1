package ar.edu.unq.desapp.grupoa.controller.rest.dto;

public class LoginOutDTO {

    public String accessToken;
    public String userId;


    public LoginOutDTO(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

}
