package ar.edu.unq.desapp.grupoa.model.user.account;

import java.time.LocalDateTime;

public class Movement {

    private final Integer amount;
    private final MovementType type;
    private final LocalDateTime date;

    public Movement(Integer amount, MovementType type, LocalDateTime date) {
        this.amount= amount;
        this.type  = type;
        this.date  = date;
    }

    public Integer amount() {
        return amount;
    }
}
