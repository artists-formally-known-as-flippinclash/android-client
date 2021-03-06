package com.tir38.android.blastermind.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tir38.android.blastermind.R;
import com.tir38.android.blastermind.core.Feedback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is hacky and gross. Don't go looking for good ideas here.
 */
public class FeedbackView extends LinearLayout {

    @BindView(R.id.feedback_peg1)
    ImageView mFeedbackPeg1;
    @BindView(R.id.feedback_peg2)
    ImageView mFeedbackPeg2;
    @BindView(R.id.feedback_peg3)
    ImageView mFeedbackPeg3;
    @BindView(R.id.feedback_peg4)
    ImageView mFeedbackPeg4;
    private List<ImageView> mFeedbackPegs;

    public FeedbackView(Context context) {
        this(context, null);
    }

    public FeedbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void update(Feedback feedback) {
        int index = 0;
        int numPositionColor = feedback.getPositionCount();
        int numColor = feedback.getTypeCount();

        // set position and color pegs
        for (int i = index; i < numPositionColor; i++, index++) {
            markPegPositionColor(index);
        }

        // then set color pegs
        for (int i = index; i < numColor + numPositionColor; i++, index++) {
            markPegColor(index);
        }
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_feedback, this);
        ButterKnife.bind(this, view);
        mFeedbackPegs = new ArrayList<>();
        mFeedbackPegs.add(mFeedbackPeg1);
        mFeedbackPegs.add(mFeedbackPeg2);
        mFeedbackPegs.add(mFeedbackPeg3);
        mFeedbackPegs.add(mFeedbackPeg4);
    }

    private void markPegPositionColor(int i) {
        int color = getResources().getColor(R.color.feedback_position_and_color_color);
        mFeedbackPegs.get(i).setBackgroundColor(color);
    }

    private void markPegColor(int i) {
        int color = getResources().getColor(R.color.feedback_color_color);
        mFeedbackPegs.get(i).setBackgroundColor(color);
    }
}
