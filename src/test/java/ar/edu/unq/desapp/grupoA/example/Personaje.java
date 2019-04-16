package ar.edu.unq.desapp.grupoA.example;


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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> items;

    public Personaje(){}


    public Personaje(String name,Integer life ){
        this.items = new HashSet<>();
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
       return getItems().stream().anyMatch(personajeItem-> personajeItem.getName().equalsIgnoreCase(item.getName()));
    }
}