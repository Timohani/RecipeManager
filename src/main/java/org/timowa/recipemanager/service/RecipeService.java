package org.timowa.recipemanager.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.timowa.recipemanager.controller.ConsoleManager;
import org.timowa.recipemanager.database.entity.Ingredient;
import org.timowa.recipemanager.database.entity.Recipe;
import org.timowa.recipemanager.database.entity.User;
import org.timowa.recipemanager.database.repository.RecipeRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Getter
public class RecipeService {
    private final ConsoleManager consoleManager;
    private final RecipeRepository recipeRepository;

    public void addRecipe(User currentUser) {
        String name = consoleManager.readStringInput("Введите уникальное название рецепта:");
        String description = consoleManager.readStringInput("Введите описание рецепта:");
        Set<Ingredient> ingredients = consoleManager.readIngredientInput("Введите ингредиенты рецепта: ");
        List<String> steps = consoleManager.readListInput("Введите шаги приготовления рецепта:" +
                "\n(По окончанию введите q)");

        Recipe recipe = Recipe.builder()
                .name(name)
                .description(description)
                .steps(steps)
                .ingredients(ingredients)
                .user(currentUser)
                .build();

        recipeRepository.save(recipe);
        consoleManager.displayMessage("\nРецепт успешно создан!");
    }

    public void getRecipes() {
        var pageable = PageRequest.of(0, 10, Sort.by("ratings.rate"));
        var recipes = recipeRepository.findAllBy(pageable);
        consoleManager.displayMessage("----- Рецепты -----");

        recipes.forEach(r -> consoleManager.displayMessage(r.getName() + " by "
                + r.getUser().getUsername()));
    }
}
