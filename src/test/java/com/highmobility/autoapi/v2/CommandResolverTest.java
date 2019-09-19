package com.highmobility.autoapi.v2;

import com.highmobility.autoapitest.TestUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandResolverTest {
    @Test public void logsInvalidProperties() {
        // It tries to match 2 commands where 2 properties will fail
        TestUtils.errorLogExpected(2, () -> {
            Bytes invalidStartParking = new Bytes(
                    "004701" +
                            "01000401000104"
            );
            Command command = CommandResolver.resolve(invalidStartParking);
            assertTrue(command.getClass() == Command.class);
            assertTrue(command.getProperties().length == 1);
        });
    }
}
