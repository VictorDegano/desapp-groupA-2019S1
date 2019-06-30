package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Guest;

import java.util.List;
import java.util.stream.Collectors;

public class GuestDTO {

    private Integer id;

    private String  mail;

    public GuestDTO(Integer id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public static List<GuestDTO> fromList(List<Guest> guest) {
        return guest
                .stream()
                .map(GuestDTO::from)
                .collect(Collectors.toList());
    }

    public static GuestDTO from(Guest guest) {
        return new GuestDTO(guest.getUser().getId(),guest.getUser().getEmail());
    }

    public Integer getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }
}
