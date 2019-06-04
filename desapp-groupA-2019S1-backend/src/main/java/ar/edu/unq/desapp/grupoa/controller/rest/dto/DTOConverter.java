package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class DTOConverter {

    public static ArrayList<EventHomeDTO> createEventHomeDTOList(List<Event> events){
        ArrayList<EventHomeDTO> eventDtoList = new ArrayList<>();

        for (Event event: events) {
            eventDtoList.add(createEventHomeDTO(event));
        }
        return eventDtoList;
    }

    private static EventHomeDTO createEventHomeDTO(Event aEvent){
        User organizer = aEvent.getOrganizer();
        UserEventDTO aOrganizerDTO = new UserEventDTO(
                                                organizer.getId(),
                                                organizer.getFirstName(),
                                                organizer.getLastName(),
                                                organizer.getEmail());

        EventHomeDTO eventDTO = new EventHomeDTO();
        eventDTO.setId(aEvent.getId());
        eventDTO.setName(aEvent.getName());
        eventDTO.setOrganizer(aOrganizerDTO);
        eventDTO.setType(aEvent.getType().name());

        if(aEvent.getGuest() == null){
            eventDTO.setQuantityOfGuest(0);
        } else {
            eventDTO.setQuantityOfGuest(aEvent.getGuest().size());
        }

        return eventDTO;
    }
}
