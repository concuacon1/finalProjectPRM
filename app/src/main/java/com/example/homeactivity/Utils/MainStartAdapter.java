package com.example.homeactivity.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Views.LoginActivity;
import com.example.homeactivity.Views.MainStartActivity;
import com.example.homeactivity.Views.StudySetActivity;

import java.util.List;

public class MainStartAdapter extends RecyclerView.Adapter<MainStartAdapter.TermViewHolder >{
    private List<StudySet> studySetList;
    private Context mContext;

    public MainStartAdapter(List<StudySet> list, Context mContext) {
        this.mContext = mContext;
        this.studySetList = list;
    }

    @NonNull
    @Override
    public MainStartAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item,parent,false);
        return new MainStartAdapter.TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainStartAdapter.TermViewHolder holder, int position) {
        StudySet studySet = studySetList.get(position);
        if (studySet == null) {
            return;
        }
        holder.quiz_name.setText(studySet.getTitle());
        holder.view_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickStudySet(studySet);
            }
        });
    }
    private void OnClickStudySet(StudySet studySet) {
        Intent intent = new Intent(mContext, StudySetActivity.class);
        intent.putExtra("ID",studySet.getId());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return studySetList.size();
    }
    public class TermViewHolder extends RecyclerView.ViewHolder {

        private TextView quiz_name;
        private Button view_quiz;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            quiz_name = itemView.findViewById(R.id.quiz_name);
            view_quiz = itemView.findViewById(R.id.button);
        }
    }
}
