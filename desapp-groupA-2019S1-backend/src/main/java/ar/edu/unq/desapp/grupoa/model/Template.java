package ar.edu.unq.desapp.grupoa.model;

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


}
