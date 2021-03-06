package com.tir38.android.blastermind.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogicTest {

    @Test
    public void typePositionsShouldIncrement() throws Exception {
        Logic.TYPE[] values = Logic.TYPE.values();

        for (int i = 0; i < values.length; i++) {
            assertThat(values[i].getPosition()).isEqualTo(i);
        }
    }
}
