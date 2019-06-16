package ar.edu.unq.desapp.grupoa.persistence;

import ar.edu.unq.desapp.grupoa.model.Login;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginDAO extends JpaRepository<Login, Integer> {

    List<Login> getLoginByUserAndLogOutIsNullOrderByLogInDesc(User userToLogin);
}
