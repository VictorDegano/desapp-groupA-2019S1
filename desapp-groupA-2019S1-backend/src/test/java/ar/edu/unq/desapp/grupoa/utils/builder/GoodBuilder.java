package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.Good;

public class GoodBuilder {

    private Good aGood;

    private GoodBuilder(){
        this.aGood = new Good();
    }

    public static GoodBuilder buildAGood(){
        return new GoodBuilder();
    }

    public GoodBuilder withName(String name){
        this.aGood.setName(name);
        return this;
    }

    public GoodBuilder withPricesPerUnit(Integer price){
        this.aGood.setPricePerUnit(price);
        return this;
    }

    public GoodBuilder withFinalQuantity(Integer finalQuantity) {
        this.aGood.setFinalQuantity(finalQuantity);
        return this;
    }

    public GoodBuilder withQuantityForPerson(Integer quantity){
        this.aGood.setQuantityForPerson(quantity);
        return this;
    }

    public Good build(){
        return this.aGood;
    }


}
