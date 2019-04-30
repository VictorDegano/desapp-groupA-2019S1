package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.user.User;

public class CanastaGood extends Good{
    private User userThatOwnsTheGood;

    public CanastaGood(String name, Integer price, Integer quantity) {
        super(name, price, quantity);
    }

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
}
