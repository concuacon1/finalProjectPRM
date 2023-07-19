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

import com.example.homeactivity.Models.TestResult;
import com.example.homeactivity.R;
import com.example.homeactivity.Views.StudySetActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestHistoryAdapter extends RecyclerView.Adapter<TestHistoryAdapter.TestHistoryHolder> {
    List<TestResult> testResults;

    public TestHistoryAdapter(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    @NonNull
    @Override
    public TestHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_history, parent, false);

        return new TestHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHistoryHolder holder, int position) {
        holder.setData(testResults.get(position));
    }

    @Override
    public int getItemCount() {
        return testResults.size();
    }

    class TestHistoryHolder extends RecyclerView.ViewHolder {

        TextView tvHistory;
        Button btn;
        Context context;

        public TestHistoryHolder(@NonNull View itemView) {
            super(itemView);
            tvHistory = itemView.findViewById(R.id.tv_history);
            btn = itemView.findViewById(R.id.btn_retest);
            context = itemView.getContext();
        }

        public void setData(TestResult testResult) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(testResult.getTimeTest());
            tvHistory.setText("You've done " + testResult.getStudyTitle() + " at " + time);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudySetActivity.class);
                    intent.putExtra("studySetId", testResult.getStudySetId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
