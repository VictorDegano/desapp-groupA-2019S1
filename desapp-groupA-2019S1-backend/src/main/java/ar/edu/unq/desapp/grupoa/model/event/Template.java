package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Good> goodsForEvent;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> usersThatUsedThisTemplate = new ArrayList<>();


    public Boolean isForEvent(EventType eventTypeToCompare) {
        return this.eventType.equals(eventTypeToCompare) ;
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Template(String templateName, EventType eventType, List<Good> listOfGoods) {
        this.setName(templateName);
        this.setEventType(eventType);
        this.setGoodsForEvent(listOfGoods);
    }

    public Template() { }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------[GETTER & SETTER METHODS]----------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }

    public List<Good> getGoodsForEvent() {  return goodsForEvent;   }
    public void setGoodsForEvent(List<Good> goodsForEvent) {    this.goodsForEvent = goodsForEvent; }

    public EventType getEventType() {   return eventType;   }
    public void setEventType(EventType eventType) { this.eventType = eventType; }


    public List<User> getUsersThatUsedIt() {
        return this.usersThatUsedThisTemplate;
    }

    public void addUser(User organizer) {
        this.usersThatUsedThisTemplate.add(organizer);
    }

    public Integer getId() {
        return this.id;
    }
}
