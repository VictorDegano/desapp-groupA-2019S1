package ar.edu.unq.desapp.grupoa.model.event.baquita;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class LoadedGood {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Guest guest;
    @OneToOne(cascade = CascadeType.ALL)
    private Good  good ;

    public LoadedGood(){}

    public LoadedGood(Guest guest,Good good) {
        this.guest =guest;
        this.good = good;
    }

    public Integer getAmount(){
        return  good.totalCost();
    }

    public Good getGood() {
        return good;
    }

    public Guest getGuest() {
        return guest;
    }

    public Integer getId() {
        return id;
    }
}
