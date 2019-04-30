package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.Good;
import ar.edu.unq.desapp.grupoa.model.Guest;
import ar.edu.unq.desapp.grupoa.model.user.User;
import javax.persistence.*;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
abstract public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Transient
    private User organizer;
    @Transient
    private List<Guest> guest;
    @Transient
    private List<Good> goodsForGuest; //Este seria un generico, cada evento implementaria su concreto de ser necesario

    /**Valdria la pena tener este metodo en el evento y darle la logica para que reciba
     * el tipo de evento a crear, los datos y esta lo cree?*/
    //Static createWithATemplate(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template)

    abstract boolean eventIsClosed();

    abstract void close();

    abstract Integer totalCost();

    abstract Guest inviteUser(User userToInvite);

    abstract void confirmAsistancesOf(Guest guestToAssist); //Habria que definir si se recibe el User o el Guest.

    abstract void addGood(Good goodToAdd); //Se podra agregar mas goods una vez creada?¿
}
