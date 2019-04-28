package ar.edu.unq.desapp.grupoa.model.user.account;

import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Account {

    private List<Movement> movements;

    public Account(User user) {
        this.movements = new ArrayList<>();
    }

    public Integer balance() {
        return movements.stream().flatMapToInt(
                movement -> IntStream.of(movement.amount()))
                .sum();
    }


    public void addMovement(Movement movement) {
        movements.add(movement);
    }

    public List<Movement> movements() {
        return new ArrayList<>(movements);
    }
}
