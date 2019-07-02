package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.GuestDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

public class FiestaDTO extends EventDTO {

    private LocalDateTime limitConfirmationDateTime;
    private Integer confirmations;

    public FiestaDTO(Integer id, String name, UserDTO organizer,
                     String type, Integer quantityOfGuest, List<Good> goods,
                     List<GuestDTO> guests, LocalDateTime limitConfirmationDateTime,
                     Integer confirmations, EventStatus status, LocalDateTime creationDate) {
        this.id = id;
        this.eventName = name;
        this.organizer = organizer;
        this.type = type;
        this.quantityOfGuest = quantityOfGuest;
        this.goods = goods;
        this.guests = guests;
        this.limitConfirmationDateTime = limitConfirmationDateTime;
        this.confirmations = confirmations;
        this.status = status;
        this.creationDate = creationDate;
    }

    public FiestaDTO(){}

    @Override
    public FiestaDTO from(Event aFiesta) {
        return new FiestaDTO(
                aFiesta.getId(),
                aFiesta.getName(),
                UserDTO.from(aFiesta.getOrganizer()),
                aFiesta.getType().toString(),
                aFiesta.getQuantityOfGuests(),
                aFiesta.getGoodsForGuest(),
                getGuestsFrom(aFiesta.getGuest()),
                aFiesta.getLimitConfirmationDateTime(),
                aFiesta.getConfirmations(),
                aFiesta.getStatus(),
                aFiesta.getCreationDate()
        );
    }

    @Override
    public Integer handleCreation(EventService eventService) {
       return eventService.createFiesta(
               this.eventName,
               this.organizer.id,
               this.guestsId(),
               this.limitConfirmationDateTime,
               this.goods
       );
    }


    public LocalDateTime getLimitConfirmationDateTime() {
        return limitConfirmationDateTime;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

}
