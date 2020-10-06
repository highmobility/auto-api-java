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

import java.lang.Runnable
import java.text.DateFormat
import java.text.SimpleDateFormat
import kotlin.Throws
import java.time.OffsetDateTime
import com.highmobility.utils.ByteUtils
import com.highmobility.value.Bytes
import org.junit.jupiter.api.Assertions
import java.text.ParseException
import java.util.*

object TestUtils {
    fun dateIsSame(c: Calendar, dateString: String?): Boolean {
        return dateIsSameIgnoreTimezone(c, dateString) // currently ignoring time zone.
        /*DateFormat format = getFormat(dateString);
        Date expectedDate;
        try {
            expectedDate = format.parse(dateString);
        } catch (ParseException e) {
            expectedDate = format.parse(dateString + "+0000"); // add timezone
        }

        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(expectedDate);
        expectedCalendar.setTimeZone(format.getTimeZone());
        Date commandDate = c.getTime();

        float rawOffset = c.getTimeZone().getRawOffset();
        float expectedRawOffset = expectedCalendar.getTimeZone().getRawOffset();
        assertTrue(rawOffset == expectedRawOffset);

        String commandDateString = getUTCFormat().format(commandDate);
        String expectedDateString = getUTCFormat().format(expectedDate);

        return (commandDateString.equals(expectedDateString));*/
    }

    @JvmStatic
    fun dateIsSame(c: Calendar, c2: Calendar): Boolean {
        return dateIsSameIgnoreTimezone(c, c2)
    }

    fun getFormat(date: String): DateFormat {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        val offset = date.substring(19)
        val tz = TimeZone.getTimeZone("GMT$offset")
        format.timeZone = tz
        return format
    }

    @Throws(ParseException::class)
    fun dateIsSameUTC(c: Calendar, date: String?): Boolean {
        val rawOffset = c.timeZone.rawOffset.toFloat()
        val expectedDate = uTCFormat.parse(date)
        val commandDate = c.time
        val expectedRawOffset = 0f
        Assertions.assertTrue(rawOffset == expectedRawOffset)
        val commandDateString = uTCFormat.format(commandDate)
        val expectedDateString = uTCFormat.format(expectedDate)
        return commandDateString == expectedDateString
    }

    /**
     * This does not consider time zone.
     */
    fun dateIsSameIgnoreTimezone(c1: Calendar, c2: Calendar): Boolean {
        return c1.timeInMillis == c2.timeInMillis
        /*if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) return false;
        if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) return false;
        if (c1.get(Calendar.DAY_OF_MONTH) != c2.get(Calendar.DAY_OF_MONTH)) return false;
        if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY)) return false;
        if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE)) return false;
        if (c1.get(Calendar.SECOND) != c2.get(Calendar.SECOND)) return false;*/
    }

    fun dateIsSameIgnoreTimezone(c1: Calendar, c2: String?): Boolean {
        var expectedDate: Date? = null
        try {
            expectedDate = uTCFormat.parse(c2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val commandDate = c1.time
        val commandDateString = uTCFormat.format(commandDate)
        val expectedDateString = uTCFormat.format(expectedDate)
        return commandDateString == expectedDateString
    }

    //    private static DateFormat format;
    val uTCFormat: DateFormat
        get() {
            val format: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            format.timeZone = TimeZone.getTimeZone("UTC")
            return format
        }

    @Throws(ParseException::class)
    fun getUTCCalendar(dateString: String?, timeZoneMinuteOffset: Int): Calendar {
        val date = uTCFormat.parse(dateString)
        val c: Calendar = GregorianCalendar()
        c.time = date
        c.timeZone =
            TimeZone.getTimeZone(TimeZone.getAvailableIDs(timeZoneMinuteOffset * 60 * 1000)[0])
        return c
    }

    @JvmStatic
    @Throws(ParseException::class)
    fun getUTCCalendar(dateString: String?): Calendar {
        return getUTCCalendar(dateString, 0)
    }

    @JvmStatic
    fun getExampleCalendar(dateString: String?): Calendar {
        val parsed = OffsetDateTime.parse(dateString)
        return GregorianCalendar.from(parsed.toZonedDateTime())
    }

    fun getCalendar(dateString: String): Calendar {
        val format = getFormat(dateString)
        var date: Date? = null
        try {
            date = format.parse(dateString)
        } catch (e: ParseException) {
            try {
                date = format.parse("$dateString+0000") // add timezone
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }
        }
        val c: Calendar = GregorianCalendar()
        c.time = date
        c.timeZone = format.timeZone
        return c
    }

    @JvmStatic
    fun bytesTheSame(state: Bytes, bytes: Bytes): Boolean {
        for (i in 0 until state.length) {
            val stateByte = state.byteArray[i]
            if (bytes.byteArray.size < i + 1) {
                println("state bytes has more bytes\nbytes ex: $bytes\nbytes in: $state")
                return false
            }
            val otherByte = bytes.byteArray[i]
            if (stateByte != otherByte) {
                println(
                    "bytes not equal at index $i. expected: " + ByteUtils
                        .hexFromBytes(byteArrayOf(otherByte)) + ", actual: " + ByteUtils
                        .hexFromBytes(byteArrayOf(stateByte)) +
                            "\nbytes1: " + ByteUtils.hexFromBytes(
                        Arrays.copyOf(
                            bytes.byteArray,
                            i + 1
                        )
                    ) +
                            "\nbytes2: " + ByteUtils.hexFromBytes(
                        Arrays.copyOf(
                            state
                                .byteArray, i + 1
                        )
                    )
                )
                println("bytes ex: $bytes")
                println("bytes in: $state")
                return false
            }
        }
        if (bytes.length > state.length) {
            println("expected bytes has more bytes\nbytes ex: $bytes\nbytes in: $state")
            return false
        }
        return true
    }
}