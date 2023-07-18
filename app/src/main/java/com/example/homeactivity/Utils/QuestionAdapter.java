package com.example.homeactivity.Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
import com.example.homeactivity.Models.Questions;
import com.example.homeactivity.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Questions> questionsList;

    public QuestionAdapter(List<Questions> questionsList) {
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private Button optionA, optionB, optionC, optionD, prevSelectedBtn;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            question = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            prevSelectedBtn = null;
            prevSelectedBtn = null;
        }

        private void setData(final int position) {
            ques.setText(questionsList.get(position).getQuestion());
            optionA.setText(questionsList.get(position).getOptionA());
            optionB.setText(questionsList.get(position).getOptionB());
            optionC.setText(questionsList.get(position).getOptionC());
            optionD.setText(questionsList.get(position).getOptionD());

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionA, 1, position);
                }
            });
            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionB, 2, position);
                }
            });
            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionC, 3, position);
                }
            });
            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionD, 4, position);
                }
            });
        }

        private void selectOption(Button btn, String chosenOption, Question q) {

            if (prevSelectedBtn == null) {
                // Set the selected button background
                btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.selected_button));
                q.setSelectedAns(chosenOption);
                prevSelectedBtn = btn;

            } else {
                if (prevSelectedBtn.getId() == btn.getId()) {
                    Log.i("prevBtn", "c: " + prevSelectedBtn);
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.unselected_button));
                    q.setSelectedAns(null);
                    prevSelectedBtn = null;
                } else {
                    Log.i("prevBtn", "d: " + prevSelectedBtn);
                    prevSelectedBtn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.unselected_button));
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.selected_button));
                    q.setSelectedAns(chosenOption);
                    prevSelectedBtn = btn;
                }
            }
        }
    }


}
