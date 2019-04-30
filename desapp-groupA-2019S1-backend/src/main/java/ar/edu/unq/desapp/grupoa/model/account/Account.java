package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.model.account.movement.Movement;
import ar.edu.unq.desapp.grupoa.model.account.movement.MovementType;
import ar.edu.unq.desapp.grupoa.model.user.User;
import com.google.inject.internal.util.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Account {

    private final User user;
    private final ImmutableList<Movement> movements;

    public static Account newAccount(User user) {
        return new Account(user, new ArrayList<>());
    }

    private Account(User user, List<Movement> movements) {
        this.user = user;
        this.movements = ImmutableList.copyOf(movements);
    }

    public Account extract(Integer amount) {
        return addMovement(Movement.extraction(amount));
    }

    public Account deposit(Integer amount) {
        return addMovement(Movement.deposit(amount));
    }

    public Account debt(Integer amount) {
        return addMovement(Movement.debt(amount));
    }

    public Account payDebt(Integer amount) {
        return addMovement(Movement.paydebt(amount));
    }


    private Account addMovement(Movement movement) {
        List<Movement> movements = new ArrayList<>(this.movements);
        movements.add(movement);
        return new Account(this.user, movements);
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

}
