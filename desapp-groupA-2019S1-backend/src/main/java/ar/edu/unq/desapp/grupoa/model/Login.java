package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column( length = 1500)
    private String accessToken;
    private LocalDateTime expireAt;
    private LocalDateTime logIn;
    private LocalDateTime logOut;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Login() { }

    public Login(String accessToken, LocalDateTime expireAt, User loggedUser) {
        this.accessToken = accessToken;
        this.expireAt = expireAt;
        this.logIn = LocalDateTime.now();
        this.logOut = null;
        this.user = loggedUser;
    }

    public void logOut(){
        if(Objects.nonNull(this.logOut)){
            this.logOut = LocalDateTime.now();
        }
    }

    public Boolean isValidToken(String accessTokenToCheck){
        return this.accessToken.equals(accessTokenToCheck) && this.expireAt.isAfter(LocalDateTime.now());
    }

    public String getAccessToken() {    return accessToken; }

    public LocalDateTime getExpireAt() {    return expireAt;    }

    public User getUser() { return user;    }

    public Integer getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
