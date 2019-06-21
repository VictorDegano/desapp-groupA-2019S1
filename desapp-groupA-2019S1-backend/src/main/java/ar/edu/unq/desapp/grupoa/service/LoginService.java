package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.LoginOutDTO;
import ar.edu.unq.desapp.grupoa.exception.LoginException;
import ar.edu.unq.desapp.grupoa.model.Login;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class LoginService {

    // TODO: 16/6/2019 Sacar la logica a una clase que maneje el login

    @Autowired
    private LoginDAO loginDAO;

    public void logIn(User userToLogin, String accessToken, String expireAt){
        List<Login> opensUserLogins = this.loginDAO.getLoginByUserAndLogOutIsNullOrderByLogInDesc(userToLogin);

        if(! opensUserLogins.isEmpty()){
            this.checkLogin(opensUserLogins, accessToken);
            this.loginDAO.saveAll(opensUserLogins);
        } else {
            this.createLogin(userToLogin, accessToken, expireAt);
        }
    }

    private void checkLogin(List<Login> opensUserLogins, String accessToken) {
        opensUserLogins.forEach(login -> this.checkIfIsValidLogin(login, accessToken));

    }

    private void checkIfIsValidLogin(Login login, String accessToken) {
        Boolean loginExpired = login.getExpireAt().isBefore(LocalDateTime.now());
        Boolean distinctToken = !login.getAccessToken().equals(accessToken);

        if(loginExpired || distinctToken){
            login.logOut();
        }
    }

    // todo: Habria que usar el DTO converter
    private void createLogin(User userToLogin, String accessToken, String expireAt) {
        if(! accessToken.isEmpty() && ! expireAt.isEmpty()){
            Instant instant = Instant.ofEpochMilli(Long.valueOf(expireAt));
            Login newLogin = new Login(accessToken, LocalDateTime.ofInstant(instant, ZoneOffset.UTC), userToLogin);
            this.loginDAO.save(newLogin);
        } else {
            throw new LoginException("There Was An Error Trying To Log In, Try Again");
        }

    }

    // TODO: excepcion de no encontrar el login?¿
    public void logOut(LoginOutDTO userToLogOut) {
        Login loginToLogout = this.loginDAO.getLoginByUserIdAndAccessToken(Integer.valueOf(userToLogOut.userId), userToLogOut.accessToken);

        loginToLogout.logOut();

        this.loginDAO.save(loginToLogout);
    }
}
