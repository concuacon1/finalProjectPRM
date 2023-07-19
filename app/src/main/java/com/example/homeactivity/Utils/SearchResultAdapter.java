package com.example.homeactivity.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.R;
import com.example.homeactivity.Views.StudySetActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private final List<StudySet> studySetList;

    public SearchResultAdapter(List<StudySet> studySetList) {
        this.studySetList = studySetList;
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);

        return new SearchResultHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
        if (studySetList != null && studySetList.size() > 0) {
            StudySet s = studySetList.get(position);

            holder.setData(s);

        }
    }

    @Override
    public int getItemCount() {
        return studySetList.size();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvSearchResult;
        private final TextView tvDate;

        private StudySet s = new StudySet();
        private final Context context;

        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvSearchResult = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_created_date);
            itemView.setOnClickListener(this);
        }

        public void setData(StudySet s) {
            this.s = s;
            tvSearchResult.setText(s.getTitle());
            if (s.getCreatedAt() != null) {
                Date date = s.getCreatedAt().toDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("d, MMM yyyy", Locale.ENGLISH);
                tvDate.setText(dateFormat.format(date));
            } else {
                tvDate.setText("Unknown");
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, StudySetActivity.class);
            intent.putExtra("studySetId", s.getId());
            context.startActivity(intent);
        }
    }
}
