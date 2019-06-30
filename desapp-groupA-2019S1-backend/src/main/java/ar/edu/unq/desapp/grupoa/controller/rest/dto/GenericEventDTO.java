package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.Good;

import java.util.List;
import java.util.stream.Collectors;

public class GenericEventDTO extends EventDTO {


    private GenericEventDTO(Integer id, String name, Integer organizerId, String type, Integer quantityOfGuest, List<Good> goods, List<GuestDTO> guests) {
        this.id = id;
        this.name = name;
        this.organizerId = organizerId;
        this.type = type;
        this.quantityOfGuest = quantityOfGuest;
        this.goods = goods;
        this.guests = guests;
    }

    public static GenericEventDTO from(Event anEvent) {
        return new GenericEventDTO(
                anEvent.getId(),
                anEvent.getName(),
                anEvent.getOrganizer().getId(),
                anEvent.getType().toString(),
                anEvent.getQuantityOfGuests(),
                anEvent.getGoodsForGuest(),
                getGuests(anEvent.getGuest())
        );
    }

    public static List<GenericEventDTO> fromList(List<Event> eventsInProgress) {
        return eventsInProgress
                .stream()
                .map(GenericEventDTO::from)
                .collect(Collectors.toList());
    }


}
