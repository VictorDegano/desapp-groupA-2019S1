package ar.edu.unq.desapp.grupoa.persistence;

import ar.edu.unq.desapp.grupoa.model.Fiesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FiestaDAO extends JpaRepository<Fiesta, Integer>, QueryByExampleExecutor<Fiesta> {
}
