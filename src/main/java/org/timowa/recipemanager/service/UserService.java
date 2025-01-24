package org.timowa.recipemanager.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.timowa.recipemanager.controller.ConsoleManager;
import org.timowa.recipemanager.database.entity.Ingredient;
import org.timowa.recipemanager.database.entity.Recipe;
import org.timowa.recipemanager.database.entity.User;
import org.timowa.recipemanager.database.repository.RecipeRepository;
import org.timowa.recipemanager.database.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
@Setter
public class UserService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ConsoleManager consoleManager;
    private User currentUser;

    public void initUser() {
        String username = consoleManager.readStringInput("\nОт имени какого пользователя вы хотите действовать? (username)");
        Optional<User> maybeUser = userRepository.findByUsername(username);

        if (maybeUser.isPresent()) {
            currentUser = maybeUser.get();
        } else {
            throw new IllegalArgumentException();
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

        User user = currentUser;

        userRepository.save(user);

        recipeRepository.save(recipe);
        consoleManager.displayMessage("\nРецепт успешно создан!");
    }
}