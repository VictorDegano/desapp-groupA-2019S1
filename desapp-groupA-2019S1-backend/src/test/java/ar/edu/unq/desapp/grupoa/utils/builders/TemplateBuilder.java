package ar.edu.unq.desapp.grupoa.utils.builders;

import ar.edu.unq.desapp.grupoa.model.EventType;
import ar.edu.unq.desapp.grupoa.model.Good;
import ar.edu.unq.desapp.grupoa.model.Template;

import java.util.ArrayList;
import java.util.List;

public class TemplateBuilder {

    private Template template;

    private TemplateBuilder(){
        this.template  = new Template();
        this.template.setGoodsForEvent(new ArrayList<>());
    }

    public static TemplateBuilder buildATemplate(){
        return new TemplateBuilder();
    }

    public TemplateBuilder withName(String name){
        this.template.setName(name);
        return this;
    }

    public TemplateBuilder withEventType(EventType type) {
        this.template.setEventType(type);
        return this;
    }

    public TemplateBuilder addGood(Good good) {
        List<Good> listOfGood = this.template.getGoodsForEvent();
        listOfGood.add(good);
        this.template.setGoodsForEvent(listOfGood);
        return this;
    }

    public Template build(){
        return this.template;
    }
}
