package ar.edu.unq.desapp.grupoa.persistence;


import ar.edu.unq.desapp.grupoa.model.event.EventType;
import ar.edu.unq.desapp.grupoa.model.event.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateDAO extends JpaRepository<Template, Integer> {


    @Query("SELECT template " +
            "FROM Template template " +
            "WHERE template.eventType = ?1")
    List<Template> findAllByType(EventType typeEvent);
}
