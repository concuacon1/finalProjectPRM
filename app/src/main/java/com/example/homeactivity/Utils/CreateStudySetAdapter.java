package com.example.homeactivity.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;

import java.util.List;


public class CreateStudySetAdapter extends RecyclerView.Adapter<CreateStudySetAdapter.CreateStudySetViewHolder>{

    private Context mContext;
    private List<Term> mListTerm;

    public CreateStudySetAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Term> list) {
        this.mListTerm = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreateStudySetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_create_edit_term,parent,false);
        return new CreateStudySetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateStudySetViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Term term = mListTerm.get(position);
        if (term == null) {
            return;
        }

//        holder.tvTermNumber.setText("1");
        holder.tvTerm.setText("TERM");
        holder.tvDefinition.setText("DEFINITION");

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListTerm.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mListTerm.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListTerm != null) {
            return mListTerm.size();
        }
        return 0;
    }

    public class CreateStudySetViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTermNumber;
        private EditText etTerm;
        private TextView tvTerm;
        private EditText etDefinition;
        private TextView tvDefinition;
        private TextView tvSpace;

        private ImageButton deleteButton;

        public CreateStudySetViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTermNumber = itemView.findViewById(R.id.tv_term_number);
            etTerm = itemView.findViewById(R.id.et_term);
            tvTerm = itemView.findViewById(R.id.tv_term);
            etDefinition = itemView.findViewById(R.id.et_definition);
            tvDefinition = itemView.findViewById(R.id.tv_definition);
            tvSpace = itemView.findViewById(R.id.tv_space);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
