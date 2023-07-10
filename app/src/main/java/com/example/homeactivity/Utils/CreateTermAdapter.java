package com.example.homeactivity.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;


public class CreateTermAdapter extends RecyclerView.Adapter<CreateTermAdapter.CreateStudySetViewHolder>{

    private Context mContext;
    private List<Term> mListTerm;

    public CreateTermAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void SetData(List<Term> list) {
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

        holder.tvTermNumber.setText(String.valueOf(position+1));
        holder.tvTerm.setText("TERM");
        holder.tvDefinition.setText("DEFINITION");

        holder.etTerm.setText(term.getTerm());
        holder.etDefinition.setText(term.getDefinition());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListTerm.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mListTerm.size());
            }
        });
        holder.etTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mListTerm.get(position).setTerm(String.valueOf(holder.etTerm.getText()).trim());
            }
        });

        holder.etDefinition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mListTerm.get(position).setDefinition(String.valueOf(holder.etDefinition.getText()).trim());
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

    public List<Term> GetData() {
        return mListTerm;
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
            tvTerm = itemView.findViewById(R.id.tv_term_create);
            etDefinition = itemView.findViewById(R.id.et_definition);
            tvDefinition = itemView.findViewById(R.id.tv_definition_create);
            tvSpace = itemView.findViewById(R.id.tv_space);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
