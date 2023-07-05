package com.example.homeactivity.Controllers;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.Services.StudySetService;
import com.example.homeactivity.Services.TermService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StudySetController {

    private final StudySetService studySetService;
    private final TermService termService;

    public StudySetController() {
        studySetService = new StudySetService();
        termService = new TermService();
    }

    public Error createStudySet(StudySet studySet, List<Term> termList) {
        try {
            String studySetId = studySetService.createStudySet(studySet);
            for (Term t: termList
            ) {
                t.setStudySetId(studySetId);
            }
            termService.createTerms(termList);
        } catch (Exception e) {
            return new Error(e);
        }
        return null;
    }

    public StudySet getStudySet(String studySetId) {
        try {
            StudySet studySet = new StudySet();
            studySet = studySetService.findStudySet(studySetId,studySet1 -> {
//                studySet.setTitle(studySet1.getTitle());
//                studySet.setId(studySet1.getUserId());
//                studySet.setDescription(studySet1.getDescription());
//                studySet.setUserId(studySet1.getUserId());
//                studySet.setCreatedAt(studySet1.getCreatedAt());
//                studySet.setUpdatedAt(studySet1.getUpdatedAt());
//                studySet.setAvailable(studySet1.isAvailable());
//                studySet.setCategories(studySet1.getCategories());
            });
            return studySet;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Term> getTerms(String studySetId) {
        List<Term> termList = new ArrayList<>();
        try {
            termService.listAllTerms(studySetId, termList::addAll);
        } catch (Exception e) {
            return null;
        }
        return termList;
    }
}
