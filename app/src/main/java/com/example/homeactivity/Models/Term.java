package com.example.homeactivity.Models;

import java.io.Serializable;
import java.util.Objects;

public class Term implements Serializable {
    private String id;
    private String term;
    private String definition;
    private String studySetId;

    public Term() {
    }

    public Term(String id, String term, String definition, String studySetId) {
        this.id = id;
        this.term = term;
        this.definition = definition;
        this.studySetId = studySetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(String studySetId) {
        this.studySetId = studySetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return id.equals(term.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
