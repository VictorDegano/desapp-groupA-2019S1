package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.model.account.movement.MovementType;
import ar.edu.unq.desapp.grupoa.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    @OneToOne
    private  User user;

    @OneToMany(cascade = CascadeType.ALL)
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

    public Account extract(Integer amount) {
        return newMovement(Movement.extraction(amount));
    }

    public Account deposit(Integer amount) {
        return newMovement(Movement.deposit(amount));
    }

    public Account debt(Integer amount) {

        return newMovement(Movement.debt(amount));
    }

    public Account payDebt(Integer amount) {
        return newMovement(Movement.paydebt(amount));
    }


    private Account newMovement(Movement movement) {
        return new Account(this.user,addMovement(movement));
    }

    private List<Movement> addMovement(Movement movement) {
        List<Movement> movements = new ArrayList<>(this.movements);
        movements.add(movement);
        return movements;
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
