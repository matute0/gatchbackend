package org.example.gatchbackend.repository;

import org.example.gatchbackend.models.UserVerification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationRepository extends MongoRepository<UserVerification, String> {
    UserVerification findUserVerificationByCode(String code);
}
