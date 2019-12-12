package com.highmobility.autoapi;

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by ttiganik on 15/09/16.
 */
public class KeyFobPositionTest extends BaseTest {
    Bytes bytes = new Bytes(COMMAND_HEADER + "004801" +
            "01000401000105");

    @Test
    public void state() {
        KeyfobPosition.State command = (KeyfobPosition.State) CommandResolver.resolve(bytes);
        assertTrue(command.getLocation().getValue() == KeyfobPosition.Location.INSIDE_CAR);
    }

    @Test public void get() {
        String waitingForBytes = COMMAND_HEADER + "004800";
        String commandBytes =
                ByteUtils.hexFromBytes(new KeyfobPosition.GetKeyfobPosition().getByteArray());
        assertTrue(waitingForBytes.equals(commandBytes));
    }
}