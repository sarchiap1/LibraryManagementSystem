package ecampus.lp.lms.repository;

import ecampus.lp.lms.model.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User,Long>{
    
}
