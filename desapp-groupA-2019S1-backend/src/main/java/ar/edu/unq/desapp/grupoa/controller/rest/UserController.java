package ar.edu.unq.desapp.grupoa.controller.rest;

import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        Integer userId = userService.create(user);
        return new ResponseEntity<>(userId.toString(), HttpStatus.CREATED);
    }



    @GetMapping(value= "/{userId}")
    public ResponseEntity<User> findUser(@PathVariable Integer userId){
        User user = userService.getById(userId);
        return new ResponseEntity<>( user, HttpStatus.OK) ;
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>("User updated succesfuly",HttpStatus.OK );
    }

}
