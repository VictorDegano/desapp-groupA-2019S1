package ar.edu.unq.desapp.grupoa.model.account;

import ar.edu.unq.desapp.grupoa.model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Account {

    private final User user;
    private List<Movement> movements;

    public Account(User user) {
        this.user = user;
        this.movements = new ArrayList<>();
    }

    public Integer extract(Integer amount) {
        movements.add(new Movement(-amount,MovementType.CASH,LocalDateTime.now()));
        return amount;
    }

    public void deposit(Integer amount, MovementType type) {
        movements.add(new Movement(amount,type,LocalDateTime.now()));
    }

    public void addDebt(Integer amount) {
        movements.add(new Movement(amount,MovementType.DEBT,LocalDateTime.now()));
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

    private static Integer sumMovements(List<Movement> operations) {
        return operations.stream().flatMapToInt(
                movement -> IntStream.of(movement.amount()))
                .sum();
    }

    private List<Movement> debtMovements() {
        return movements.stream().filter(movement -> movement.getType().equals(MovementType.DEBT)).collect(Collectors.toList());
    }

}
