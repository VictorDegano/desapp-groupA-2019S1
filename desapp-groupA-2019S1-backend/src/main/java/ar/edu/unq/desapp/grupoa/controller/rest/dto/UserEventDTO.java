package ar.edu.unq.desapp.grupoa.controller.rest.dto;

// TODO: 2/6/2019 hacer test
public class UserEventDTO {

    public Integer id;
    public String firstName;
    public String lastName;
    public String email;


    public UserEventDTO(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
