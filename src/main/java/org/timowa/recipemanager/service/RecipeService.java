package org.timowa.recipemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.timowa.recipemanager.controller.ConsoleManager;
import org.timowa.recipemanager.database.entity.Ingredient;
import org.timowa.recipemanager.database.entity.Recipe;
import org.timowa.recipemanager.database.repository.RecipeRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final ConsoleManager consoleManager;
    private final RecipeRepository recipeRepository;

    public void closeScanner() {
        consoleManager.closeScanner();
    }

    public void menu() {
        if (consoleManager.menu() == 1) {
            addRecipe();
        } else {
            System.out.println("Неизвесный аргумент, повторите попытку");
            menu();
        }
    }

    public void addRecipe() {
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
                .build();
        recipeRepository.save(recipe);
        consoleManager.displayMessage("\nРецепт успешно создан!");
    }
}
