package ar.edu.unq.desapp.grupoa.model.event.fiesta;

import ar.edu.unq.desapp.grupoa.model.event.Good;

import javax.persistence.Entity;

@Entity
public class FiestaGood extends Good {

    private Integer finalQuantity;

    @Override
    public Integer totalCost() {
        return super.totalCost() * this.finalQuantity;
    }

    public void multiplyFinalQuantityBy(Integer quantityToMultiply) {
        this.finalQuantity = this.getQuantityForPerson() * quantityToMultiply;
    }

    @Override
    public Integer getFinalQuantity() { return this.finalQuantity;   }

    public Boolean hasOwner() {
        return false;
    }

    public FiestaGood() {   }

    public void setFinalQuantity(Integer finalQuantity) {   this.finalQuantity = finalQuantity; }
}
