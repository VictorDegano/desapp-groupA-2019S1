package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GenericEventDTO extends EventDTO {


    private GenericEventDTO(Integer id, String name, UserDTO organizer,
                            String type, Integer quantityOfGuest, List<Good> goods,
                            List<GuestDTO> guests, EventStatus status,
                            LocalDateTime creationDate) {
        this.id = id;
        this.eventName = name;
        this.organizer = organizer;
        this.type = type;
        this.quantityOfGuest = quantityOfGuest;
        this.goods = goods;
        this.guests = guests;
        this.status = status;
        this.creationDate = creationDate;
    }

    public static GenericEventDTO from(Event anEvent) {
        return new GenericEventDTO(
                anEvent.getId(),
                anEvent.getName(),
                UserDTO.from(anEvent.getOrganizer()),
                anEvent.getType().toString(),
                anEvent.getQuantityOfGuests(),
                anEvent.getGoodsForGuest(),
                getGuestsFrom(anEvent.getGuest()),
                anEvent.getStatus(),
                anEvent.getCreationDate()

        );
    }

    //Not supported
    @Override
    public Integer handleCreation(EventService eventService) {
        return null;
    }


    public static List<GenericEventDTO> fromList(List<Event> eventsInProgress) {
        return eventsInProgress
                .stream()
                .map(GenericEventDTO::from)
                .collect(Collectors.toList());
    }


}
