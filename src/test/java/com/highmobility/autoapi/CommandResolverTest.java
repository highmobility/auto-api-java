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
                    COMMAND_HEADER + "004701" +
                            "01000401000104"
            );
            Command command = CommandResolver.resolve(invalidEndParking);
            assertTrue(command.getClass() == ParkingTicket.State.class);
            assertTrue(command.getProperties().length == 1);
        });
    }

    @Test public void handlesIncorrectAutoApiVersion() {
        // if hoodState bytes are correct the State will be returned. Only error is logged.
        Bytes hoodBytes = new Bytes("020B" + "006701" + "01000401000101");
        TestUtils.errorLogExpected(1, () -> CommandResolver.resolve(hoodBytes));

        // For unknown VSS bytes the bytes are just returned in Command instance
        Bytes randomBytes = new Bytes("020B" + "676767" + "676767676767676767");
        TestUtils.errorLogExpected(1, () -> {
            Command command = CommandResolver.resolve(randomBytes);
            assertTrue(command instanceof Command);
            assertTrue(command.equals(randomBytes));
        });
    }
}
