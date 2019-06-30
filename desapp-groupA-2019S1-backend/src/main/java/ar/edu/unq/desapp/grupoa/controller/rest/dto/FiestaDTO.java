package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;

import java.time.LocalDateTime;
import java.util.List;

public class FiestaDTO extends EventDTO {

    private LocalDateTime limitConfirmationDateTime;
    private Integer confirmations;

    private FiestaDTO(Integer id, String name, Integer organizerId,
                        String type, Integer quantityOfGuest, List<Good> goods,
                        List<GuestDTO> guests, LocalDateTime limitConfirmationDateTime,
                        Integer confirmations) {
        this.id = id;
        this.name = name;
        this.organizerId = organizerId;
        this.type = type;
        this.quantityOfGuest = quantityOfGuest;
        this.goods = goods;
        this.guests = guests;

        this.limitConfirmationDateTime = limitConfirmationDateTime;
        this.confirmations = confirmations;
    }

    public static FiestaDTO from(Fiesta aFiesta) {
        return new FiestaDTO(
                aFiesta.getId(),
                aFiesta.getName(),
                aFiesta.getOrganizer().getId(),
                aFiesta.getType().toString(),
                aFiesta.getQuantityOfGuests(),
                aFiesta.getGoodsForGuest(),
                getGuests(aFiesta.getGuest()),
                aFiesta.getLimitConfirmationDateTime(),
                aFiesta.getConfirmations()
                );
    }


    public LocalDateTime getLimitConfirmationDateTime() {
        return limitConfirmationDateTime;
    }

    public Integer getConfirmations() {
        return confirmations;
    }
}
