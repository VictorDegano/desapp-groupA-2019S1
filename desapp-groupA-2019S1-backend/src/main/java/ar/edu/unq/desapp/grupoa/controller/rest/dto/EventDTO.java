package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;

import java.util.List;

public abstract class EventDTO {

    protected Integer id;
    protected String name;
    protected Integer organizerId;
    protected String type;
    protected Integer quantityOfGuest;
    protected List<Good> goods;
    protected List<GuestDTO> guests;


    protected static List<GuestDTO> getGuests(List<Guest> guests) {
        return GuestDTO.fromList(guests);
    }

    /* Getters */

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public Integer getQuantityOfGuest() {
        return quantityOfGuest;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public List<GuestDTO> getGuests() {
        return guests;
    }

}
