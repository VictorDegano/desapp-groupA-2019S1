package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.DTOConverter;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.LoginDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.LoginOutDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.service.LoginService;
import ar.edu.unq.desapp.grupoa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO: 10/6/2019 DO TESTS
@CrossOrigin
@Transactional
@Controller
//@RequestMapping("/login")
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping(value="/google/login/")
    public ResponseEntity<UserDTO> googleLogIn(@RequestBody LoginDTO userToLogIn){
        LOGGER.info("Got request for Google Log In of {}", userToLogIn);

        User findedUser = this.userService.findOrCreate(userToLogIn.firstName, userToLogIn.familyName, userToLogIn.email);

        this.loginService.logIn(findedUser, userToLogIn.accessToken, userToLogIn.expireAt);

        UserDTO loggedUser = DTOConverter.createUserDTO(findedUser);

        LOGGER.info("Response the request with the user {}", loggedUser);

        return new ResponseEntity<>(loggedUser, HttpStatus.OK);
    }

    @PostMapping(value="/google/Logout/")
    public ResponseEntity<Boolean> googleLogOut(@RequestBody LoginOutDTO userToLogOut){
        LOGGER.info("Got request for Google Log Out of user with Id {}", userToLogOut.userId);

        this.loginService.logOut(userToLogOut);

        LOGGER.info("User succesfully log out");

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
