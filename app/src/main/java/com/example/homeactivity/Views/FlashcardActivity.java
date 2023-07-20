package com.example.homeactivity.Views;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeactivity.Models.Term;
import com.example.homeactivity.R;
import com.example.homeactivity.Utils.LoadingDialog;

import java.util.List;

public class FlashcardActivity extends AppCompatActivity {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private List<Term> termList;
    private ImageButton btnNext;
    private ImageButton btnBack;
    private TextView tvFront;
    private TextView tvBack;
    private TextView tvPosition;
    private int position;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingDialog();

        findViews();
        loadAnimations();
        changeCameraDistance();
        loadData();

        position = 0;
        findViewById(R.id.back_btn_FlashCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < termList.size() - 1) {
                    position++;
                    tvPosition.setText(String.format("%d/%d", position + 1, termList.size()));
                    tvFront.setText(termList.get(position).getTerm());
                    tvBack.setText(termList.get(position).getDefinition());
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                    tvPosition.setText(String.format("%d/%d", position + 1, termList.size()));
                    tvFront.setText(termList.get(position).getTerm());
                    tvBack.setText(termList.get(position).getDefinition());
                }
            }
        });
        findViewById(R.id.close_button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadingDialog.dismissDialog();
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        tvBack = findViewById(R.id.tv_back);
        tvFront = findViewById(R.id.tv_front);
        tvPosition = findViewById(R.id.tv_position);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public void loadData() {
        termList = (List<Term>) getIntent().getSerializableExtra("terms");
        tvFront.setText(termList.get(0).getTerm());
        tvBack.setText(termList.get(0).getDefinition());
        tvPosition.setText(String.format("1/%d", termList.size()));
    }
}