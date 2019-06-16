package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.LoginException;
import ar.edu.unq.desapp.grupoa.model.Login;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginDAO loginDAO;

    public void logIn(User userToLogin, String accessToken, String expireAt){
        List<Login> opensUserLogins = this.loginDAO.getLoginByUserAndLogOutIsNullOrderByLogInDesc(userToLogin);

        if(! opensUserLogins.isEmpty()){
            // reviso que solo haya un login
            // si hay mas de uno me quedo con el primero y los demas los mando a "cerrar"
        } else {
            this.createLogin(userToLogin, accessToken, expireAt);
        }
    }

    private void createLogin(User userToLogin, String accessToken, String expireAt) {
        if(! accessToken.isEmpty() && ! expireAt.isEmpty()){
            Login newLogin = new Login(accessToken, LocalDateTime.parse(expireAt), userToLogin);
            this.loginDAO.save(newLogin);
        } else {
            throw new LoginException("There Was An Error Trying To Log In, Try Again");
        }

    }
}
