package com.highmobility.autoapitest;

import com.highmobility.autoapi.CommandResolver;
import com.highmobility.autoapi.VideoHandover;
import com.highmobility.autoapi.value.ScreenLocation;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoHandoverTest {
    @Test public void videoHandover() {
        Bytes waitingForBytes = new Bytes(
                "004300" +
                        "01002E01002b68747470733a2f2f7777772e796f75747562652e636f6d2f77617463683f763d795756423755366d583259" +
                        "020005010002005a" +
                        "03000401000100"
        );

        VideoHandover state = new VideoHandover("https://www.youtube.com/watch?v=yWVB7U6mX2Y", 90,
                ScreenLocation.FRONT);
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));

        VideoHandover command = (VideoHandover) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://www.youtube" +
                ".com/watch?v=yWVB7U6mX2Y"));
        assertTrue(command.getLocation().getValue() == ScreenLocation.FRONT);
        assertTrue(command.getStartingSecond().getValue() == 90);
    }
}
