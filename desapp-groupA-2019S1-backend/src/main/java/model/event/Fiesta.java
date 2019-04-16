package model.event;

import com.sun.tools.javac.util.List;
import model.good.Good;
import java.time.LocalDateTime;


public class Fiesta {

    private String organizer;
    private List<String> guest;
    private LocalDateTime limitConfirmationDateTime;
    private List<Good> goodsForGuest;

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Fiesta() {
    }

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
}

