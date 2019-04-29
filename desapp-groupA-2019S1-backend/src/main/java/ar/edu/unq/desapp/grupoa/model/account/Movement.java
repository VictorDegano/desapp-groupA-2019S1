package ar.edu.unq.desapp.grupoa.model.account;

import java.time.LocalDateTime;

public class Movement{

    private final Integer amount;
    private final LocalDateTime date;
    private final MovementType type;

    public Movement(Integer amount, MovementType type, LocalDateTime date) {
        this.amount= amount;
        this.date  = date;
        this.type  = type;
    }

    public Integer amount(){
        return amount;
    }

    public MovementType getType() {
        return type;
    }
}
