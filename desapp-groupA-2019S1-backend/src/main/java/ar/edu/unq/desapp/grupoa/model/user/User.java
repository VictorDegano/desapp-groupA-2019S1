package ar.edu.unq.desapp.grupoa.model.user;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String firstName;

    @NotBlank(message = "LastName is mandatory")
    private String lastName;

    @NotBlank(message = "Mail is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotNull(message = "BornDay is mandatory")
    @Column
    private LocalDateTime bornDay;


    //TODO: Fijarse si se puede resolver desde una funcion
    @Transient
    @JsonIgnore
    private UserState state;

    //TODO: Provisorio, a confirmar
    @Transient
    @JsonIgnore
    private Account account;


    public User(String firstName, String lastName, String email, String password, LocalDateTime bornDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bornDay = bornDay;
        this.state = UserState.NORMAL;
        this.account = Account.newAccount(this);
    }

    public User(){}

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public LocalDateTime getBornDay() {
        return bornDay;
    }

    public Integer getId() {
        return this.id;
    }

    public void setFirstName(String name) {
        this.firstName= name;
    }

    public void defaultUser() {
        this.state = UserState.DEFAULT;
    }

    public Boolean hasDefaulted() {
        return this.state.equals(UserState.DEFAULT);
    }

    public boolean hasBeenDutiful() {
        return this.state.equals(UserState.DUTIFUL);
    }

    public Boolean hasNormalState() {
        return this.state.equals(UserState.NORMAL);
    }

    public void setLastName(String lastName) {  this.lastName = lastName;   }

    public void deposit(Integer amount) {
        this.account = this.account.deposit(amount);
    }

    public Integer balance() {
        return this.account.balance();
    }

    public void extract(Integer totalCost) {
        this.account = this.account.extract(totalCost);
    }
}
