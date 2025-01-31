package org.timowa.recipemanager.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.timowa.recipemanager.controller.ConsoleManager;
import org.timowa.recipemanager.database.entity.User;
import org.timowa.recipemanager.database.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
@Setter
public class UserService {
    private final UserRepository userRepository;
    private final RecipeService recipeService;
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

    public void menu() {
        while (true) {
            int menuResult = consoleManager.menu();
            if (menuResult == 0) break;
            switch (menuResult) {
                case 1:
                    recipeService.addRecipe(currentUser);
                    break;
                case 2:
                    recipeService.getRecipes(currentUser);
                    break;
                default:
                    consoleManager.displayMessage("Неизвесный аргумент, повторите попытку");
            }

        }
    }
}