package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.accountIsInDebt;
import static ar.edu.unq.desapp.grupoa.model.account.behaviour.Loan.payQuota;

@Component
@Service
public class QuotaPayScheduler {


    @Autowired
    private UserDAO userDAO;

    private static final Logger log = LoggerFactory.getLogger(QuotaPayScheduler.class);

    //    @Scheduled(cron = "0 0 5 * *")
    @Scheduled(fixedRate = 30000)
    public void payDebtForAllUsers() {
        List<User> users = userDAO.findAll();
        List<User> userWithDebt = getUsersWithDebt(users);
        makeUsersPayQuota(userWithDebt);
    }

    private void makeUsersPayQuota(List<User> userWithDebt) {
        userWithDebt.forEach(user ->{
            payQuota(user.getAccount());
            userDAO.save(user);
        });

    }

    private List<User> getUsersWithDebt(List<User> users) {
        return users.stream().filter(user -> accountIsInDebt(user.getAccount())).collect(Collectors.toList());
    }
}
