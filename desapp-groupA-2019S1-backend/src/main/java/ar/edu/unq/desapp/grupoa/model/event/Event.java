package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.user.User;
import javax.persistence.*;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
abstract public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String name;
    @Transient
    protected User organizer;
    @Transient
    protected List<Guest> guests;
    @Transient
    protected List<Good> goodsForGuest; //Este seria un generico, cada evento implementaria su concreto de ser necesario

    /**Valdria la pena tener este metodo en el evento y darle la logica para que reciba
     * el tipo de evento a crear, los datos y esta lo cree?*/
    //Static createWithATemplate(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template)

    protected abstract Boolean eventIsClosed();

    protected abstract void close();

    protected abstract Integer totalCost();

    protected abstract Guest inviteUser(User userToInvite);

    protected abstract void confirmAsistanceOf(User guestToAssist); //Habria que definir si se recibe el User o el Guest.

    protected abstract void addGood(Good goodToAdd); //Se podra agregar mas goods una vez creada?¿

}
