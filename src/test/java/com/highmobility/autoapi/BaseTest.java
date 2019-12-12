package com.highmobility.autoapi;

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.BeforeEach;

import static com.highmobility.autoapi.Command.AUTO_API_VERSION;

public class BaseTest {
    public static final String COMMAND_HEADER =
            ByteUtils.hexFromBytes(new byte[]{AUTO_API_VERSION});

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
