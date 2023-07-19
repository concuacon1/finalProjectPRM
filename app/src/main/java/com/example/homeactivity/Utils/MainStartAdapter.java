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
import com.example.homeactivity.R;
import com.example.homeactivity.Views.StudySetActivity;

import java.util.List;

public class MainStartAdapter extends RecyclerView.Adapter<MainStartAdapter.TermViewHolder> {
    private final Context context;
    private final List<StudySet> studySetList;

    public MainStartAdapter(List<StudySet> list, Context context) {
        this.studySetList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MainStartAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_set_main, parent, false);
        return new MainStartAdapter.TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainStartAdapter.TermViewHolder holder, int position) {
        StudySet studySet = studySetList.get(position);
        if (studySet == null) {
            return;
        }
        holder.quiz_name.setText(studySet.getTitle());

        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudySetActivity.class);
                intent.putExtra("studySetId", studySet.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studySetList.size();
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView quiz_name;
        private final Button startButton;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);

            quiz_name = itemView.findViewById(R.id.quiz_name);
            startButton = itemView.findViewById(R.id.btnStart);
        }
    }
}
