package com.example.homeactivity.Utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.Question;
import com.example.homeactivity.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Question> questionList;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.question_item_layout, viewGroup, false);
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
        private TextView question;
        private Button optionA, optionB, optionC, optionD;;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
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
                    Log.i("Btn", "BTN A");
                    selectOption(optionA, q.getOptionA(), q);
                }
            });
            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Btn", "BTN B");
                    selectOption(optionB, q.getOptionB(), q);
                }
            });
            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("Btn", "BTN C");
                    selectOption(optionC, q.getOptionC(), q);
                }
            });
            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Btn", "BTN D");
                    selectOption(optionD, q.getOptionD(), q);
                }
            });
        }

        private void selectOption(Button btn, String chosenOption, Question q) {
            // Set the selected button background
            btn.setBackgroundResource(R.drawable.selected_button);
            q.setSelectedAns(chosenOption);

            // Loop through all option buttons and set unselected background for non-selected options
            for (Button optionButton : new Button[]{optionA, optionB, optionC, optionD}) {
                if (optionButton != btn) {
                    optionButton.setBackgroundResource(R.drawable.unselected_button);
                }
            }
        }
    }


}
