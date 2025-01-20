package org.timowa.recipemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.timowa.recipemanager.database.entity.User;
import org.timowa.recipemanager.database.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
