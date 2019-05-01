package ar.edu.unq.desapp.grupoa.model.account.movement;

import java.time.LocalDateTime;

public abstract class Movement{

    protected final Integer amount;
    private final LocalDateTime date;
    private final MovementType type;


    public static Movement deposit(Integer amount){
        return new Inflow(amount,MovementType.CASH,LocalDateTime.now());
    }

    public static Movement extraction(Integer amount){
        return new Outflow(amount,MovementType.CASH,LocalDateTime.now());
    }

    public static Movement debt(Integer amount){
        return new Inflow(amount,MovementType.DEBT,LocalDateTime.now());
    }

    public static Movement paydebt(Integer amount){
        return new Outflow(amount,MovementType.DEBT,LocalDateTime.now());
    }

    protected Movement(Integer amount, MovementType type, LocalDateTime date) {
        this.amount= amount;
        this.date  = date;
        this.type  = type;
    }

    public Integer amount(){
        return amount;
    }

    public abstract Integer value();

    public Boolean isMovementType(MovementType type) {
        return this.type.equals(type);
    }


}
