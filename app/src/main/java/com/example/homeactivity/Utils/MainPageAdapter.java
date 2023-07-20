package com.example.homeactivity.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.R;
import com.example.homeactivity.Views.CreateStudySetActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.MainPageHolder> {

    private List<StudySet> studySets;

    public MainPageAdapter(List<StudySet> studySets) {
        this.studySets = studySets;
    }

    @NonNull
    @Override
    public MainPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_studyset, parent, false);
        return new MainPageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPageHolder holder, int position) {
        StudySet studySet = studySets.get(position);
        holder.SetData(studySet);

    }

    @Override
    public int getItemCount() {
        return studySets.size();
    }

    public class MainPageHolder extends RecyclerView.ViewHolder{

        TextView tvNoParticipants, tvTitle;
        Button btnJoinNow;
        Context context;

        public MainPageHolder(@NonNull View itemView) {
            super(itemView);
            tvNoParticipants = itemView.findViewById(R.id.tv_No_Participants);
            tvTitle = itemView.findViewById(R.id.tv_title_main);
            btnJoinNow = itemView.findViewById(R.id.btn_join_now);
            context = itemView.getContext();
        }

        public void SetData (StudySet studySet) {
            tvNoParticipants.setText(String.valueOf(studySet.getNumberOfParticipants()));
            tvTitle.setText(studySet.getTitle());
            btnJoinNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreateStudySetActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
