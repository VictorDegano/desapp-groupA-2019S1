package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.model.account.movement.MovementType;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Account {

    private final User user;
    private final List<Movement> movements;

    public Account(User user) {
        this.user = user;
        this.movements = new ArrayList<>();
    }

    public void extract(Integer amount) {
        movements.add(Movement.extraction(amount));
    }

    public void deposit(Integer amount) {
        movements.add(Movement.deposit(amount));
    }

    public void debt(Integer amount) {
        movements.add(Movement.debt(amount));
    }

    public void payDebt(Integer amount) {
        movements.add(Movement.paydebt(amount));
    }

    public Integer balance(){
        return sumMovements(movements);
    }

    public Integer debt() {
        return sumMovements(debtMovements());
    }

    public User getUser(){
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

}
