package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.GuestDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.service.EventService;

import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

public class BaquitaRepresentativesDTO extends EventDTO {

    @OneToMany
    private List<GuestDTO> representatives;

    @Transient
    protected List<LoadedGoodDTO> loadedGoods;

    public BaquitaRepresentativesDTO(Integer id, String name, UserDTO organizer,
                                 String type, Integer quantityOfGuest, List<Good> goods,
                                 List<GuestDTO> guests, EventStatus status, LocalDateTime creationDate, List<GuestDTO> representatives, List<LoadedGoodDTO> loadedGoods) {
        this.id = id;
        this.eventName = name;
        this.organizer = organizer;
        this.type = type;
        this.quantityOfGuest = quantityOfGuest;
        this.goods = goods;
        this.guests = guests;
        this.status = status;
        this.creationDate = creationDate;
        this.representatives = representatives;
        this.loadedGoods = loadedGoods;


    }
    public BaquitaRepresentativesDTO(){}

    @Override
    public EventDTO from(Event aBaquita) {
        return new BaquitaRepresentativesDTO(
                aBaquita.getId(),
                aBaquita.getName(),
                UserDTO.from(aBaquita.getOrganizer()),
                aBaquita.getType().toString(),
                aBaquita.getQuantityOfGuests(),
                aBaquita.getGoodsForGuest(),
                getGuestsFrom(aBaquita.getGuest()),
                aBaquita.getStatus(),
                aBaquita.getCreationDate(),
                getGuestsFrom(aBaquita.getRepresentatives()),
                LoadedGoodDTO.fromList(aBaquita.getLoadedGoods())
        );
    }

    @Override
    public Integer handleCreation(EventService eventService) {
       return eventService.createBaquitaRepresentatives(
                this.eventName,
                this.organizer.id,
               this.guestMail(),
               this.goods);
    }
}
