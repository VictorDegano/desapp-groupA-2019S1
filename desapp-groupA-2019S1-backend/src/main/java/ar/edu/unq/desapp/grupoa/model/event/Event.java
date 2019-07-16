package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.exception.event.InvitationException;
import ar.edu.unq.desapp.grupoa.exception.event.InvitationLimitException;
import ar.edu.unq.desapp.grupoa.model.event.baquita.LoadedGood;
import ar.edu.unq.desapp.grupoa.model.event.createstrategy.CreateEventStrategySelector;
import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @NotBlank(message = "Name is mandatory")
    protected String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Organizer is mandatory")
    protected User organizer;
    @OneToMany(cascade = CascadeType.ALL)
    protected List<Guest> guests;
    @OneToMany(cascade = CascadeType.ALL)
    protected List<Good> goodsForGuest;
    @Enumerated(EnumType.STRING)
    protected EventStatus status;

    protected LocalDateTime creationDate;

    public static Event createWithATemplate(String name, User organizer, List<Guest> guests, LocalDateTime limitTime, Template template, EventType aEventType, LocalDateTime creationDate){
        return CreateEventStrategySelector.selectStrategyFor(aEventType).createEvent(name, organizer, guests, limitTime, template, creationDate);
    }

    public abstract boolean eventIsClosed();

    public abstract void close();

    public abstract Integer totalCost();

    public abstract void confirmAsistancesOf(Guest guestToAssist);

    public Guest inviteUser(User userToInvite){
        if(this.canInviteUser()){
            return makeInvitation(userToInvite);
        } else {
            throw new InvitationLimitException(userToInvite);
        }
    }

    private Guest makeInvitation(User userToInvite) {
        if(alreadyHaveAsAGuest(userToInvite)) {
            throw new InvitationException(this, userToInvite);
        }
        Guest newGuest = new Guest(userToInvite);
        this.getGuest().add(newGuest);
        return newGuest;
    }

    private boolean alreadyHaveAsAGuest(User userToInvite) {
        return this.getGuest()
                   .stream()
                   .anyMatch(guest1 -> guest1.isTheUser(userToInvite));
    }

    protected boolean canInviteUser(){
        return !this.eventIsClosed();
    }


// TODO: 2/5/2019  vale la pena que podamos agregar mas goods una vez creado el evento?

//    public abstract void addGood(Good goodToAdd); //Se podra agregar mas goods una vez creada?¿
/** [}-{]---------------------------------------------[}-{]
    [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
    [}-{]---------------------------------------------[}-{]**/
    public User getOrganizer() {    return this.organizer;   }

    public void setOrganizer(User organizer) {  this.organizer = organizer; }
    public String getName() {   return this.name;    }

    public void setName(String name) {  this.name = name;   }
    public List<Guest> getGuest() { return this.guests;   }

    public void setGuest(List<Guest> guest) {   this.guests = guest; }
    public List<Good> getGoodsForGuest() {  return this.goodsForGuest;   }

    public void setGoodsForGuest(List<Good> goodsForGuest) {    this.goodsForGuest = goodsForGuest; }
    public Integer getId() { return this.id; }

    public void setStatus(EventStatus status) { this.status = status;   }

    public LocalDateTime getLimitConfirmationDateTime(){   return null; }

    public Integer getConfirmations(){ return guests.stream().filter(guest -> guest.isconfirmed()).collect(Collectors.toList()).size(); }

    public List<Guest> getRepresentatives() {
        return null;
    }

    public List<LoadedGood> getLoadedGoods() {
        return null;
    }

    public LocalDateTime getCreationDate() {    return creationDate;    }

    public void setCreationDate(LocalDateTime creationDate) {   this.creationDate = creationDate;   }
    public Integer getQuantityOfGuests(){
        return this.guests.size();
    }

    public abstract EventType getType();

    public List<Guest> getGuests() {
        return guests;
    }

    public EventStatus getStatus() {
        return status;
    }
}
