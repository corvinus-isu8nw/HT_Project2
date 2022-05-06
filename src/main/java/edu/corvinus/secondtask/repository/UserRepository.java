package edu.corvinus.secondtask.repository;

import edu.corvinus.secondtask.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

    Optional<User> findByUsername(String username);
}
