package ar.edu.unq.desapp.grupoa.model.account.movement;

import java.time.LocalDateTime;

public final class Outflow extends Movement{

    public Outflow(Integer amount, MovementType type, LocalDateTime date) {
        super(amount,type,date);
    }

    @Override
    public Integer value() {
        return -this.amount;
    }

}
