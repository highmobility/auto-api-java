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

import com.highmobility.autoapi.property.Property
import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KVideoHandoverTest : BaseTest() {
    
    
    @Test fun videoHandoverCommand() {
        val bytes = Bytes(COMMAND_HEADER + "004301" +
            "01001901001668747470733a2f2f6269742e6c792f326f6259374735" +
            "03000401000101" +
            "04000D01000A07004004000000000000")
    
        val constructed = VideoHandover.VideoHandoverCommand("https://bit.ly/2obY7G5", VideoHandover.Screen.REAR, Duration(2.5, Duration.Unit.SECONDS))
        assertTrue(bytesTheSame(constructed, bytes))
    
        setRuntime(CommandResolver.RunTime.JAVA)
    
        val resolved = CommandResolver.resolve(bytes) as VideoHandover.VideoHandoverCommand
        assertTrue(resolved.getUrl().value == "https://bit.ly/2obY7G5")
        assertTrue(resolved.getScreen().value == VideoHandover.Screen.REAR)
        assertTrue(resolved.getStartingTime().value?.value == 2.5)
        assertTrue(resolved.getStartingTime().value?.unit == Duration.Unit.SECONDS)
        assertTrue(resolved == bytes)
    }
}