package ar.edu.unq.desapp.grupoa.persistence;


import ar.edu.unq.desapp.grupoa.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO extends JpaRepository<Event, Integer>, QueryByExampleExecutor<Event> {

    //Todo: Seria ideal poner un limit y un offset para bancar mostrar mas de X eventos.
    @Query("SELECT event " +
           "FROM Event event " +
           "LEFT JOIN event.guests AS guest "+
           "WHERE (event.organizer.id = :userId OR guest.user.id = :userId) " +
                 "AND event.status = 'OPEN' " +
           "GROUP BY event " +
           "ORDER BY event.creationDate DESC ")
    List<Event> getEventsInProgressForUser(@Param("userId") Integer userId);

    @Query("SELECT event " +
           "FROM Event event " +
           "LEFT JOIN event.guests guest " +
           "WHERE event.organizer.id = :userId " +
                 "OR guest.user.id = :userId " +
           "GROUP BY event " +
           "ORDER BY event.creationDate DESC")
    List<Event> getLastEventsForUser(@Param("userId")Integer userId);

    @Query("SELECT event " +
           "FROM Event event " +
           "LEFT JOIN event.guests guest " +
           "GROUP BY event " +
           "ORDER BY COUNT(guest) DESC")
    List<Event> getMostPopularEventsForUser();
}
