package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.fiesta.FiestaGood;

public class FiestaGoodBuilder {

    private FiestaGood aFiestaGood;

    private FiestaGoodBuilder(){
        this.aFiestaGood = new FiestaGood();
    }

    public static FiestaGoodBuilder buildAGood(){
        return new FiestaGoodBuilder();
    }

    public FiestaGoodBuilder withName(String name){
        this.aFiestaGood.setName(name);
        return this;
    }

    public FiestaGoodBuilder withPricesPerUnit(Integer price){
        this.aFiestaGood.setPricePerUnit(price);
        return this;
    }

    public FiestaGoodBuilder withFinalQuantity(Integer finalQuantity) {
        this.aFiestaGood.setFinalQuantity(finalQuantity);
        return this;
    }

    public FiestaGoodBuilder withQuantityForPerson(Integer quantity){
        this.aFiestaGood.setQuantityForPerson(quantity);
        return this;
    }

    public FiestaGood build(){
        return this.aFiestaGood;
    }
}
