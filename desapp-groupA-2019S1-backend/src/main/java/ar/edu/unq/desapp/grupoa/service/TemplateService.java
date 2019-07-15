package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import ar.edu.unq.desapp.grupoa.persistence.TemplateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateDAO templateDAO;

    public List<Template> getTemplates(String eventType) {
      return templateDAO.findAllByType(EventType.valueOf(eventType));
    }

    public void useTemplate(Integer templateId, Integer userId) {

    }

    public boolean userUsedTemplate(Integer id, Integer id1) {
        return true;
    }

    public List<Template> templatesFromUsersThatChooseThisTemplate(Integer templateId, Integer maxQuantity) {
        return null;
    }
}
