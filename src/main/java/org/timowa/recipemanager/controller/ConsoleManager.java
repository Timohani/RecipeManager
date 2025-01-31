package org.timowa.recipemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.timowa.recipemanager.database.entity.Ingredient;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ConsoleManager {
    private final Scanner scanner;

    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Вывод меню
    public int menu() {
        System.out.println("""
                \n==== Меню ====
                1. Добавить рецепт
                2. Просмотреть список рецептов
                0. Выход
                ================
                """);

        return readIntInput("");
    }

    public int readIntInput(String message) {
        while (true) {
            try {
                System.out.println(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод, попробуйте ввести целое число: ");
                scanner.nextLine();
            }
        }
    }

    public String readStringInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public List<String> readListInput(String message) {
        System.out.println(message);
        List<String> list = new LinkedList<>();
        String line = scanner.nextLine();

        while (!line.equals("q")) {
            list.add(line);
            line = scanner.nextLine();
        }
        return list;
    }

    public Set<Ingredient> readIngredientInput(String message) {
        System.out.println(message);
        Set<Ingredient> set = new HashSet<>();
        List<String> whitelist = List.of("грам", "милиграмм", "литр", "милилитр", "штук");
        int numberOfIngredient = 1;

        while (true) {
            System.out.printf("Ингредиент #%d\n".formatted(numberOfIngredient));

            String name = readStringInput("Название");
            Integer count = readIntInput("Количество");
            String unit = readStringInput("Единица измерения");

            if (whitelist.contains(unit)) {
                set.add(Ingredient.builder()
                        .name(name.toLowerCase())
                        .count(count)
                        .unit(unit)
                        .build());
            } else {
                System.out.printf("Неверный аргумент '%s', повторите попытку сначала\n".formatted(unit));
                continue;
            }
            String isAddIngredient = readStringInput("Продолжить? (Д/Н)");
            if (isAddIngredient.equals("Д") || isAddIngredient.equals("Н")) {
                if (isAddIngredient.equals("Н")) {
                    break;
                }
            } else {
                System.out.printf("Неверный аргумент '%s', повторите попытку сначала\n".formatted(isAddIngredient));
                continue;
            }
            numberOfIngredient++;
        }
        return set;
    }

    public void closeScanner() {
        scanner.close();
    }
}
