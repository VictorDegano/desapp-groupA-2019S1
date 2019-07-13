package ar.edu.unq.desapp.grupoa.persistence;

import ar.edu.unq.desapp.grupoa.model.event.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodDAO  extends JpaRepository<Good, Integer> {
}
