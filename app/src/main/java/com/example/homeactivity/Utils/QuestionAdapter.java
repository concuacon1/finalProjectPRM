package com.example.homeactivity.Utils;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.Question;
import com.example.homeactivity.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private final List<Question> questionList;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.setData(question);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView question;
        private final Button optionA;
        private final Button optionB;
        private final Button optionC;
        private final Button optionD;
        private Button prevSelectedBtn;
        private final Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            question = itemView.findViewById(R.id.tv_question);
            question.setMovementMethod(new ScrollingMovementMethod());
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            prevSelectedBtn = null;
        }

        private void setData(Question q) {
            question.setText(q.getQuestion());
            optionA.setText(q.getOptionA());
            optionB.setText(q.getOptionB());
            optionC.setText(q.getOptionC());
            optionD.setText(q.getOptionD());

            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionA, q.getOptionA(), q);
                }
            });
            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionB, q.getOptionB(), q);
                }
            });
            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectOption(optionC, q.getOptionC(), q);
                }
            });
            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionD, q.getOptionD(), q);
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
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.unselected_button));
                    q.setSelectedAns(null);
                    prevSelectedBtn = null;
                } else {
                    prevSelectedBtn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.unselected_button));
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.selected_button));
                    q.setSelectedAns(chosenOption);
                    prevSelectedBtn = btn;
                }
            }
        }
    }


}
