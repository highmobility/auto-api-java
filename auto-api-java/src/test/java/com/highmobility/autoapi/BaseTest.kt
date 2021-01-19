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
package com.highmobility.autoapi

import com.highmobility.autoapi.CommandResolver.RunTime
import com.highmobility.autoapi.TestUtils.getExampleCalendar
import com.highmobility.utils.ByteUtils
import com.highmobility.value.Bytes
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.slf4j.Logger
import java.util.*

open class BaseTest {
    lateinit var mockLogger:Logger

    @BeforeEach
    fun before() {
        mockLogger = mockk()
        mockkStatic(AutoApiLogger::class)
        every { AutoApiLogger.getLogger() } returns mockLogger

        var log = CapturingSlot<String>()
        every { mockLogger.warn(capture(log)) } answers { println(log.captured) }
        every { mockLogger.debug(allAny()) } answers { println(log.captured) }
        every { mockLogger.error(capture(log)) } answers { println(log.captured) }
        every { mockLogger.error(any(), any<Throwable>()) } answers { println(log.captured) }

        setRuntime(RunTime.ANDROID)
    }

    fun setRuntime(runtime: RunTime?) {
        CommandResolver._runtime = runtime
    }

    fun bytesTheSame(state: Bytes, bytes: Bytes): Boolean {
        return TestUtils.bytesTheSame(state, bytes)
    }

    fun dateIsSame(date: Calendar?, calendar: Calendar): Boolean {
        if (date == null) return false
        return TestUtils.dateIsSame(date, calendar)
    }

    fun dateIsSame(date: Calendar?, dateString: String): Boolean {
        if (date == null) return false
        return TestUtils.dateIsSame(date, dateString)
    }

    fun getCalendar(dateString: String?): Calendar {
        return getExampleCalendar(dateString)
    }

    fun errorLogExpected(runnable: Runnable) {
        errorLogExpected(1, runnable)
    }

    fun errorLogExpected(count: Int, runnable: Runnable) {
        runnable.run()
        verify(exactly = count) { mockLogger.error(allAny()) }
    }

    fun warningLogExpected(runnable: Runnable) {
        warningLogExpected(1, runnable)
    }

    fun warningLogExpected(count: Int, runnable: Runnable) {
        runnable.run()
        verify(exactly = count) { mockLogger.warn(allAny()) }
    }

    fun debugLogExpected(runnable: Runnable) {
        debugLogExpected(1, runnable)
    }

    fun debugLogExpected(count: Int, runnable: Runnable) {
        runnable.run()
        verify(exactly = count) { mockLogger.debug(allAny()) }
    }

    companion object {
        @JvmField
        val COMMAND_HEADER = ByteUtils.hexFromByte(Command.AUTO_API_VERSION)
    }
}