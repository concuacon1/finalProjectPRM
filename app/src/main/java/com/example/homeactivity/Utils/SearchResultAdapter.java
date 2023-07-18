package com.example.homeactivity.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.StudySet;
import com.example.homeactivity.R;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private List<StudySet> studySetList;

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
        StudySet s =  studySetList.get(position);
        holder.tvSearchResult.setText(s.getTitle());
    }

    @Override
    public int getItemCount() {
        return studySetList.size();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder {
        private TextView tvSearchResult;


        public SearchResultHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchResult = itemView.findViewById(R.id.tv_search_result);
        }
    }
}
