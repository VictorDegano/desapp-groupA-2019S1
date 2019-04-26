package ar.edu.unq.desapp.grupoa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
public class Fiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private User organizer;
    @Transient
    private List<Guest> guest;
    private LocalDateTime limitConfirmationDateTime;
    @Transient
    private List<Good> goodsForGuest;
    private Integer confirmations;
    private String name;


    /** Confirma la asistencia de un invitado a la fiesta, al hacerlo se recalcula la cantidad de mercaderia a comprar y la cantidad de confirmaciones
     * @param guestToAssist User el usuario invitado
     * @throws
     */
    public void confirmAsistanceOf(Guest guestToAssist) {
        if(guest.stream().anyMatch(guest1 -> guest1==guestToAssist)){
            this.confirmations += 1;

            guestToAssist.confirmAsistance();

            this.getGoodsForGuest().forEach((Good good) -> good.multiplyFinalQuantityBy(this.confirmations));
        } else {
            throw new ConfirmAsistanceException(this.getName(), guestToAssist.name());
        }



    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Fiesta() {}

    public Fiesta(User organizer, List<Guest> guest, LocalDateTime limitConfirmationDateTime, List<Good> goodsForGuest) {
        this.organizer = organizer;
        this.guest = guest;
        this.limitConfirmationDateTime = limitConfirmationDateTime;
        this.goodsForGuest = goodsForGuest;
        this.confirmations = 0;
    }


/**[}-{]---------------------------------------------[}-{]
   [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public List<Guest> getGuest() {    return guest;   }
    public void setGuest(List<Guest> guest) {  this.guest = guest; }

    public void setLimitConfirmationDateTime(LocalDateTime limitConfirmationDateTime) { this.limitConfirmationDateTime = limitConfirmationDateTime; }

    public void setOrganizer(User organizer) {    this.organizer = organizer; }

    public List<Good> getGoodsForGuest() {  return goodsForGuest;   }
    public void setGoodsForGuest(List<Good> goodsForGuest) {    this.goodsForGuest = goodsForGuest; }

    public Integer getId() {    return id;  }

    public Integer getConfirmations() { return confirmations;   }
    public void setConfirmations(Integer confirmations) {   this.confirmations = confirmations; }

    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }
}

