package ar.edu.unq.desapp.grupoa.persistence;

import ar.edu.unq.desapp.grupoa.model.Login;
import ar.edu.unq.desapp.grupoa.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginDAO extends JpaRepository<Login, Integer>, QueryByExampleExecutor<Login> {

    List<Login> getLoginByUserAndLogOutIsNullOrderByLogInDesc(User userToLogin);

    Login getLoginByUserIdAndAccessToken(Integer userId, String accessToken);
}
