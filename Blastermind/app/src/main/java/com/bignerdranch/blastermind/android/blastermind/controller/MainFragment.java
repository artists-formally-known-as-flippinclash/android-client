package com.bignerdranch.blastermind.android.blastermind.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bignerdranch.blastermind.andorid.core.Guess;
import com.bignerdranch.blastermind.andorid.core.Logic;
import com.bignerdranch.blastermind.android.blastermind.R;
import com.bignerdranch.blastermind.android.blastermind.view.GuessRowView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.bignerdranch.blastermind.andorid.core.Logic.TYPE;

public class MainFragment extends Fragment {

    @InjectView(R.id.update_button)
    protected Button mUpdateButton;
    @InjectView(R.id.fragment_main_guesses_container)
    protected LinearLayout mGuessContainer;
    private int mCurrentTurn;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.update_button)
    public void onUpdateClick() {

        if (mCurrentTurn > Logic.guessLimit) {
            mUpdateButton.setEnabled(false);
        }

        mCurrentTurn++;

        // create new row and add it
        Guess guess = pullGuessFromInput();

        GuessRowView guessRow = new GuessRowView(getActivity());
        guessRow.setup(Logic.guessWidth);
        guessRow.setGuess(guess);
        mGuessContainer.addView(guessRow);
    }

    private Guess pullGuessFromInput() {
        Guess guess = new Guess(Logic.guessWidth);
        ArrayList<TYPE> types = new ArrayList<>(Logic.guessWidth);

        types.add(TYPE.Purple);
        types.add(TYPE.Red);
        types.add(TYPE.Yellow);
        types.add(TYPE.Green);
        guess.setTypes(types);
        return guess;
    }

}
