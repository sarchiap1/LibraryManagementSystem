package ecampus.lp.lms.repository;

import ecampus.lp.lms.model.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User,Long>{

    // spring framework create a query like
    // select 1 * from user u where u.email = : email
    Optional<User> findByEmail(String email);
    
}
