package com.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class School {

    private String name;
    private List<Student> students;
    private List<Subject> subjects;

    public School() {
        this.students = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }
    

    public School(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Student> getStudents() { return students; }
    public List<Subject> getSubjects() { return subjects; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof School)) return false;
        School school = (School) o;
        return Objects.equals(name, school.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "School{" + "name='" + name + '\'' + ", students=" + students + ", subjects=" + subjects + '}';
    }

   @JsonIgnore
    public int getStudentCount() {
        return students.size();
    }
    
}
