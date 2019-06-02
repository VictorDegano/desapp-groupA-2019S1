package ar.edu.unq.desapp.grupoa.persistence;

import ar.edu.unq.desapp.grupoa.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface EventDAO extends JpaRepository<Event, Integer>, QueryByExampleExecutor<Event> {

    //Todo: Seria ideal poner un limit y un offset para bancar mostrar mas de X eventos.
    @Query("SELECT event FROM Event event WHERE event.organizer.id = :userId AND event.status = 'OPEN'")
    List<Event> getEventsInProgressForUser(@Param("userId") Integer userId);

    @Query("SELECT event FROM Event event WHERE event.organizer.id = :userId ORDER BY event.creationDate DESC")
    List<Event> getLastEventsForUser(@Param("userId")Integer userId);
}