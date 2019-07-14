package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.model.account.movement.MovementType;
import ar.edu.unq.desapp.grupoa.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private  User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Movement> movements;

    public static Account newAccount(User user) {
        return new Account(user, new ArrayList<>());
    }

    private Account(User user, List<Movement> movements) {
        this.user = user;
        this.movements = movements;
    }


    private Account() {
    }

    public void extract(Integer amount) {
         addMovement(Movement.extraction(amount));
    }

    public void deposit(Integer amount) {
        addMovement(Movement.deposit(amount));
    }

    public void debt(Integer amount) {

        addMovement(Movement.debt(amount));
    }

    public void payDebt(Integer amount) {
        addMovement(Movement.paydebt(amount));
    }


    private void addMovement(Movement movement) {
        movements.add(movement);
    }


    public Integer balance() {
        return sumMovements(movements);
    }

    public Integer debt() {
        return sumMovements(debtMovements());
    }

    public User getUser() {
        return this.user;
    }

    private static Integer sumMovements(List<Movement> movements) {
        return movements.stream().mapToInt(Movement::value).sum();

    }

    private List<Movement> debtMovements() {
        return movements.stream()
                .filter(movement -> movement.isMovementType(MovementType.DEBT))
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public List<Movement> getMovements() {
        return movements;
    }

}
