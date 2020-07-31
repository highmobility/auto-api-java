/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.value.Time;
import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoHandoverTest extends BaseTest {
    @Test
    public void videoHandover() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "004301" +
                "01002E01002b68747470733a2f2f7777772e796f75747562652e636f6d2f77617463683f763d795756423755366d583259" +
                "020005010002005a" +
                "03000401000100"
        );

        VideoHandover.VideoHandoverCommand state = new VideoHandover.VideoHandoverCommand("https" +
                "://www.youtube.com/watch?v=yWVB7U6mX2Y",
                VideoHandover.Screen.FRONT, new Duration(30d, Duration.Unit.SECONDS));
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));

        VideoHandover.VideoHandoverCommand command =
                (VideoHandover.VideoHandoverCommand) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://www.youtube" +
                ".com/watch?v=yWVB7U6mX2Y"));
        assertTrue(command.getScreen().getValue() == VideoHandover.Screen.FRONT);
        assertTrue(command.getStartingTime().getValue().getValue() == 90d);
    }
}
