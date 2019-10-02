package com.highmobility.autoapi.v2;

import com.highmobility.autoapitest.TestUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.BeforeEach;

class BaseTest {
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
