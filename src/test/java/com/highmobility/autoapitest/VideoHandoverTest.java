package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.VideoHandover;
import com.highmobility.autoapi.property.ScreenLocation;
import com.highmobility.utils.ByteUtils;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class VideoHandoverTest {
    @Test public void videoHandover() {
        byte[] waitingForBytes = ByteUtils.bytesFromHex
                ("00430001002b68747470733a2f2f7777772e796f75747562652e636f6d2f77617463683f763d795756423755366d583259020002005a03000100");
        byte[] bytes = new VideoHandover("https://www.youtube.com/watch?v=yWVB7U6mX2Y", 90,
                ScreenLocation.FRONT).getByteArray();
        assertTrue(Arrays.equals(waitingForBytes, bytes));

        VideoHandover command = (VideoHandover) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().equals("https://www.youtube.com/watch?v=yWVB7U6mX2Y"));
        assertTrue(command.getLocation() == ScreenLocation.FRONT);
        assertTrue(command.getStartingSecond() == 90);
    }
}
