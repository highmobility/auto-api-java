package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphicsTest extends BaseTest {
    @Test public void displayImage() {
        Bytes waitingForBytes = new Bytes
                ("00510101001801001568747470733a2f2f676f6f2e676c2f567955316970");
        Command bytes = new DisplayImage("https://goo.gl/VyU1ip");
        assertTrue(bytesTheSame(bytes, waitingForBytes));

        setRuntime(CommandResolver.RunTime.JAVA);
        Command command = CommandResolver.resolve(waitingForBytes);
        assertTrue(command instanceof DisplayImage);
        assertTrue(((DisplayImage) command).getImageURL().getValue().equals("https://goo" +
                ".gl/VyU1ip"));
    }
}
