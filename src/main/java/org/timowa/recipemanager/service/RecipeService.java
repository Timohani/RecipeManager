package org.timowa.recipemanager.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.timowa.recipemanager.controller.ConsoleManager;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
public class RecipeService {
    private final ConsoleManager consoleManager;
    private final UserService userService;

    public void menu() {
        while (true) {
            int menuResult = consoleManager.menu();
            if (menuResult == 0) break;
            switch (menuResult) {
                case 1:
                    userService.addRecipe();
                default:
                    consoleManager.displayMessage("Неизвесный аргумент, повторите попытку");
            }

        }
    }
}
