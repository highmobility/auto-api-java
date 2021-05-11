package com.highmobility.autoapi.property;

import com.highmobility.autoapi.BaseTest
import com.highmobility.autoapi.Charging
import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.CommandResolver
import com.highmobility.autoapi.value.Failure
import com.highmobility.value.Bytes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PropertyComponentFailureTest : BaseTest() {
    @Test
    @Throws(CommandParseException::class)
    fun parseFailure() {
        // test bytes correct and components exist
        var completeBytes = Bytes(
            "00001A" +
                    "" +  // value
                    "02000800000160E0EA1388" +  // timestamp
                    "03000D00000A54727920696e20343073" //failure
        )
        val property: Property<*> = Property(Charging.ChargeMode::class.java, 0)
        val propertyForComponents = Property<Any?>(completeBytes.byteArray)
        property.update(propertyForComponents)
        PropertyTest.testTimestampComponent(property)
        PropertyTest.testFailureComponent(property, completeBytes)
        // parse in different order as well
        completeBytes = Bytes(
            "00001A" +
                    "" +  // value
                    "03000D00000A54727920696e20343073" +  //failure
                    "02000800000160E0EA1388" // timestamp
        )
        property.update(Property<Any?>(completeBytes.byteArray))
        PropertyTest.testTimestampComponent(property)
        PropertyTest.testFailureComponent(property, completeBytes)
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
            "110010" +
                    "" +  // value
                    "" +  // timestamp
                    "03000D00000A54727920696e20343073" //failure
        )

        PropertyTest.testFailureComponent(property, completeBytes)
    }

    @Test
    fun buildFailureIgnoreValue() {
        val failure =
            PropertyComponentFailure(PropertyComponentFailure.Reason.RATE_LIMIT, "Try in 40s")
        val property: Property<*> =
            Property(
                0x02,
                Charging.ChargeMode.IMMEDIATE,
                PropertyTest.timestamp,
                failure,
                null
            )
        assertTrue(property.timestampComponent != null)
        assertTrue(property.valueComponent == null)
        val completeBytes = Bytes(
            "02001B" +
                    "" +  // value
                    "02000800000160E0EA1388" +  // timestamp
                    "03000D00000A54727920696e20343073" //failure
        )
        PropertyTest.testFailureComponent(property, completeBytes)
        PropertyTest.testTimestampComponent(property)
    }

    @Test
    fun buildFailureV2() {
        val failure =
            PropertyComponentFailure(Failure(Failure.Reason.RATE_LIMIT, "Try in 40s"))
        assertTrue(failure.failureDescription == "Try in 40s")
        assertTrue(failure.failure.reason == Failure.Reason.RATE_LIMIT)
        assertTrue(failure.failure.description == "Try in 40s")
        assertTrue(failure.valueBytes.equals("00000A54727920696e20343073"))
    }

    @Test
    fun testFailureComponentFailedParsing() {
        // test if invalid failure reason, then warning logged and failure component not populated,
        // and put to generic component array
        // 0x11 is invalid failure reason
        warningLogExpected {
            val command = CommandResolver.resolve(
                BaseTest.COMMAND_HEADER + "002301" +
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
}
