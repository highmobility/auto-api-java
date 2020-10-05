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
package com.highmobility.autoapi.property

import com.highmobility.autoapi.BaseTest
import com.highmobility.autoapi.Charging
import com.highmobility.autoapi.Charging.ChargeMode
import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.CommandResolver
import com.highmobility.value.Bytes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PropertyTest : BaseTest() {
    // bytes: 00000160E0EA1388
    var timestamp = getCalendar("2018-01-10T16:32:05.000Z")

    @Test
    @Throws(CommandParseException::class)
    fun parseValue() {
        // assert that bytes are parsed to the value component
        val completeBytes = Bytes(
            "000004" +
                    "01000100" // value
        )
        val property: Property<ChargeMode> = Property(ChargeMode::class.java, 0)
        property.update(Property<Any?>(completeBytes.byteArray))
        assertTrue(property.valueComponent!!.getValue() === ChargeMode.IMMEDIATE)
        testValueComponent(property, 1, ChargeMode.IMMEDIATE)
    }

    @Test
    @Throws(CommandParseException::class)
    fun parseValueWithTimestamp() {
        // assert that bytes are parsed to the value/timestamp component
        var completeBytes = Bytes(
            "00000F" +
                    "01000100" +  // value
                    "02000800000160E0EA1388" // timestamp
        )

        val property: Property<ChargeMode> = Property(ChargeMode::class.java, 0)
        property.update(Property<Any?>(completeBytes.byteArray))
        testValueComponent(property, 1, ChargeMode.IMMEDIATE)
        testTimestampComponent(property)

        // parse in different order as well
        completeBytes = Bytes(
            "00000F" +
                    "02000800000160E0EA1388" +  // timestamp
                    "01000100" // value
        )
        property.update(Property<Any?>(completeBytes.byteArray))
        testValueComponent(property, 1, ChargeMode.IMMEDIATE)
        testTimestampComponent(property)
    }

    @Test
    @Throws(CommandParseException::class)
    fun parseValueWithTimestampLessThanEightBytesLong() {
        // assert that bytes are parsed to the value/timestamp component
        val completeBytes = Bytes(
            "00000F" +
                    "01000100" +  // value
                    "0200060160E0EA1388" // timestamp 6 bytes
        )
        val property: Property<ChargeMode> = Property(ChargeMode::class.java, 0)
        property.update(Property<Any?>(completeBytes.byteArray))
        testTimestampComponent(property)
    }

    @Test
    @Throws(CommandParseException::class)
    fun parseFailure() {
        // test bytes correct and components exist
        var completeBytes = Bytes(
            "00001A" +
                    "" +  // value
                    "02000800000160E0EA1388" +  // timestamp
                    "03000C000A54727920696e20343073" //failure
        )
        val property: Property<*> = Property(ChargeMode::class.java, 0)
        property.update(Property<Any?>(completeBytes.byteArray))
        testTimestampComponent(property)
        testFailureComponent(property, completeBytes)
        // parse in different order as well
        completeBytes = Bytes(
            "00001A" +
                    "" +  // value
                    "03000C000A54727920696e20343073" +  //failure
                    "02000800000160E0EA1388" // timestamp
        )
        property.update(Property<Any?>(completeBytes.byteArray))
        testTimestampComponent(property)
        testFailureComponent(property, completeBytes)
    }

    @Test
    fun buildValue() {
        val property = Property<ChargeMode>(ChargeMode.IMMEDIATE)
        val propertyLength = Property.getUnsignedInt(property.getRange(1, 3))
        assertTrue(propertyLength == 4)
        assertTrue(property.propertyLength == 4)
        testValueComponent(property, 1, ChargeMode.IMMEDIATE)
        val completeBytes = Bytes("00000401000100")
        assertTrue(property == completeBytes)
    }

    @Test
    fun buildValueWithTimestamp() {
        // test bytes correct and components exist
        val property =
            Property(
                0x12,
                ChargeMode.IMMEDIATE,
                timestamp,
                null,
                null
            )
        testValueComponent(property, 1, ChargeMode.IMMEDIATE)
        testTimestampComponent(property)
        val completeBytes = Bytes(
            "12000F" +
                    "01000100" +  // value
                    "02000800000160E0EA1388" // timestamp
        )
        assertTrue(property == completeBytes)
    }

    private fun testValueComponent(property: Property<*>, length: Int, value: Any) {
        // test bytes value component identifier and length is correct
        assertTrue(property.valueComponent != null)
        assertTrue(property.valueComponent!!.getValue() != null)
        assertTrue(property.valueComponent!!.getValueBytes().length == length)
        assertTrue(property.valueComponent!!.getValue() === value)
    }

    private fun testTimestampComponent(property: Property<*>) {
        assertTrue(property.timestampComponent != null)
        assertTrue(property.timestampComponent!!.identifier.toInt() == 0x02)
        assertTrue(dateIsSame(property.timestampComponent!!.calendar, timestamp))
        // test that bytes are set in component
        assertTrue(Property.getLong(property.timestampComponent!!.getValueBytes().byteArray) == 1515601925000L)
    }

    private fun testFailureComponent(property: Property<*>, expectedBytes: Bytes) {
        val failureComponent = property.failureComponent
        assertTrue(failureComponent != null)
        assertTrue(failureComponent!!.identifier.toInt() == 0x03)
        assertTrue(failureComponent.failureDescription == "Try in 40s")
        assertTrue(failureComponent.getFailureReason() == PropertyComponentFailure.Reason.RATE_LIMIT)
        assertTrue(failureComponent.valueBytes.equals("000A54727920696e20343073"))
        assertTrue(property == expectedBytes)
    }

    @Test
    fun testValueComponentFailedParsing() {
        debugLogExpected {
            // test if float expected but bytes are with smaller length
            // charging with invalid length chargeCurrentAC. cannot parse to float
            // correct chargeCurrentDC
            val command = CommandResolver.resolve(
                COMMAND_HEADER + "002301" +  // 19000D01000A0900bfe3333333333333 correct
                        "19000C0100090900bfe33333333333" +  // double value length should be 8
                        "0b000401000101"
            ) as Charging.State
            assertTrue(command.batteryCurrent.getValue() == null)
            assertTrue(
                command.batteryCurrent.getComponent(0x01)!!.equals("0100090900bfe33333333333")
            )
            assertTrue(command.chargePortState.getValue() != null)
        }
    }

    @Test
    fun testFailureComponentFailedParsing() {
        // test if invalid failure reason, then error logged and failure component not populated, and put to generic
        // component array
        // 0x11 is invalid failure reason
        errorLogExpected {
            val command = CommandResolver.resolve(
                COMMAND_HEADER + "002301" +
                        "19001C01000A0900bfe3333333333333" + "03000C110A54727920696e20343073" +
                        "0b000401000101"
            ) as Charging.State
            assertTrue(command.batteryCurrent.failureComponent == null)
            assertTrue(
                command.batteryCurrent.getComponent(0x03)!!.equals(
                    "03000C110A54727920696e20343073"
                )
            )
        }
    }

    @Test
    fun buildFailure() {
        val failure =
            PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT, "Try in 40s")
        // test bytes correct
        val property: Property<*> =
            Property(
                0x11,
                null,
                null,
                failure,
                null
            )
        assertTrue(property.timestampComponent == null)
        assertTrue(property.valueComponent == null)
        val completeBytes = Bytes(
            "11000F" +
                    "" +  // value
                    "" +  // timestamp
                    "03000C000A54727920696e20343073" //failure
        )
        testFailureComponent(property, completeBytes)
    }

    @Test
    fun buildFailureIgnoreValue() {
        val failure =
            PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT, "Try in 40s")
        val property: Property<*> =
            Property(
                0x02,
                ChargeMode.IMMEDIATE,
                timestamp,
                failure,
                null
            )
        assertTrue(property.timestampComponent != null)
        assertTrue(property.valueComponent == null)
        val completeBytes = Bytes(
            "02001A" +
                    "" +  // value
                    "02000800000160E0EA1388" +  // timestamp
                    "03000C000A54727920696e20343073" //failure
        )
        testFailureComponent(property, completeBytes)
        testTimestampComponent(property)
    }

    @Test
    fun propertyLength() {
        val property = PropertyInteger(
            0x01,
            false,
            2,
            Property(2)
        )
        assertTrue(property.equals("0100050100020002"))
        val longString =
            "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                    "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring" +
                    "longstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstringlongstring"
        val stringProperty: Property<String?> =
            Property(0x02, longString)
        assertTrue(stringProperty[4] == 0x01.toByte()) // length
        assertTrue(stringProperty[5] == 0x4A.toByte())
    }

    @Test
    fun emptyValueProperty() {
        val bytes = Bytes("010000") // data component with 00 length
        PropertyComponentValue<Any?>(bytes)
    }

    @Test
    @Throws(CommandParseException::class)
    fun emptyString() {
        // representing null/"" string
        // null = no value component
        // "" = value component with 0 length
        val nullStringBytes = Property<String>(Bytes("000000").byteArray)
        val emptyStringBytes: Property<String> = Property(Bytes("000003010000").byteArray)
        var nullStringProperty: Property<String?> = Property(0x00, null)
        assertTrue(nullStringProperty.valueComponent == null)
        assertTrue(nullStringProperty == nullStringBytes)
        var emptyStringProperty: Property<String?> = Property("")
        assertTrue(emptyStringProperty.valueComponent != null)
        assertTrue(emptyStringProperty.valueComponent!!.getValue() is String)
        assertTrue(emptyStringProperty.valueComponent!!.getValue() == "")
        assertTrue(emptyStringProperty == emptyStringBytes)
        nullStringProperty = Property(String::class.java, 0x00)
        nullStringProperty.update(nullStringBytes)
        assertTrue(nullStringProperty.valueComponent == null)
        emptyStringProperty = Property(String::class.java, 0x00)
        emptyStringProperty.update(emptyStringBytes)
        assertTrue(emptyStringProperty.valueComponent != null)
        assertTrue(emptyStringProperty.valueComponent!!.getValue() == "")
    }// JF2SHBDC7CH451869

    @Test
    fun isUniversalProperty() {
        // isUniversalProperty() is an convenience method in property. Not used internally
        val timestamp = Property(0xA2, Bytes("41D6F1C07F800000"))
        val nonce = Property(0xA0, Bytes("324244433743483436"))
        val sig = Property(
            0xA1,
            Bytes("4D2C6ADCEF2DC5631E63A178BF5C9FDD8F5375FB6A5BC05432877D6A00A18F6C749B1D3C3C85B6524563AC3AB9D832AFF0DB20828C1C8AB8C7F7D79A322099E6")
        )
        val vin = Property(0xA3, Bytes("4a46325348424443374348343531383639")) // JF2SHBDC7CH451869
        val brand = Property(0xA4, Bytes("01"))

        assertTrue(timestamp.isUniversalProperty)
        assertTrue(nonce.isUniversalProperty)
        assertTrue(sig.isUniversalProperty)
        assertTrue(vin.isUniversalProperty)
        assertTrue(brand.isUniversalProperty)
    }

    @Test
    @Throws(CommandParseException::class)
    fun integerPropertySignChecked() {
        val integerProperty = PropertyInteger(253)
        integerProperty.update(false, 1, 253)
        assertTrue(integerProperty.getValue() == 253)
        val checked = PropertyInteger(0x00, false)
        checked.update(integerProperty)
        // assert that the bytes are correct to create 253 int
        assertTrue(checked.getValue() == 253)
    }

    @Test
    fun integerPropertyUpdateWithoutValue() {
        val failure = PropertyComponentFailure(
            PropertyComponentFailure.Reason.UNAUTHORISED,
            "Permissions not granted"
        )
        val property =
            Property<Int>(
                0x02,
                null,
                null,
                failure,
                null
            )
        PropertyInteger(0x02, true, 2, property)
    }

    // MARK: availability component
    @Test
    @Throws(CommandParseException::class)
    fun availabilityComponentParsedToIvar() {
        // assert that bytes are parsed to the value component
        val completeBytes = Bytes(
            "010010" +
                    "01000100" +  // ChargeMode immediate
                    "05000C000e003fe999999999999a01"
        )
        val property: Property<ChargeMode> = Property(ChargeMode::class.java, 0)
        property.update(Property<Any?>(completeBytes.byteArray))
        assertTrue(property.availabilityComponent!!.equals("05000C000e003fe999999999999a01"))
        assertTrue(property.valueComponent!!.equals("01000100"))
        assertTrue(property.getComponents().size == 2)
    }

    @Test
    fun buildAvailability() {
        val availability =
            PropertyComponentAvailability(Bytes("05000C000e003fe999999999999a01"))
        val property = Property(
            0x01,
            ChargeMode.IMMEDIATE,
            null,
            null,
            availability
        )
        val propertyLength = Property.getUnsignedInt(property.getRange(1, 3))
        assertTrue(propertyLength == 4 + 15) // value + availability
        assertTrue(property.propertyLength == 4 + 15)
        assertTrue(property.availabilityComponent!!.equals("05000C000e003fe999999999999a01"))
        // values tested in Availability test
        assertTrue(property.valueComponent!!.equals("01000100"))
        assertTrue(property.getComponents().size == 2)
    }
}