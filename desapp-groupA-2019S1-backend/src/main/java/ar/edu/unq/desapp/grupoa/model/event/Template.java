package ar.edu.unq.desapp.grupoa.model.event;

import java.util.List;

public class Template {

    private String name;
    private List<Good> goodsForEvent;
    private EventType eventType;

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
}
