package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.exception.event.GoodTypeException;
import ar.edu.unq.desapp.grupoa.model.user.User;

public abstract class Good {

    private Integer id;
    private String name;
    private Integer pricePerUnit;
    private Integer quantityForPerson;

    public Integer totalCost() {
        return this.pricePerUnit * this.quantityForPerson;
    }

    public User getUserThatOwnsTheGood(){
        throw new GoodTypeException("This type of good don't have owner");
    }

    public void multiplyFinalQuantityBy(Integer quantityToMultiply){    }

    public Integer finalQuantity() { return this.quantityForPerson;   }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/

    public String getName() {   return this.name;    }
    public void setName(String name) {  this.name = name;   }

    public void setPricePerUnit(Integer pricePerUnit) { this.pricePerUnit = pricePerUnit;   }

    public void setQuantityForPerson(Integer quantityForPerson) {   this.quantityForPerson = quantityForPerson; }
    public Integer getQuantityForPerson() { return this.quantityForPerson;   }

    protected Integer getPricePerUnit() {
        return this.pricePerUnit;
    }
}