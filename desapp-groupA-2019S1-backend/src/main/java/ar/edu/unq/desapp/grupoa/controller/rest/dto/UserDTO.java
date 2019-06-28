package ar.edu.unq.desapp.grupoa.controller.rest.dto;

// TODO: 10/6/2019 DO TESTS 
public class UserDTO {

    public Integer id;
    public String fistName;
    public String lastName;
    public String bornDate;
    public String email;

    public UserDTO() {}

    public UserDTO(Integer id, String fistName, String lastName, String bornDate, String email) {
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.bornDate = bornDate;
        this.email = email;
    }
}
