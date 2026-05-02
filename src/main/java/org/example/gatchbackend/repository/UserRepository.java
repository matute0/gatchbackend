package org.example.gatchbackend.repository;

import org.example.gatchbackend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    User findUserByEmail(String mail);

    User findUserByUsername(String username);
}
