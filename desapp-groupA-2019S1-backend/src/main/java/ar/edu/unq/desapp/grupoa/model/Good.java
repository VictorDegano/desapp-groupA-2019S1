package ar.edu.unq.desapp.grupoa.model;

public class Good {

    private Integer id;
    private String name;
    private Integer pricePerUnit;
    private Integer quantityForPerson;
    private Integer finalQuantity;

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Good(String name, Integer price, Integer quantity) {
        this.name = name;
        this.pricePerUnit = price;
        this.quantityForPerson = quantity;
        this.finalQuantity = quantity;
    }

    public Good() { }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Integer getId() {    return id;  }
    public void setId(Integer id) { this.id = id;   }

    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public Integer getPricePerUnit() {  return pricePerUnit;    }
    public void setPricePerUnit(Integer pricePerUnit) { this.pricePerUnit = pricePerUnit;   }

    public Integer getQuantityForPerson() { return quantityForPerson;   }
    public void setQuantityForPerson(Integer quantityForPerson) {   this.quantityForPerson = quantityForPerson; }

    public Integer getFinalQuantity() { return finalQuantity;   }
    public void setFinalQuantity(Integer finalQuantity) {   this.finalQuantity = finalQuantity; }

}