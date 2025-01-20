package org.timowa.recipemanager.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.timowa.recipemanager.database.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
