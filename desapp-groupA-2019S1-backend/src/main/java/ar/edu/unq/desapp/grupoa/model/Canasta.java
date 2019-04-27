package ar.edu.unq.desapp.grupoa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Canasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Transient
    private User organizer;
    @Transient
    private List<Guest> guests;
    @Transient
    private List<Good> goods;


    public Canasta(String name, User organizer) {
        this.name = name;
        this.organizer = organizer;
        this.guests = new ArrayList<>();
        this.goods = new ArrayList<>();
    }

    public Canasta(String name, User organizer, List<Guest> guests, List<Good> goods) {
        this.name = name;
        this.organizer = organizer;
        this.guests = guests;
        this.goods = goods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
