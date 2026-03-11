package com.moviex.user;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<UserAccount> authenticate(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return Optional.empty();
        }
        return repository.findByUsernameAndPassword(username, password);
    }

    public boolean register(UserAccount user) {
        if (repository.existsByEmail(user.getEmail()) || repository.existsByUsername(user.getUsername())) {
            return false;
        }
        repository.save(user);
        return true;
    }
}
