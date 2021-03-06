package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.account.Credit;
import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.getCredit;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.takeLoan;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.deposit;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Payment.extract;

@Service
public class AccountService {


    @Autowired
    private UserDAO userDAO;

    public void depositMoney(Integer userId, Integer amount) {
        User user = getUser(userId);
        deposit(user.getAccount(),amount);
        userDAO.save(user);
    }

    public Integer extractMoney(Integer userId, Integer amount) {
        User user = getUser(userId);
        extract(user.getAccount(),amount);
        userDAO.save(user);
        return amount;
    }

    private User getUser(Integer userId) {
        return userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }


    public void loan(Integer userId) {
        User user = getUser(userId);
        takeLoan(user.getAccount());
        userDAO.save(user);
    }

    public Credit getCreditsOnCourse(Integer userId) {
        User user = getUser(userId);
        return  getCredit(user.getAccount());
    }

    public List<Movement> getMovements(Integer userId) {
        User user = getUser(userId);
        return  user.getAccount().getMovements();
    }

    public Integer userBalance(Integer userId) {
        User user = getUser(userId);
        return user.getAccount().balance();
    }
}
