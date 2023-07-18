package com.example.homeactivity.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;

import java.util.List;

public class MainStartAdapter extends RecyclerView.Adapter<MainStartAdapter.TermViewHolder >{
    private List<StudySet> studySetList;

    public MainStartAdapter(List<StudySet> list) {
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
    }

    @Override
    public int getItemCount() {
        return studySetList.size();
    }
    public class TermViewHolder extends RecyclerView.ViewHolder {

        private TextView quiz_name;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);

            quiz_name = itemView.findViewById(R.id.quiz_name);
        }
    }
}
