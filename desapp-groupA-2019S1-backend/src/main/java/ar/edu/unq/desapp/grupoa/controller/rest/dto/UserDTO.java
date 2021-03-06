package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(),user.getFirstName(),user.getLastName(),user.getBornDay().toString(),user.getEmail());
    }
}
