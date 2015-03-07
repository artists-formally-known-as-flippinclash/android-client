package com.bignerdranch.blastermind.android.blastermind.event;

import com.bignerdranch.blastermind.andorid.core.MatchEnd;

public class MatchEndedEvent {

    private final MatchEnd mMatchEnd;

    public MatchEndedEvent(MatchEnd matchEnd) {
        mMatchEnd = matchEnd;
    }

    public MatchEnd getMatchEnd() {
        return mMatchEnd;
    }
}
