package com.aston.controller;

import com.aston.entity.User;
import com.aston.service.UserService;
import com.aston.service.UserServiceImpl;

import java.util.Scanner;

public class UserController {

    private final UserService service = new UserServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {

        while (true) {
            System.out.println("""
                === Меню ===
                1. Создать пользователя
                2. Найти всех пользователей
                3. Найти через id
                4. Обновить данные пользователя
                5. Удалить пользователя
                0. Выход
                """);

            System.out.print("Выберите пункт: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Введите число!");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> create();
                case 2 -> service.getAll().forEach(System.out::println);
                case 3 -> findById();
                case 4 -> update();
                case 5 -> delete();
                case 0 -> {
                    System.out.println("Пока!");
                    return;
                }
                default -> System.out.println("Неверный пункт меню");
            }
        }
    }

    private void create() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();

        System.out.print("Почта: ");
        String email = scanner.nextLine();

        System.out.print("Возраст: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Возраст должен быть числом");
            scanner.nextLine();
            return;
        }

        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(name, email, age);

        try {
            service.create(user);
            System.out.println("Пользователь создан");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findById() {
        System.out.print("ID: ");

        if (!scanner.hasNextLong()) {
            System.out.println("ID должен быть числом");
            scanner.nextLine();
            return;
        }

        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = service.getById(id);

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    private void update() {
        System.out.print("ID: ");

        if (!scanner.hasNextLong()) {
            System.out.println("ID должен быть числом");
            scanner.nextLine();
            return;
        }

        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Новое имя: ");
        String name = scanner.nextLine();

        System.out.print("Новая почта: ");
        String email = scanner.nextLine();

        System.out.print("Новый возраст: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Возраст должен быть числом");
            scanner.nextLine();
            return;
        }

        int age = scanner.nextInt();
        scanner.nextLine();

        User user = service.getById(id);

        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);

            service.update(user);
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    private void delete() {
        System.out.print("ID: ");

        if (!scanner.hasNextLong()) {
            System.out.println("ID должен быть числом");
            scanner.nextLine();
            return;
        }

        Long id = scanner.nextLong();
        scanner.nextLine();

        service.delete(id);
    }
}