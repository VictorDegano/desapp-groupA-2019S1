package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.event.createstrategy.CreateEventStrategySelector;
import ar.edu.unq.desapp.grupoa.model.user.User;
import javax.persistence.*;
import java.time.LocalDateTime;
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
    public static Event createWithATemplate(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template, EventType aEventType){
        return CreateEventStrategySelector.selectStrategyFor(aEventType).createEvent(name, organizer, guests, limitTime, template);
    }

    public abstract boolean eventIsClosed();

    public abstract void close();

    public abstract Integer totalCost();

    public abstract void confirmAsistancesOf(Guest guestToAssist); //Habria que definir si se recibe el User o el Guest.

// TODO: 2/5/2019  Fiesta, Canasta y baquitas tienen que implementarlos!

//    public abstract Guest inviteUser(User userToInvite);

//    public abstract void addGood(Good goodToAdd); //Se podra agregar mas goods una vez creada?¿

/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public User getOrganizer() {    return this.organizer;   }
    public void setOrganizer(User organizer) {  this.organizer = organizer; }

    public String getName() {   return this.name;    }
    public void setName(String name) {  this.name = name;   }

    public List<Guest> getGuest() { return this.guest;   }
    public void setGuest(List<Guest> guest) {   this.guest = guest; }

    public List<Good> getGoodsForGuest() {  return this.goodsForGuest;   }
    public void setGoodsForGuest(List<Good> goodsForGuest) {    this.goodsForGuest = goodsForGuest; }
}
