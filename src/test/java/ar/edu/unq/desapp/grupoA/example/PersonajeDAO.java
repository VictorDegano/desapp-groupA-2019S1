package ar.edu.unq.desapp.grupoA.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface PersonajeDAO extends JpaRepository<Personaje, Integer>, QueryByExampleExecutor<Personaje> {
}
