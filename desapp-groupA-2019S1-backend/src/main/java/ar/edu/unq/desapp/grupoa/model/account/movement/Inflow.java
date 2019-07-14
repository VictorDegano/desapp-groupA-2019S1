package ar.edu.unq.desapp.grupoa.model.account.movement;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Inflow extends Movement {

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String flowType = "inflow";

    public Inflow(Integer amount, MovementType type, LocalDateTime date) {
        super(amount,type,date);
    }

    public Inflow() {
    }

    @Override
    public Integer value() {
        return this.amount;
    }

}
