package ar.edu.unq.desapp.grupoa.model.account.movement;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public  class Outflow extends Movement{

    public Outflow(Integer amount, MovementType type, LocalDateTime date) {
        super(amount,type,date);
    }

    private Outflow() {
    }


    @Override
    public Integer value() {
        return -this.amount;
    }

}
