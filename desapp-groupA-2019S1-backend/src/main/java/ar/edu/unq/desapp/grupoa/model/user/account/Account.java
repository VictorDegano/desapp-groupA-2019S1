package ar.edu.unq.desapp.grupoa.model.user.account;

import ar.edu.unq.desapp.grupoa.exception.LoanOnCourseException;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Account {

    private List<Movement> movements;
    private Optional<Loan> loan;

    public Account(User user) {
        this.movements = new ArrayList<>();
        this.loan      = Optional.empty ();
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

    public void takeLoan() {
        if (this.loan.isPresent())
            throw new LoanOnCourseException();
        else{
            this.loan = Optional.of(new Loan());
        }
    }

    public Optional<Loan> loan() {
        return this.loan;
    }
}
