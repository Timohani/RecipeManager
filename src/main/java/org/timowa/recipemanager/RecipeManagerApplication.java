package org.timowa.recipemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.timowa.recipemanager.service.RecipeService;
import org.timowa.recipemanager.service.UserService;

@SpringBootApplication
public class RecipeManagerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RecipeManagerApplication.class, args);

        RecipeService recipeService = applicationContext.getBean(RecipeService.class);
        UserService userService = applicationContext.getBean(UserService.class);

        System.out.println(userService.getCurrentUser().getRole());
        userService.initUser();

        recipeService.menu();

        recipeService.getConsoleManager().closeScanner();
    }

}
