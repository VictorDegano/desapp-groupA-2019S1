package example;

import javax.persistence.Embeddable;

@Embeddable
//@MappedSuperclass you can use this annotation if you got a hierarchy of value objects.
public class Item {

    private final String itemName;

    public Item(String name){
        this.itemName = name;
    }

    public String getItemName() {
        return itemName;
    }

    //FOR JPA
    private Item(){
        itemName = null;
    }

}
