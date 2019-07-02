package ar.edu.unq.desapp.grupoa.persistence;


import ar.edu.unq.desapp.grupoa.model.event.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestDAO extends JpaRepository<Guest, Integer> {

}

