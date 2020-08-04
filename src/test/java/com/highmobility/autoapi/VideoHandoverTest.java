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

import com.highmobility.autoapi.value.measurement.Duration;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoHandoverTest extends BaseTest {
    @Test
    public void videoHandover() {
        Bytes waitingForBytes = new Bytes(COMMAND_HEADER + "004301" +
                "01001901001668747470733a2f2f6269742e6c792f326f6259374735" + // https://bit.ly/2obY7G5
                "03000401000100" + // front
                "04000D01000A07004008000000000000" // 3s
        );

        VideoHandover.VideoHandoverCommand state = new VideoHandover.VideoHandoverCommand("https://bit.ly/2obY7G5",
                VideoHandover.Screen.FRONT, new Duration(3d, Duration.Unit.SECONDS));
        assertTrue(TestUtils.bytesTheSame(state, waitingForBytes));

        VideoHandover.VideoHandoverCommand command =
                (VideoHandover.VideoHandoverCommand) CommandResolver.resolve(waitingForBytes);
        assertTrue(command.getUrl().getValue().equals("https://bit.ly/2obY7G5"));
        assertTrue(command.getScreen().getValue() == VideoHandover.Screen.FRONT);
        assertTrue(command.getStartingTime().getValue().getValue() == 3d);
    }
}
