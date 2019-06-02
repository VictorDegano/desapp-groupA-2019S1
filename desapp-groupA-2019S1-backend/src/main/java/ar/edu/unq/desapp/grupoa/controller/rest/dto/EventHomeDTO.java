package ar.edu.unq.desapp.grupoa.controller.rest.dto;

// TODO: 2/6/2019 hacer test
public class EventHomeDTO {

    public Integer id;
    public String name;
    public UserEventDTO organizer;
    public String type;
    public Integer quantityOfGuest;

    public EventHomeDTO(Integer id, String name, UserEventDTO organizer, String type, Integer quantityOfGuest) {
        this.id = id;
        this.name = name;
        this.organizer = organizer;
        this.type = type;
        this.quantityOfGuest = quantityOfGuest;
    }

    public EventHomeDTO() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizer(UserEventDTO organizador) {
        this.organizer = organizador;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantityOfGuest(Integer quantityOfGuest) {
        this.quantityOfGuest = quantityOfGuest;
    }
}
