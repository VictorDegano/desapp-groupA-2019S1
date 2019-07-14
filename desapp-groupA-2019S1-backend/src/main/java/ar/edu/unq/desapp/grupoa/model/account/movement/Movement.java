package ar.edu.unq.desapp.grupoa.model.account.movement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "flowType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Inflow.class, name = "inflow"),
        @JsonSubTypes.Type(value = Outflow.class, name = "outflow")
})
public abstract class Movement{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    protected  Integer amount;

    private  LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private  MovementType type;

    protected Movement() {
    }

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

    public abstract Integer value();

    public Boolean isMovementType(MovementType type) {
        return this.type.equals(type);
    }


    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public MovementType getType() {
        return type;
    }

}
