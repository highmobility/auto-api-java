package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    @BeforeEach
    public void before() {
        setRuntime(CommandResolver.RunTime.ANDROID);
    }

    void setRuntime(CommandResolver.RunTime runtime) {
        CommandResolver._runtime = runtime;
    }

    static boolean bytesTheSame(Bytes state, Bytes bytes) {
        return TestUtils.bytesTheSame(state, bytes);
    }
}
