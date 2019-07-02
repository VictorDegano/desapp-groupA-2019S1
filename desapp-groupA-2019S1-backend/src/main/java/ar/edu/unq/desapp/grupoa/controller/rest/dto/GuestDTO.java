package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Guest;
import ar.edu.unq.desapp.grupoa.model.event.InvitationState;
import ar.edu.unq.desapp.grupoa.model.user.User;

public class GuestDTO {

    private Integer guestId;
    private Integer userId;
    private String mail;
    private String firstName;

    private String lastName;

    private InvitationState confirmAsistance;


    public GuestDTO(Integer guestId, Integer userId, String mail, String firstName, String lastName, InvitationState confirmAsistance) {
        this.guestId = guestId;
        this.userId = userId;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.confirmAsistance = confirmAsistance;
    }

    public GuestDTO(){}

    public static GuestDTO from(Guest guest) {
        User user = guest.getUser();
        return new GuestDTO(guest.getId(),user.getId(),user.getEmail(),user.getFirstName(),user.getLastName(),guest.getConfirmAsistance());
    }

    public Integer getGuestId() {
        return guestId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public InvitationState getConfirmAsistance() {
        return confirmAsistance;
    }
}
