package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
