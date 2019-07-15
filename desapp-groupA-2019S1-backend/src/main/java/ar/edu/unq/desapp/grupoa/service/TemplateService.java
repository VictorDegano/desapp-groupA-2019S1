package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.exception.TemplateNotFoundException;
import ar.edu.unq.desapp.grupoa.exception.user.UserNotFoundException;
import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.model.user.User;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import ar.edu.unq.desapp.grupoa.persistence.TemplateDAO;
import ar.edu.unq.desapp.grupoa.persistence.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateDAO templateDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;


    @Autowired
    private EventService eventService;

    public List<Template> getTemplates(String eventType) {
        return templateDAO.findAllByType(EventType.valueOf(eventType));
    }

    public Integer useTemplate(Integer templateId, Integer userId, List<String> guestsMails) {
        Template template = templateDAO.findById(templateId).orElseThrow(() -> new TemplateNotFoundException(templateId));
        User user = userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        LocalDateTime limitTime = LocalDateTime.now();

        if (template.getLimitTime() != null){
            limitTime = template.getLimitTime();
        }

        Event eventCreated = Event.createWithATemplate(template.getName(), user, eventService.getGuests(guestsMails),limitTime,template,template.getEventType(), LocalDateTime.now());

        eventDAO.save(eventCreated);
        template.addUser(user);
        templateDAO.save(template);

        eventService.sendInvitationToGuests(eventCreated);
        return eventCreated.getId();
    }




    public List<Template> templatesFromUsersThatChooseThisTemplate(Integer templateId, Integer maxQuantity) {
        return null;
    }



}
