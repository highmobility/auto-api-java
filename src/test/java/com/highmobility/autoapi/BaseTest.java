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

import com.highmobility.utils.ByteUtils;
import com.highmobility.value.Bytes;

import org.junit.jupiter.api.BeforeEach;

import java.util.Calendar;

import static com.highmobility.autoapi.Command.AUTO_API_VERSION;

public class BaseTest {
    public static final String COMMAND_HEADER =
            ByteUtils.hexFromBytes(new byte[]{AUTO_API_VERSION});

    @BeforeEach
    public void before() {
        setRuntime(CommandResolver.RunTime.ANDROID);
    }

    void setRuntime(CommandResolver.RunTime runtime) {
        CommandResolver._runtime = runtime;
    }

    static boolean bytesTheSame(Bytes state, Bytes bytes) {
        return TestUtils.bytesTheSame(state, bytes);
    }

    static boolean dateIsSame(Calendar date, String dateString) {
        return TestUtils.dateIsSame(date, dateString);
    }

    static Calendar getCalendar(String dateString) {
        return TestUtils.getExampleCalendar(dateString);
    }

    static void errorLogExpected(Runnable runnable) {
        TestUtils.errorLogExpected(1, runnable);
    }

    static void errorLogExpected(int count, Runnable runnable) {
        TestUtils.errorLogExpected(count, runnable);
    }

    static void warningLogExpected(Runnable runnable) {
        TestUtils.warningLogExpected(1, runnable);
    }

    static void warningLogExpected(int count, Runnable runnable) {
        TestUtils.warningLogExpected(count, runnable);
    }

    static void debugLogExpected(Runnable runnable) {
        TestUtils.debugLogExpected(1, runnable);
    }

    static void debugLogExpected(int count, Runnable runnable) {
        TestUtils.debugLogExpected(count, runnable);
    }
}
