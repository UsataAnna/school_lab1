package com.example;

import com.example.model.*;
import com.example.service.*;

import java.io.IOException;
import java.util.Scanner;

public class SchoolConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_PATH = "school_data.json";

    public static void main(String[] args) {
        
        School school = new School("My School");
        SchoolService schoolService = new SchoolService(school);
        DataManager dataManager = new DataManager();

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addStudent(schoolService);
                case "2" -> addSubject(schoolService);
                case "3" -> addScore(schoolService);
                case "4" -> printAllStudents(schoolService);
                case "5" -> printAllSubjects(schoolService);
                case "6" -> printSchoolAverage(schoolService);
                case "7" -> exportSchool(dataManager, school);
                case "8" -> {
                    School importedSchool = importSchool(dataManager);
                    if (importedSchool != null) {
                        school = importedSchool;
                        schoolService = new SchoolService(school);
                        System.out.println("Школу успішно завантажено.");
                    } else {
                        System.out.println("Помилка завантаження.");
                    }
                }           
                case "0" -> {
                    System.out.println("Вихід з програми.");
                    running = false;
                }
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n====== МЕНЮ ======");
        System.out.println("1. Додати учня");
        System.out.println("2. Додати предмет");
        System.out.println("3. Додати оцінку");
        System.out.println("4. Вивести всіх учнів");
        System.out.println("5. Вивести всі предмети");
        System.out.println("6. Вивести середній бал по школі");
        System.out.println("7. Зберегти школу в JSON");
        System.out.println("8. Завантажити школу з JSON");
        System.out.println("0. Вийти");
        System.out.print("Ваш вибір: ");
    }

    private static void addStudent(SchoolService service) {
        System.out.print("Введіть ім'я учня: ");
        String name = scanner.nextLine().trim();
    
        if (name.isEmpty() || !name.matches("[a-zA-Zа-яА-ЯіІїЇєЄґҐ' ]+")) {
            System.out.println("Некоректне ім'я. Спробуйте ще раз.");
            return;
        }
    
        service.addStudent(new Student(name));
        System.out.println("Учня додано.");
    }

    private static void addSubject(SchoolService service) {
    System.out.print("Введіть назву предмета: ");
    String name = scanner.nextLine().trim();

    if (name.isEmpty() || !name.matches("[a-zA-Zа-яА-ЯіІїЇєЄґҐ' ]+")) {
        System.out.println("Некоректна назва предмета. Спробуйте ще раз.");
        return;
    }

    System.out.print("Введіть максимальний бал: ");
    String input = scanner.nextLine().trim();

    int maxScore;
    try {
        maxScore = Integer.parseInt(input);
        if (maxScore <= 0 || maxScore > 100) {
            System.out.println("Максимальний бал має бути від 1 до 100.");
            return;
        }
    } catch (NumberFormatException e) {
        System.out.println("Некоректне число. Спробуйте ще раз.");
        return;
    }

    service.addSubject(new Subject(name, maxScore));
    System.out.println("Предмет додано.");
}


private static void addScore(SchoolService service) {
    System.out.print("Введіть ім'я учня: ");
    String studentName = scanner.nextLine();
    System.out.print("Введіть назву предмета: ");
    String subjectName = scanner.nextLine();

    int score;
    try {
        System.out.print("Введіть оцінку: ");
        score = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Некоректне значення для оцінки. Спробуйте ще раз.");
        return;
    }

    if (score < 0 || score > 100) {
        System.out.println("Оцінка має бути від 0 до 100.");
        return;
    }

    if (service.addScore(studentName, subjectName, score)) {
        System.out.println("Оцінку додано.");
    } else {
        System.out.println("Не вдалося додати оцінку. Перевірте ім'я учня та назву предмета.");
    }
}


    private static void printAllStudents(SchoolService service) {
        System.out.println("\nСписок учнів:");
        if (service.getAllStudents().isEmpty()) {
            System.out.println("Учнів немає.");
        } else {
            service.getAllStudents().forEach(student -> {
                System.out.println("- " + student.getName() + " (середній бал: " + student.getAverageScore() + ")");
            });
        }
    }

    private static void printAllSubjects(SchoolService service) {
        System.out.println("\nСписок предметів:");
        if (service.getAllSubjects().isEmpty()) {
            System.out.println("Предметів немає.");
        } else {
            service.getAllSubjects().forEach(subject -> {
                System.out.println("- " + subject.getName() + " (макс. бал: " + subject.getMaxScore() + ", складний: " + subject.isDifficult() + ")");
            });
        }
    }

    private static void printSchoolAverage(SchoolService service) {
        double average = service.getSchoolAverage();
        System.out.println("Середній бал по школі: " + average);
    }

    private static void exportSchool(DataManager dataManager, School school) {
        try {
            dataManager.exportToJson(school, FILE_PATH);
            System.out.println("Дані збережено у файл " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    private static School importSchool(DataManager dataManager) {
        try {
            String filePath = "school_data.json"; // просто беремо файл з папки проєкту
            return dataManager.importFromJson(filePath);
        } catch (IOException e) {
            System.out.println("Помилка завантаження: " + e.getMessage());
            return null;
        }
    }
    
}
