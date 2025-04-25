package com.example.service;

import com.example.model.School;
import com.example.model.Student;
import com.example.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SchoolServiceTest {
    private SchoolService schoolService;
    private School school;

    @BeforeEach
    void setUp() {
        school = new School("Test School");
        schoolService = new SchoolService(school);
    }

    @Test
    void testAddAndFindStudent() {
        Student student = new Student("Anna");
        schoolService.addStudent(student);
        Optional<Student> found = schoolService.findStudent("Anna");
        assertTrue(found.isPresent());
        assertEquals("Anna", found.get().getName());
    }

    @Test
    void testRemoveStudent() {
        Student student = new Student("Anna");
        schoolService.addStudent(student);
        assertTrue(schoolService.removeStudent("Anna"));
        assertFalse(schoolService.findStudent("Anna").isPresent());
    }

    @Test
    void testAddAndFindSubject() {
        Subject math = new Subject("Math", 100);
        schoolService.addSubject(math);
        Optional<Subject> found = schoolService.findSubject("Math");
        assertTrue(found.isPresent());
        assertEquals(100, found.get().getMaxScore());
    }

    @Test
    void testRemoveSubject() {
        Subject math = new Subject("Math", 100);
        schoolService.addSubject(math);
        assertTrue(schoolService.removeSubject("Math"));
        assertFalse(schoolService.findSubject("Math").isPresent());
    }

    @Test
    void testAddScore() {
        Student anna = new Student("Anna");
        Subject math = new Subject("Math", 100);
        schoolService.addStudent(anna);
        schoolService.addSubject(math);
        assertTrue(schoolService.addScore("Anna", "Math", 95));
        assertEquals(95, anna.getScores().get(math.getName()));
    }


    @Test
    void testAddScoreNonExistingStudent() {
        Subject math = new Subject("Math", 100);
        schoolService.addSubject(math);
        assertFalse(schoolService.addScore("NonExisting", "Math", 90));
    }

    @Test
    void testAddScoreNonExistingSubject() {
        Student anna = new Student("Anna");
        schoolService.addStudent(anna);
        assertFalse(schoolService.addScore("Anna", "Physics", 90));
    }
 
    @Test
    void testGetSchoolAverage() {
        Student anna = new Student("Anna");
        Student bob = new Student("Bob");
        Subject math = new Subject("Math", 100);
        Subject physics = new Subject("Physics", 100);

        schoolService.addStudent(anna);
        schoolService.addStudent(bob);
        schoolService.addSubject(math);
        schoolService.addSubject(physics);

        schoolService.addScore("Anna", "Math", 90);
        schoolService.addScore("Anna", "Physics", 70);
        schoolService.addScore("Bob", "Math", 80);
        schoolService.addScore("Bob", "Physics", 60);

        double expectedAverage = ( (90 + 70)/2.0 + (80 + 60)/2.0 ) / 2.0;
        assertEquals(expectedAverage, schoolService.getSchoolAverage(), 0.001);
    }

    @Test
    void testEmptySchoolAverage() {
        assertEquals(0, schoolService.getSchoolAverage(), 0.001);
    }

    @Test
    void testAddNegativeScore() {
        Student anna = new Student("Anna");
        Subject math = new Subject("Math", 100);
        schoolService.addStudent(anna);
        schoolService.addSubject(math);

        assertTrue(schoolService.addScore("Anna", "Math", -10));
        assertEquals(-10, anna.getScores().get("Math"));
    }

    @Test
    void testAddStudentWithNullName() {
        Student student = new Student(null);
        schoolService.addStudent(student);
        assertTrue(schoolService.getAllStudents().contains(student));
    }

    @Test
    void testRemoveNonExistingStudent() {
        assertFalse(schoolService.removeStudent("Ghost"));
    }

}
