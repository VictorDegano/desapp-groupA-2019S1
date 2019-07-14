package ar.edu.unq.desapp.grupoa.model.user;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    @Size(
            min = 1,
            max = 30,
            message = "firstName should have between 1 and 30 characters."
    )
    private String firstName;
    @NotBlank(message = "LastName is mandatory")
    @Size(
            min = 1,
            max = 30,
            message = "lastName should have between 1 and 30 characters."
    )
    private String lastName;
    @NotBlank(message = "Mail is mandatory")
    @Column(unique = true)
    @Email
    private String email;
    @NotBlank(message = "Password is mandatory")
    @Size(
            min = 4,
            max = 10,
            message = "Password should be between 4 and 10 characters."
    )
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    private String password;
    @NotNull(message = "BornDay is mandatory")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime bornDay;
    @Enumerated(EnumType.STRING)
    private UserState state;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;


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

    public void deposit(Integer amount) {
        this.account.deposit(amount);
    }

    public Integer balance() {
        return this.account.balance();
    }

    public void extract(Integer totalCost) {
        this.account.extract(totalCost);
    }

    public void updateAccount(Account account) {
        this.account = account;
    }

    public String fullName(){
        return this.getFirstName() + ' ' + this.getLastName();
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]---------------[CONSTRUCTORS]----------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
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

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {    this.email = email; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {  this.password = password; }

    public Integer getId() {
        return this.id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName= name;
    }

    public Account getAccount() {
        return this.account;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {  this.lastName = lastName;   }

    public LocalDateTime getBornDay() {
        return bornDay;
    }
    public void setBornDay(LocalDateTime bornDay) { this.bornDay = bornDay; }
}
