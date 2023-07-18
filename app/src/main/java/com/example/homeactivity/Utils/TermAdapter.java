package com.example.homeactivity.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{

    private Context mContext;
    private List<Term> mListTerm;

    public TermAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void SetData(List<Term> list) {
        this.mListTerm = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_term,parent,false);
        return new TermAdapter.TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Term term = mListTerm.get(position);
        if (term == null) {
            return;
        }

        holder.tvTerm.setText(term.getTerm());
        holder.tvDefinition.setText(term.getDefinition());
    }

    @Override
    public int getItemCount() {
        if (mListTerm != null) {
            return mListTerm.size();
        }
        return 0;
    }

    public class TermViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTerm;
        private TextView tvDefinition;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTerm = itemView.findViewById(R.id.tv_term);
            tvDefinition = itemView.findViewById(R.id.tv_definition);
        }
    }

}
