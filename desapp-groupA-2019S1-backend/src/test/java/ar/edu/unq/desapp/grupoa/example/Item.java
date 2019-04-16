package ar.edu.unq.desapp.grupoa.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;

    public Item(){ }

    public Item(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
