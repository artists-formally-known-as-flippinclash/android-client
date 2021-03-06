package com.tir38.android.blastermind.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.tir38.android.blastermind.core.Feedback;
import com.tir38.android.blastermind.core.Guess;
import com.tir38.android.blastermind.core.Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * GSON object to interface between Guess model object and webservice
 */
public class GuessRowView extends LinearLayout {

    private static final String TAG = GuessRowView.class.getSimpleName();
    private Context mContext;
    private ArrayList<PegView> mPegViews;
    private FeedbackView mFeedbackView;
    private int mActivePegIndex; // index in mPegView for which peg is active; -1 == all pegs are set

    public GuessRowView(Context context) {
        this(context, null);
    }

    public GuessRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setup(int numPegs, int sizePx) {
        setGravity(Gravity.CENTER);
        // circumvent layout process by forcing this to be square and but match parent on height
        LayoutParams layoutParams = new LayoutParams(sizePx, sizePx); // make square
        layoutParams.setMargins(8, 0, 8, 0);

        // add feedback view
        mFeedbackView = new FeedbackView(mContext);
        mFeedbackView.setLayoutParams(layoutParams);
        addView(mFeedbackView);

        // add peg views
        mPegViews = new ArrayList<>(numPegs);

        for (int i = 0; i < numPegs; i++) {

            final int index = i;
            final PegView pegView = new PegView(mContext);
            pegView.setLayoutParams(layoutParams);
            pegView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivePegIndex = index;

                    setAllPegsInactive();
                    // and set this one to active
                    pegView.setActive();
                }
            });

            // add to view hierarchy and array
            addView(pegView);
            mPegViews.add(pegView);

            // set first item to active
            mPegViews.get(mActivePegIndex).setActive();
        }
    }

    @Nullable
    public PegView getActivePeg() {
        if (mActivePegIndex == -1) {
            return null;
        }
        return mPegViews.get(mActivePegIndex);
    }

    public void setActivePegType(Logic.TYPE type) {
        if (getActivePeg() == null) {
            return;
        }
        getActivePeg().setType(type);
        advanceActivePeg();
    }

    public boolean areAllPegsSet() {
        for (PegView pegView : mPegViews) {
            if (!pegView.isSet()) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public Guess getGuess() {
        if (!areAllPegsSet()) {
            return null;
        }

        // build guess from each PegView
        List<Logic.TYPE> types = new ArrayList<>();
        for (PegView pegView : mPegViews) {
            types.add(pegView.getType());
        }
        return new Guess(types);
    }

    public void setFeedback(Feedback feedback) {
        mFeedbackView.update(feedback);
    }

    public void setCurrent() {
        // set first peg active
        mPegViews.get(0).setActive();
        setAllPegsEnabled(true);
    }

    public void setNotCurrent() {
        setAllPegsInactive();
        setAllPegsEnabled(false);
    }

    // inactive meaning not currently selected peg
    private void setAllPegsInactive() {
        for (PegView pegView : mPegViews) {
            pegView.setInactive();
        }
    }

    // enabled meaning clickable
    private void setAllPegsEnabled(boolean enabled) {
        for (PegView pegView : mPegViews) {
            pegView.setEnabled(enabled);
        }
    }

    /**
     * advance to the next activatable peg (i.e. the next peg that hasn't been set yet)
     */
    private void advanceActivePeg() {

        // if all pegs are set, don't advance, stay on current peg
        if (areAllPegsSet()) {
            return;
        }

        setAllPegsInactive();

        for (int i = 0; i < mPegViews.size(); i++) {
            PegView pegView = mPegViews.get(i);
            if (!pegView.isSet()) {
                pegView.setActive();
                mActivePegIndex = i;
                return;
            }
        }

        // all pegs are set
        mActivePegIndex = -1;
    }
}
