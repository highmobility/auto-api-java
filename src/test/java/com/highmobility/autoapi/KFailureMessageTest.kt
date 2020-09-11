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
import com.highmobility.value.Bytes

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertTrue

class KFailureMessageTest : BaseTest() {
    val bytes = Bytes(COMMAND_HEADER + "000201" + 
            "0100050100020021" +  // Failed message is the 'Trunk' capability
            "02000401000101" +  // Failed message type is 'set'
            "03000401000101" +  // Failure occured because of unauthorised state
            "04000C01000954727920616761696e" +  // Failure description informs to 'Try again'
            "0500050100020102" // Trunk's lock and position properties failed
    )
    
    @Test
    fun testState() {
        val command = CommandResolver.resolve(bytes)
        testState(command as FailureMessage.State)
    }
    
    @Test
    fun testBuilder() {
        val builder = FailureMessage.State.Builder()
        builder.setFailedMessageID(Property(0x0021))
        builder.setFailedMessageType(Property(0x01))
        builder.setFailureReason(Property(FailureMessage.FailureReason.UNAUTHORISED))
        builder.setFailureDescription(Property("Try again"))
        builder.setFailedPropertyIDs(Property(Bytes(byteArrayOf(0x01, 0x02))))
        testState(builder.build())
    }
    
    private fun testState(state: FailureMessage.State) {
        assertTrue(bytesTheSame(state, bytes))
        assertTrue(state.getFailedMessageID().value == 0x0021)
        assertTrue(state.getFailedMessageType().value == 0x01)
        assertTrue(state.getFailureReason().value == FailureMessage.FailureReason.UNAUTHORISED)
        assertTrue(state.getFailureDescription().value == "Try again")
        assertTrue(state.getFailedPropertyIDs().value == Bytes(byteArrayOf(0x01, 0x02)))
    }
    
}