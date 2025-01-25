package org.timowa.recipemanager.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.timowa.recipemanager.controller.ConsoleManager;
import org.timowa.recipemanager.database.entity.Ingredient;
import org.timowa.recipemanager.database.entity.Recipe;
import org.timowa.recipemanager.database.entity.User;
import org.timowa.recipemanager.database.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
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
        consoleManager.displayMessage("== Страница: " + pageable.getPageNumber()
                + " из " + recipes.getTotalPages());

        int input = 0;
        while (input != 4) {
            input = consoleManager.readIntInput("""
                
                1. Посмотреть рецепт
                2. Следующая страница
                3. Предыдущая страница
                4. Назад""");
            switch (input) {
                case 1:
                    readRecipe();
                    break;
                case 2:
                    pageable.next();
                    printPage(pageable, recipes);
                    break;
                case 3:
                    pageable.previous();
                    printPage(pageable, recipes);
                    break;
                case 4:
                    break;
            }
        }
    }

    private void printPage(PageRequest pageable, Page<Recipe> recipes) {
        recipes = recipeRepository.findAllBy(pageable);
        recipes.forEach(r -> consoleManager.displayMessage(r.getName() + " by "
                + r.getUser().getUsername()));
        consoleManager.displayMessage("== Страница: " + pageable.getPageNumber()
                + " из " + recipes.getTotalPages());
    }

    private void readRecipe() {
        String recipeName = consoleManager.readStringInput("Введите название рецепта, для просмотра");
        Optional<Recipe> maybeRecipe = recipeRepository.findByName(recipeName);
        if (maybeRecipe.isPresent()) {
            Recipe recipe = maybeRecipe.get();
            System.out.printf("""
                            --- %s by %s ---
                            Описание: %s
                            Ингредиенты: %s
                            Шаги приготовления: %s
                            Рейтинг: %s
                            --------------
                            """.formatted(recipe.getName(),
                    recipe.getUser().getUsername(),
                    recipe.getDescription(),
                    recipe.getIngredients(),
                    recipe.getSteps(),
                    recipe.getAvgRating()));
        }
    }
}
