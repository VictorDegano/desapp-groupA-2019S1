package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.service.EventService;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FiestaDTO.class, name = "FIESTA")
})
public abstract class EventDTO {

    protected Integer id;
    protected String eventName;
    protected UserDTO organizer;
    protected String type;
    protected Integer quantityOfGuest;
    protected List<Good> goods;
    protected List<GuestDTO> guests;
    protected EventStatus status;

    protected LocalDateTime creationDate;

    static final List<EventDTOHandler> eventDTOHandlers = new ArrayList<EventDTOHandler>() {{
        add(new FiestaDTOHandler());
    }};

    public static EventDTO from(Event event) {
       return eventDTOHandlers.stream().filter(dto -> dto.canHandle(event)).findFirst().get().from(event);
    }


    /* Getters */
    public Integer getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public UserDTO getOrganizer() {
        return organizer;
    }

    public Integer getQuantityOfGuest() {
        return quantityOfGuest;
    }

    public List<Good> getGoods() {
        return goods;
    }


    public String getType() {
        return type;
    }

    public List<GuestDTO> getGuests() {
        return guests;
    }

    protected static List<GuestDTO> getGuestsFrom(List<Guest> guest) {
        return guest.stream().map(GuestDTO::from).collect(Collectors.toList());
    }

    public EventStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }


    public abstract Integer handleCreation(EventService eventService);

    protected List<Integer> guestsId(){
        return guests.stream().map(GuestDTO::getUserId).collect(Collectors.toList());
    };
}
