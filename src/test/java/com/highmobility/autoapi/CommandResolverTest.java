package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandResolverTest extends BaseTest {
    @Test public void logsInvalidProperties() {
        setRuntime(CommandResolver.RunTime.JAVA);

        // It tries to match 3 to commands, but in all of these the property parsing will fail
        TestUtils.errorLogExpected(3, () -> {
            Bytes invalidEndParking = new Bytes(
                    "004701" +
                            "01000401000104"
            );
            Command command = CommandResolver.resolve(invalidEndParking);
            assertTrue(command.getClass() == ParkingTicket.State.class);
            assertTrue(command.getProperties().length == 1);
        });
    }
}
