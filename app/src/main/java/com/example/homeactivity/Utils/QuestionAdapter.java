package com.example.homeactivity.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Controllers.StudySetController;
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
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ques;
        private Button optionA, optionB, optionC, optionD, prevSelectedBtn;
        private StudySetController studySetController;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            prevSelectedBtn = null;
        }

        private void setData(final int position) {
            ques.setText(questionList.get(position).getQuestion());
            optionA.setText(questionList.get(position).getOptionA());
            optionB.setText(questionList.get(position).getOptionB());
            optionC.setText(questionList.get(position).getOptionC());
            optionD.setText(questionList.get(position).getOptionD());

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

        private void selectOption(Button btn, int optionNum, int quesID) {
            if (prevSelectedBtn == null) {
                btn.setBackgroundResource(R.drawable.selected_button);
                questionList.get(quesID).setSelectedAns(optionNum);
                prevSelectedBtn = btn;
            } else {
                if (prevSelectedBtn.getId() == btn.getId()) {
                    btn.setBackgroundResource(R.drawable.unselected_button);
                    questionList.get(quesID).setSelectedAns(-1);
                    prevSelectedBtn = null;
                } else {
                    prevSelectedBtn.setBackgroundResource(R.drawable.unselected_button);
                    btn.setBackgroundResource(R.drawable.selected_button);
                    questionList.get(quesID).setSelectedAns(optionNum);
                    prevSelectedBtn = btn;
                }
            }
        }
    }


}
