package ar.edu.unq.desapp.grupoa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Fiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String organizer;
    @Transient
    private List<String> guest;
    private LocalDateTime limitConfirmationDateTime;

    @Transient
    private List<Good> goodsForGuest;

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Fiesta() {}

    public Fiesta(String organizer, List<String> guest, LocalDateTime limitConfirmationDateTime, List<Good> goodsForGuest) {
        this.organizer = organizer;
        this.guest = guest;
        this.limitConfirmationDateTime = limitConfirmationDateTime;
        this.goodsForGuest = goodsForGuest;
    }


/**[}-{]---------------------------------------------[}-{]
   [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public List<String> getGuest() {    return guest;   }

    public void setGuest(List<String> guest) {  this.guest = guest; }

    public LocalDateTime getLimitConfirmationDateTime() {   return limitConfirmationDateTime;   }

    public void setLimitConfirmationDateTime(LocalDateTime limitConfirmationDateTime) { this.limitConfirmationDateTime = limitConfirmationDateTime; }

    public String getOrganizer() {  return organizer;   }

    public void setOrganizer(String organizer) {    this.organizer = organizer; }

    public List<Good> getGoodsForGuest() {  return goodsForGuest;   }

    public void setGoodsForGuest(List<Good> goodsForGuest) {    this.goodsForGuest = goodsForGuest; }

    public Integer getId() {    return id;  }

    public void setId(Integer id) { this.id = id;   }
}

