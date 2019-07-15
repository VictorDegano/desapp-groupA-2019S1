package ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO;

import ar.edu.unq.desapp.grupoa.controller.rest.dto.GoodDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler.BaquitaComunitaryDTOHandler;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler.BaquitaRepresentativesDTOHandler;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler.CanastaDTOHandler;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler.EventDTOHandler;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.eventDTO.dtoHandler.FiestaDTOHandler;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.GuestDTO;
import ar.edu.unq.desapp.grupoa.controller.rest.dto.UserDTO;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventStatus;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.service.EventService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FiestaDTO.class, name = "FIESTA"),
        @JsonSubTypes.Type(value = CanastaDTO.class, name = "CANASTA"),
        @JsonSubTypes.Type(value = BaquitaComunitariaDTO.class, name = "BAQUITA_COMUNITARY"),
        @JsonSubTypes.Type(value = BaquitaRepresentativesDTO.class, name = "BAQUITA_REPRESENTATIVES")
})

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class EventDTO {

    protected Integer id;
    protected String eventName;
    protected UserDTO organizer;
    protected String type;
    protected Integer quantityOfGuest;
    protected List<GoodDTO> goods;
    protected List<GuestDTO> guests;
    protected EventStatus status;
    protected LocalDateTime creationDate;

    static final List<EventDTOHandler> eventDTOHandlers = new ArrayList<EventDTOHandler>() {{
        add(new FiestaDTOHandler());
        add(new CanastaDTOHandler());
        add(new BaquitaComunitaryDTOHandler());
        add(new BaquitaRepresentativesDTOHandler());
    }};

    public static EventDTO fromEvent(Event event) {
       return eventDTOHandlers.stream().filter(dto -> dto.canHandle(event)).findFirst().get().from(event);
    }

    public abstract EventDTO from(Event event);

    public abstract Integer handleCreation(EventService eventService);

    public static List<EventDTO> fromList(List<Event> eventsInProgress) {
        return eventsInProgress.stream().map(EventDTO::fromEvent).collect(Collectors.toList());
    }

    /* Utilities */

    protected static List<GuestDTO> getGuestsFrom(List<Guest> guest) {
        return guest.stream().map(GuestDTO::from).collect(Collectors.toList());
    }

    protected List<Good> toGood(List<GoodDTO> goods) {
        return goods.stream().map(goodDto -> goodDto.toGood()).collect(Collectors.toList());
    }

    protected List<String> guestMail(){
        return guests.stream().map(GuestDTO::getMail).collect(Collectors.toList());
    };

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

    public List<GoodDTO> getGoods() {
        return goods;
    }

    protected List<GoodDTO> getGoodsFromFiestaGood(List<Good> goodsForGuest) {
        return goodsForGuest.stream().map( it -> GoodDTO.fromFiestaGood(it)).collect(Collectors.toList());
    }

    protected List<GoodDTO> getGoodsFromCanastaGood(List<Good> goodsForGuest) {
        return goodsForGuest.stream().map( it -> GoodDTO.fromCanastaGood(it)).collect(Collectors.toList());
    }

    protected List<GoodDTO> getGoodsFrom(List<Good> goodsForGuest) {
        return goodsForGuest.stream().map( it -> GoodDTO.fromGood(it)).collect(Collectors.toList());
    }

    public String getType() {
        return type;
    }

    public List<GuestDTO> getGuests() {
        return guests;
    }

    public EventStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

}
