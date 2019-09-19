package com.highmobility.autoapi.v2;

import org.junit.jupiter.api.BeforeEach;

class BaseTest {
    @BeforeEach
    public void before() {
        setRuntime(CommandResolver.RunTime.ANDROID);
    }

    void setRuntime(CommandResolver.RunTime runtime) {
        CommandResolver._runtime = runtime;
    }
}
