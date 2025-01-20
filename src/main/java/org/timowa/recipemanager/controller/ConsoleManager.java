package org.timowa.recipemanager.controller;

import org.springframework.stereotype.Component;
import org.timowa.recipemanager.database.entity.Ingredient;

import java.util.*;

@Component
public class ConsoleManager {
    private final Scanner scanner;

    public ConsoleManager() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    // Вывод меню
    public int menu() {
        System.out.println("""
                \n==== Меню ====
                1. Добавить рецепт
                2. Просмотреть список рецептов
                3. Поиск рецепта
                0. Выход
                ================
                """);

        return readIntInput();
    }

    public int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException e) {
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
        List<String> whitelist = List.of("грамм", "милиграмм", "литр", "милилитр");
        int numberOfIngredient = 1;

        while (true) {
            System.out.printf("Ингредиент #%d\n".formatted(numberOfIngredient));
            System.out.println("Название");
            String name = scanner.nextLine();
            System.out.println("Количество");
            Integer count = Integer.parseInt(scanner.nextLine());
            System.out.println("Единица измерения");
            String unit = scanner.nextLine();
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
            System.out.println("Продолжить? (Д/Н)");
            String isAddIngredient = scanner.nextLine();
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
