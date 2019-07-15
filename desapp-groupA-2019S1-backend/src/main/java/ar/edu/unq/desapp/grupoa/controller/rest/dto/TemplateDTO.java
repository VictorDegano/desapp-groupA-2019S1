package ar.edu.unq.desapp.grupoa.controller.rest.dto;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateDTO {

    private Integer id;

    private String name;
    private List<GoodDTO> goodsForEvent;
    private String eventType;

    private List<UserDTO> usersThatUsedThisTemplate = new ArrayList<>();

    public TemplateDTO(){};

    public TemplateDTO(Integer id, String name, List<GoodDTO> goodsForEvent, String eventType, List<UserDTO> usersThatUsedThisTemplate) {
        this.id = id;
        this.name = name;
        this.goodsForEvent = goodsForEvent;
        this.eventType = eventType;
        this.usersThatUsedThisTemplate = usersThatUsedThisTemplate;
    }

    public static TemplateDTO from(Template template) {
        return new TemplateDTO(
                template.getId(),
                template.getName(),
                fromGoods(template.getGoodsForEvent()),
                template.getEventType().toString(),
                fromUsers(template.getUsersThatUsedIt())
        );
    }

    private static List<UserDTO> fromUsers(List<User> usersThatUsedIt) {
        return usersThatUsedIt.stream().map(UserDTO::from).collect(Collectors.toList());
    }

    private static List<GoodDTO> fromGoods(List<Good> goodsForEvent) {
        return goodsForEvent.stream().map(GoodDTO::fromGood).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<GoodDTO> getGoodsForEvent() {
        return goodsForEvent;
    }

    public String getEventType() {
        return eventType;
    }

    public List<UserDTO> getUsersThatUsedThisTemplate() {
        return usersThatUsedThisTemplate;
    }
}
