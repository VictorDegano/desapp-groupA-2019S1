package ar.edu.unq.desapp.grupoa.example;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String  name;
    private Integer life;

    //For a collection of value objects you use this
    @ElementCollection
    private Set<Item> items;

    //For only one, you can use this.
    @Embedded
    private Item itemfavorito;

    private Personaje(){}


    public Personaje(String name,Integer life, Item itemfavorito){
        this.items = new HashSet<>();
        this.itemfavorito = itemfavorito;
        this.name = name;
        this.life = life;
    }


    public void pickUpItem(Item item) {
        items.add(item);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getLife() {
        return life;
    }

    public Set<Item> getItems() {
        return items;
    }

    public boolean hasItem(Item item) {
       return getItems().stream().anyMatch(personajeItem-> personajeItem.getItemName().equalsIgnoreCase(item.getItemName()));
    }
}