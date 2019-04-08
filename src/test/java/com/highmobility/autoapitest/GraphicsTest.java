package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.DisplayImage;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphicsTest {
    @Test public void displayImage() {
        Bytes waitingForBytes = new Bytes
                ("00510001001801001568747470733a2f2f676f6f2e676c2f567955316970");
        byte[] bytes = new DisplayImage("https://goo.gl/VyU1ip").getByteArray();
        assertTrue(waitingForBytes.equals(bytes));

        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof DisplayImage);
        assertTrue(((DisplayImage) command).getUrl().getValue().equals("https://goo.gl/VyU1ip"));
    }

    @Test public void failsWherePropertiesMandatory() {
        TestUtils.errorLogExpected(() -> {
            assertTrue(CommandResolver.resolve(DisplayImage.TYPE.getIdentifierAndType()).getClass() == Command.class);
        });
    }
}
