package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.FiestaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;


public class GoodDTO {


    private Integer id;
    private String  name;
    private Integer pricePerUnit;
    private Integer quantityForPerson;
    private Integer finalQuantity;
    private UserDTO userThatOwnsTheGood;

    public GoodDTO(){}

    public GoodDTO(String name, Integer pricePerUnit, Integer quantityForPerson, Integer finalQuantity) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantityForPerson = quantityForPerson;
        this.finalQuantity = finalQuantity;
    }

    public GoodDTO(String name, Integer pricePerUnit, Integer quantityForPerson) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantityForPerson = quantityForPerson;

    }

    public GoodDTO(String name, Integer pricePerUnit, Integer quantityForPerson, User userThatOwnsTheGood) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantityForPerson = quantityForPerson;
        this.userThatOwnsTheGood = UserDTO.from(userThatOwnsTheGood);
    }


    public static GoodDTO fromFiestaGood(Good good) {
        FiestaGood fiestaGood = (FiestaGood) good;
        return  new GoodDTO(
                fiestaGood.getName(),
                fiestaGood.getPricePerUnit(),
                fiestaGood.getQuantityForPerson(),
                fiestaGood.getFinalQuantity());
    }

    public static GoodDTO fromCanastaGood(Good good) {
        CanastaGood canastaGood = (CanastaGood) good;
        return  new GoodDTO(
                canastaGood.getName(),
                canastaGood.getPricePerUnit(),
                canastaGood.getQuantityForPerson(),
                canastaGood.getUserThatOwnsTheGood());
    }

    public static GoodDTO fromGood(Good good) {
        return  new GoodDTO(
                good.getName(),
                good.getPricePerUnit(),
                good.getQuantityForPerson()
                );
    }

    public Good toGood() {
        Good good = new Good();
        good.setName(name);
        good.setPricePerUnit(pricePerUnit);
        good.setQuantityForPerson(quantityForPerson);
        return good;
    }

    public FiestaGood toFiestaGood() {
        FiestaGood fiestaGood = new FiestaGood() ;
        fiestaGood.setName(name);
        fiestaGood.setPricePerUnit(pricePerUnit);
        fiestaGood.setQuantityForPerson(quantityForPerson);
        fiestaGood.setFinalQuantity(finalQuantity);
        return fiestaGood;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public Integer getQuantityForPerson() {
        return quantityForPerson;
    }

    public Integer getFinalQuantity() {
        return finalQuantity;
    }
}
