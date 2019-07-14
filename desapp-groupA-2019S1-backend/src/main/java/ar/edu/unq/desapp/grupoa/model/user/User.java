package ar.edu.unq.desapp.grupoa.model.user;

import ar.edu.unq.desapp.grupoa.model.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull(message = "BornDay is mandatory")
    // FIXME: 28/5/2019 ¿esto tiene que ser obligatoriamente date time? por que no solo LocalDate, es la fecha de nacimiento
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
