package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.deposit;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.extract;

@Service
public class AccountService {


    @Autowired
    private UserDAO userDAO;

    public void depositMoney(Integer userId, Integer amount) {
        User user = getUser(userId);
        user.updateAccount( deposit(user.getAccount(),amount));
        userDAO.save(user);
    }

    public Integer extractMoney(Integer userId, Integer amount) {
        User user = getUser(userId);
        user.updateAccount( extract(user.getAccount(),amount));
        userDAO.save(user);
        return amount;
    }

    private User getUser(Integer userId) {
        return userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }


}
