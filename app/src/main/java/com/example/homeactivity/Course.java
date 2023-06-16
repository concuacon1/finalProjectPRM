package com.example.homeactivity;

import java.util.List;

public class Course {
    int id;
    String name;
    List<Integer> quizId;

    public Course() {
    }

    public Course(int id, String name, List<Integer> quizId) {
        this.id = id;
        this.name = name;
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getQuizId() {
        return quizId;
    }

    public void setQuizId(List<Integer> quizId) {
        this.quizId = quizId;
    }
}
