package com.moviex.user;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserAccount, String> {

    Optional<UserAccount> findByUsernameAndPassword(String username, String password);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
