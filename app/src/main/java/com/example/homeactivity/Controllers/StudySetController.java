package com.example.homeactivity.Controllers;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.Views.CreateStudySetActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudySetController {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void CreateStudySet(List<Term> termList) {
        for (Term t: termList
        ) {
            String studySetId = "unknow";
            String term = t.getTerm();
            String definition = t.getDefinition();

            Map<String, Object> terms = new HashMap<>();
            terms.put("study_set_id", studySetId);
            terms.put("term", term);
            terms.put("definition", definition);

            db.collection("ProjectQuizlet").document("Term").set(terms)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }
}
