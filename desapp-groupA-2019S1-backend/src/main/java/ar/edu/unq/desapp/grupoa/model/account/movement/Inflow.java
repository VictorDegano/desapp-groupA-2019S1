package ar.edu.unq.desapp.grupoa.model.account.movement;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Inflow extends Movement {

    public Inflow(Integer amount, MovementType type, LocalDateTime date) {
        super(amount,type,date);
    }

    @Override
    public Integer value() {
        return this.amount;
    }

}
