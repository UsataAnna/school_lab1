package com.example.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Subject {
    private String name;
    private int maxScore;

    public Subject() {
    }
    

    public Subject(String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return maxScore == subject.maxScore && Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxScore);
    }

    @Override
    public String toString() {
        return "Subject{" + "name='" + name + '\'' + ", maxScore=" + maxScore + '}';
    }

    // Бізнес-логіка: чи є предмет складним (макс. бал >= 90)
    @JsonIgnore
    public boolean isDifficult() {
        return maxScore >= 90;
    }
}
