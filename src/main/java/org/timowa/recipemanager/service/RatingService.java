package org.timowa.recipemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.timowa.recipemanager.database.entity.Rating;
import org.timowa.recipemanager.database.entity.Recipe;
import org.timowa.recipemanager.database.entity.User;
import org.timowa.recipemanager.database.repository.RatingRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {
    private final RatingRepository ratingRepository;

    public void addRating(User user, Recipe recipe, int rate) {
        ratingRepository.save(Rating.builder()
                        .rate(rate)
                        .recipe(recipe)
                        .user(user)
                .build());
    }

    public boolean validateRate(int rate) {
        return rate >= 1 && rate <= 5;
    }
}
