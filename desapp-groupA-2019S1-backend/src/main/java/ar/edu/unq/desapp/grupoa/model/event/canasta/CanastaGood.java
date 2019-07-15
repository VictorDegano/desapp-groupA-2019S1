package ar.edu.unq.desapp.grupoa.model.event.canasta;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CanastaGood extends Good {

    @ManyToOne(cascade = CascadeType.ALL)
    private User userThatOwnsTheGood;

    public CanastaGood(String name, Integer price, Integer quantity) {
        this.setName(name);
        this.setPricePerUnit(price);
        this.setQuantityForPerson(quantity);
    }

    public CanastaGood() {  }

    public User getUserThatOwnsTheGood() {
        return userThatOwnsTheGood;
    }

    public void setUserThatOwnsTheGood(User userThatOwnsTheGood) {
        this.userThatOwnsTheGood = userThatOwnsTheGood;
    }

    @Override
    public Integer totalCost() {
        return this.getPricePerUnit() * this.getQuantityForPerson();
    }


    @JsonIgnore
    public Boolean isAvailable() {
        return this.userThatOwnsTheGood == null;
    }

    public Boolean hasOwner() {
        return this.getUserThatOwnsTheGood() != null;
    }
}
