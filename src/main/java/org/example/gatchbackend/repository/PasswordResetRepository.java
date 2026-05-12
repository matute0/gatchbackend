package org.example.gatchbackend.repository;

import org.example.gatchbackend.models.PasswordReset;
import org.example.gatchbackend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends MongoRepository<PasswordReset, String> {
    PasswordReset getPasswordResetByCode(String code);
}
