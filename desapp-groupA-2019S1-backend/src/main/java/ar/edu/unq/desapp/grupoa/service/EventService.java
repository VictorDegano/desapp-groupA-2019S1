package ar.edu.unq.desapp.grupoa.service;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import ar.edu.unq.desapp.grupoa.model.event.fiesta.Fiesta;
import ar.edu.unq.desapp.grupoa.persistence.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventDAO eventDao;


    public EventService(EventDAO aEventDAO){
        this.eventDao = aEventDAO;
    }

    public EventService() { }

    public Integer create (Event aEventToCreate){
        this.eventDao.save(aEventToCreate);
        return aEventToCreate.getId();
    }

    public List<Integer> createAll(List<Event> aListOfEvents){
        this.eventDao.saveAll(aListOfEvents);
        List<Integer> idList = aListOfEvents.stream()
                                            .map(Event::getId)
                                            .collect(Collectors.toList());
        return idList;
    }

    public Event getById(Integer eventID) {
        return this.eventDao.findById(eventID).get();
    }

    public List<Event> getEventsInProgressForUser(Integer userId) {
        return eventDao.getEventsInProgressForUser(userId);
    }

    // TODO: 1/6/2019 Falta hacer los test
    public List<Event> getLastEventsForUser(Integer userId){
        return eventDao.getLastEventsForUser(userId);
    }
}
