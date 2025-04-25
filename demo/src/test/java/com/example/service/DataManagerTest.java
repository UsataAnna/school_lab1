package com.example.service;

import com.example.model.School;
import com.example.model.Student;
import com.example.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    private DataManager dataManager;
    private School school;
    private final String testFilePath = "test_school.json";

    @BeforeEach
    void setUp() {
        dataManager = new DataManager();
        school = new School("Test School");

        Student anna = new Student("Anna");
        Student bob = new Student("Bob");
        Subject math = new Subject("Math", 100);

        school.getStudents().add(anna);
        school.getStudents().add(bob);
        school.getSubjects().add(math);

        anna.getScores().put(math.getName(), 95);
        bob.getScores().put(math.getName(), 85);
    }

    @Test
    void testExportToJson() {
        assertDoesNotThrow(() -> dataManager.exportToJson(school, testFilePath));
        File file = new File(testFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void testImportFromJson() throws IOException {
        dataManager.exportToJson(school, testFilePath);
        School loadedSchool = dataManager.importFromJson(testFilePath);

        assertNotNull(loadedSchool);
        assertEquals("Test School", loadedSchool.getName());
        assertEquals(2, loadedSchool.getStudents().size());
        assertEquals(1, loadedSchool.getSubjects().size());

        Student loadedStudent = loadedSchool.getStudents().get(0);
        assertTrue(loadedStudent.getScores().size() > 0);
    }

    @Test
    void testImportFromNonExistingFile() {
        assertThrows(IOException.class, () -> dataManager.importFromJson("non_existing.json"));
    }
    
}
