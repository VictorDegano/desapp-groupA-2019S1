package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.Login;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void update(UserDTO user) {
        User userToUpdate = this.userDAO.findById(user.id).orElseThrow(()->new UserNotFoundException(user.id));
        userToUpdate.setFirstName(user.fistName);
        userToUpdate.setLastName(user.lastName);
        userToUpdate.setBornDay(LocalDateTime.parse("1998-06-22T03:00:00.000Z", DateTimeFormatter.ISO_DATE_TIME));

        userDAO.save(userToUpdate);
    }

    // TODO: 10/6/2019 DO TEST, revisar que el parseo a fecha se haga correctamente
    public User findOrCreate(String firstName, String familyName, String email) {
        User findedUser = this.userDAO.findByEmail(email);

        if(Objects.isNull(findedUser)){
            User newUser = new User(
                                firstName,
                                familyName,
                                email,
                                firstName+familyName,
                                LocalDateTime.of(1900,1,1,0,0)
                            );
            this.userDAO.save(newUser);
            findedUser = newUser;
        }

        return findedUser;
    }
}
