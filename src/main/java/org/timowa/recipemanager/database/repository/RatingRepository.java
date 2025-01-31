package org.timowa.recipemanager.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.timowa.recipemanager.database.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
