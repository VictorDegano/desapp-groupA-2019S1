package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.DTOConverter;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;


    public Integer create(User user) {
        userDAO.save(user);
        return user.getId();
    }

    public User getById(Integer userId) {
       return userDAO.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
    }

    public void update(User user) {
        userDAO.save(user);
    }

    // TODO: 10/6/2019 DO TEST, revisar que el parseo a fecha se haga correctamente
    public UserDTO findOrCreate(String firstName, String familyName, String email, String bornDate) {
        User findedUser = this.userDAO.findByFirstNameAndLastNameAndEmail(firstName, familyName, email);

        if(Objects.isNull(findedUser)){
            User newUser = new User(firstName, familyName,email, firstName+familyName, LocalDateTime.parse(bornDate));
            this.userDAO.save(newUser);
            findedUser = newUser;
        }

        UserDTO loggedUser = DTOConverter.createUserDTO(findedUser);

        return loggedUser;
    }
}
