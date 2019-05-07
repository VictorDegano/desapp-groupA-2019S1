package ar.edu.unq.desapp.grupoa.model.event;

import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Template {

    private String name;
    private List<Good> goodsForEvent;
    private EventType eventType;
    private List<User> usersThatUsedThisTemplate = new ArrayList<>();

    public Boolean isForEvent(EventType eventTypeToCompare) {
        return this.eventType.equals(eventTypeToCompare) ;
    }

/**[}-{]---------------------------------------------[}-{]
   [}-{]----------------[CONSTRUCTORS]---------------[}-{]
   [}-{]---------------------------------------------[}-{]**/
    public Template(String templateName, EventType eventType, List<Good> listOfGoods) {
        this.name = templateName;
        this.eventType = eventType;
        this.goodsForEvent = listOfGoods;
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
}
