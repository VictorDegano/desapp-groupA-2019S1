package ar.edu.unq.desapp.grupoa.utils.builder;

import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;

public class CanastaGoodBuilder {

    private CanastaGood aCanastaGood;

    private CanastaGoodBuilder(){
        this.aCanastaGood = new CanastaGood();
    }

    public static CanastaGoodBuilder buildAGood(){
        return new CanastaGoodBuilder();
    }

    public CanastaGoodBuilder withName(String name){
        this.aCanastaGood.setName(name);
        return this;
    }

    public CanastaGoodBuilder withPricesPerUnit(Integer price){
        this.aCanastaGood.setPricePerUnit(price);
        return this;
    }

    public CanastaGoodBuilder withQuantityForPerson(Integer quantity){
        this.aCanastaGood.setQuantityForPerson(quantity);
        return this;
    }

    public CanastaGood build(){
        return this.aCanastaGood;
    }
}
