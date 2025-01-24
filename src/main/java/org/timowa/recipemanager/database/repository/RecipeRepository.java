package org.timowa.recipemanager.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.timowa.recipemanager.database.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAllBy(Pageable pageable);
}
