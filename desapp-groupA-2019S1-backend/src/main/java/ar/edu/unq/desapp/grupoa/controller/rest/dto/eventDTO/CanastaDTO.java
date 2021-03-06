package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.GoodDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.GuestDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.canasta.Canasta;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.service.EventService;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CanastaDTO extends EventDTO {


    public CanastaDTO(Integer id, String name, UserDTO organizer,
                     String type, Integer quantityOfGuest, List<GoodDTO> goods,
                     List<GuestDTO> guests, EventStatus status, LocalDateTime creationDate) {
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

    public CanastaDTO(){}

    @Override
    public CanastaDTO from(Event aCanasta) {
        return new CanastaDTO(
                aCanasta.getId(),
                aCanasta.getName(),
                UserDTO.from(aCanasta.getOrganizer()),
                aCanasta.getType().toString(),
                aCanasta.getQuantityOfGuests(),
                getGoodsFromCanastaGood(aCanasta.getGoodsForGuest()),
                getGuestsFrom(aCanasta.getGuest()),
                aCanasta.getStatus(),
                aCanasta.getCreationDate()
        );
    }

    @Override
    public Integer handleCreation(EventService eventService) {
        return eventService.createCanasta(
                this.eventName,
                this.organizer.id,
                this.guestMail(),
                toGood(this.goods));
    }


    @Override
    public void handleUpdate(EventService eventService) {
        Canasta canasta =  (Canasta) eventService.getById(id);

        Optional.ofNullable(eventName)                .ifPresent(canasta::setName);
        Optional.ofNullable(status)                   .ifPresent(canasta::setStatus);

        eventService.update(canasta);
    }



}
