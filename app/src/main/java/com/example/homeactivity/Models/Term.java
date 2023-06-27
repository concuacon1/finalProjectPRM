package com.example.homeactivity.Models;

public class Term {
    private int id;
    private int studySet;
    private String term;
    private String definition;

    public Term() {
    }

    public Term(int id, int studySet, String term, String definition) {
        this.id = id;
        this.studySet = studySet;
        this.term = term;
        this.definition = definition;
    }

    public Term(int id, String term, String definition) {
        this.id = id;
        this.term = term;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudySet() {
        return studySet;
    }

    public void setStudySet(int studySet) {
        this.studySet = studySet;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
