package application.persistence;

import application.model.event.Fiesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FiestaDAO extends JpaRepository<Fiesta, Integer>, QueryByExampleExecutor<Fiesta> {
}
