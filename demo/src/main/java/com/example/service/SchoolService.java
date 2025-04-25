package com.example.service;

import com.example.model.School;
import com.example.model.Student;
import com.example.model.Subject;

import java.util.List;
import java.util.Optional;

public class SchoolService {
    private School school;

    public SchoolService(School school) {
        this.school = school;
    }

    // Додати учня
    public void addStudent(Student student) {
        school.getStudents().add(student);
    }

    // Видалити учня
    public boolean removeStudent(String name) {
        return school.getStudents().removeIf(student -> student.getName().equalsIgnoreCase(name));
    }

    // Знайти учня
    public Optional<Student> findStudent(String name) {
        return school.getStudents().stream()
                .filter(student -> student.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Додати предмет
    public void addSubject(Subject subject) {
        school.getSubjects().add(subject);
    }

    // Видалити предмет
    public boolean removeSubject(String name) {
        return school.getSubjects().removeIf(subject -> subject.getName().equalsIgnoreCase(name));
    }

    // Знайти предмет
    public Optional<Subject> findSubject(String name) {
        return school.getSubjects().stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Додати оцінку учню за предмет
    public boolean addScore(String studentName, String subjectName, int score) {
        Optional<Student> studentOpt = findStudent(studentName);
        Optional<Subject> subjectOpt = findSubject(subjectName);

        if (studentOpt.isPresent() && subjectOpt.isPresent()) {
            studentOpt.get().getScores().put(subjectName, score);
            return true;
        }
        return false;
    }

    // Отримати всіх учнів
    public List<Student> getAllStudents() {
        return school.getStudents();
    }

    // Отримати всі предмети
    public List<Subject> getAllSubjects() {
        return school.getSubjects();
    }

    // Середній бал по школі
    public double getSchoolAverage() {
        List<Student> students = school.getStudents();
        if (students.isEmpty()) {
            return 0;
        }
        return students.stream()
                .mapToDouble(Student::getAverageScore)
                .average()
                .orElse(0);
    }
}
