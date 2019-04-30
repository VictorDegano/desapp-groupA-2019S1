package ar.edu.unq.desapp.grupoa.model.event;

public class Good {

    private Integer id;
    private String name;
    private Integer pricePerUnit;
    private Integer quantityForPerson;

    private Integer finalQuantity;

    public void multiplyFinalQuantityBy(Integer quantityToMultiply) {
        this.finalQuantity = this.quantityForPerson * quantityToMultiply;
    }

    public Integer totalCost() {
        return this.pricePerUnit * this.quantityForPerson * this.finalQuantity;
    }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------------[CONSTRUCTORS]---------------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public Good(String name, Integer price, Integer quantity) {
        this.name = name;
        this.pricePerUnit = price;
        this.quantityForPerson = quantity;
        this.finalQuantity = 0;
    }

    public Good() { }

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/

    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public void setPricePerUnit(Integer pricePerUnit) { this.pricePerUnit = pricePerUnit;   }

    public void setQuantityForPerson(Integer quantityForPerson) {   this.quantityForPerson = quantityForPerson; }

    public Integer getFinalQuantity() { return this.finalQuantity;   }
    public void setFinalQuantity(Integer finalQuantity) {   this.finalQuantity = finalQuantity; }

}