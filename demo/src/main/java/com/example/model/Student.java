package com.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {
    private String name;
    private Map<String, Integer> scores;

    public Student() {
        scores = new HashMap<>();
    }

    public Student(String name) {
        this.name = name;
        this.scores = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @JsonIgnore
    public double getAverageScore() {
        if (scores.isEmpty()) {
            return 0;
        }
        return scores.values().stream().mapToInt(Integer::intValue).average().orElse(0);
    }
    
}
