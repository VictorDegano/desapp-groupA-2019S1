package ar.edu.unq.desapp.grupoa.model;

import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.exception.ConfirmAsistanceException;
import ar.edu.unq.desapp.grupoa.model.stateFiesta.FiestaState;
import ar.edu.unq.desapp.grupoa.model.stateFiesta.OpenFiesta;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Fiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private User organizer;
    @Transient
    private List<Guest> guest;
    private LocalDateTime limitConfirmationDateTime;
    @Transient
    private List<Good> goodsForGuest;
    private Integer confirmations;
    private String name;
    @Transient
    private FiestaState state;

    public void confirmAsistancesOf(Guest guestToAssist){
        this.getState().confirmAssistanceOf(guestToAssist);
    }

    public void completeConfirmationAsistance(Guest guestToAssist){
        if(isInvited(guestToAssist)){
            this.confirmations += 1;

            guestToAssist.confirmAsistance();

            updateFinalQuantityOfGoods();
        } else {
            throw new ConfirmAsistanceException(this, guestToAssist);
        }
    }

    private void updateFinalQuantityOfGoods() {
        this.getGoodsForGuest().forEach((Good good) -> good.multiplyFinalQuantityBy(this.confirmations));
    }

    public Integer totalCost() {
        return this.getGoodsForGuest()
                   .stream()
                   .mapToInt(Good::totalCost)
                   .sum();
    }

    private boolean isInvited(Guest guestToAssist) {
        return guest.stream().anyMatch(guest -> guest.areThatGuest(guestToAssist));
    }

    public boolean canConfirmInvitation(LocalDateTime aLocalDateTimeToCompare) {
        return this.limitConfirmationDateTime.isAfter(aLocalDateTimeToCompare);
    }

    public boolean eventIsClosed() {
        return this.getState().isClosed();
    }

    public void close() {
        this.setState(this.getState().nextState());
        cancelAllPendingInvitations();
    }

    private void cancelAllPendingInvitations() {
        this.getGuest().forEach(this::cancelPendingInvitation);
    }

    private void cancelPendingInvitation(Guest invitedGuest) {
        if(invitedGuest.isInvitationPending()){
            invitedGuest.cancelInvitation();
        }
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Fiesta() {}

    public Fiesta(String name, User organizer, List<Guest> guest, LocalDateTime limitConfirmationDateTime, List<Good> goodsForGuest) {
        this.organizer = organizer;
        this.guest = guest;
        this.limitConfirmationDateTime = limitConfirmationDateTime;
        this.goodsForGuest = goodsForGuest;
        this.confirmations = 0;
        this.name = name;
        this.state = new OpenFiesta(this);
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public List<Guest> getGuest() {    return this.guest;   }
    public void setGuest(List<Guest> guest) {  this.guest = guest; }

    public void setLimitConfirmationDateTime(LocalDateTime limitConfirmationDateTime) { this.limitConfirmationDateTime = limitConfirmationDateTime; }
    public LocalDateTime getLimitConfirmationDateTime() {   return this.limitConfirmationDateTime;   }

    public void setOrganizer(User organizer) {    this.organizer = organizer; }

    public List<Good> getGoodsForGuest() {  return this.goodsForGuest;   }
    public void setGoodsForGuest(List<Good> goodsForGuest) {    this.goodsForGuest = goodsForGuest; }

    public Integer getId() {    return this.id;  }

    public Integer getConfirmations() { return this.confirmations;   }
    public void setConfirmations(Integer confirmations) {   this.confirmations = confirmations; }

    public String getName() {   return this.name;    }
    public void setName(String name) {  this.name = name;   }

    public FiestaState getState() { return state;   }
    public void setState(FiestaState state) {   this.state = state; }
}

