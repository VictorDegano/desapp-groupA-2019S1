package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.canasta.CanastaGood;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.FiestaGood;
import ar.edu.unq.desapp.grupoa.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodDTO {


    private Integer id;
    private String  name;
    private Integer pricePerUnit;
    private Integer quantityForPerson;
    private Integer finalQuantity;
    private Boolean isAvailable;
    public GoodDTO(){}

    public GoodDTO(String name, Integer pricePerUnit, Integer quantityForPerson, Integer finalQuantity, Integer id) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantityForPerson = quantityForPerson;
        this.finalQuantity = finalQuantity;
        this.id = id;
    }

    public GoodDTO(String name, Integer pricePerUnit, Integer quantityForPerson,Integer id) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantityForPerson = quantityForPerson;
        this.id = id;
    }

    public GoodDTO(String name, Integer pricePerUnit, Integer quantityForPerson, Integer id, Boolean available) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantityForPerson = quantityForPerson;
        this.id = id;
        this.isAvailable = available;

    }

    public static GoodDTO fromFiestaGood(Good good) {
        FiestaGood fiestaGood = (FiestaGood) good;
        return  new GoodDTO(
                fiestaGood.getName(),
                fiestaGood.getPricePerUnit(),
                fiestaGood.getQuantityForPerson(),
                fiestaGood.getFinalQuantity(),
                fiestaGood.getId());
    }

    public static GoodDTO fromGood(Good good) {
        return  new GoodDTO(
                good.getName(),
                good.getPricePerUnit(),
                good.getQuantityForPerson(),
                good.getId()
                );
    }

    public static GoodDTO fromCanastaGood(Good good) {
        Good canastaGood = good;
        return  new GoodDTO(
                canastaGood.getName(),
                canastaGood.getPricePerUnit(),
                canastaGood.getQuantityForPerson(),
                canastaGood.getId(),
                canastaGood.isAvailable());
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

    public Boolean getAvailable() {
        return isAvailable;
    }
}
