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
package com.highmobility.autoapi.property;

import com.highmobility.autoapi.BaseTest
import com.highmobility.autoapi.HonkHornFlashLights
import com.highmobility.autoapi.value.Light
import com.highmobility.autoapi.value.measurement.AccelerationUnit
import com.highmobility.autoapi.value.measurement.Angle
import com.highmobility.autoapi.value.measurement.Duration
import com.highmobility.autoapi.value.measurement.Temperature
import com.highmobility.value.Bytes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import kotlin.math.roundToInt

class PropertyComponentUnitTest : BaseTest() {

    @Test
    fun fromBytes() {
        // Acceleration.gravity
        /*PropertyComponentUnit value = new PropertyComponentUnit(unitBytes, AccelerationType.class);
        assertTrue(value.getUnitTypeClass() == AccelerationType.class);
        assertTrue(value.getUnitType() == AccelerationType.GRAVITY);

        // race acceleration 0x01 with gravity measurement type
        Property prop = new Property("01000D" + "010005003F5D2F1B" + unitBytes);
        assertTrue(prop.getUnitComponent().getUnitTypeClass() == AccelerationType.class);
        assertTrue(prop.getUnitComponent().getUnitType() == AccelerationType.GRAVITY);*/


        // property parsing/building:
        // 1: from incoming bytes
        // degrees .864d = 3FEBA5E353F7CED9
        val angleProp = Property<Angle>(Bytes("01000D" + "01000A02003FEBA5E353F7CED9").byteArray);
        assertTrue(angleProp.getValue()?.inRadians() == Math.toRadians(.864));
        assertTrue(angleProp.getValue()?.unit == Angle.Unit.DEGREES);

        // property without unit is the same
        // Property<FluidLevel> fluidLevelProp = new Property(new Bytes("01000D" + "01000101").getByteArray());

        // 2) builder
        val builtAngle = Property<Angle>(
            Angle(0.864, Angle.Unit.DEGREES)
        )

        assertTrue(builtAngle.toString().equals("01000D010005003F5D2F1B0400020200"));

        // 3: in control commands
        // HonkHornFlashLights.HonkFlash command = new HonkHornFlashLights.HonkFlash(0, 3d);
        val command = HonkHornFlashLights.HonkFlash(
            null,
            Duration(
                0.864,
                Duration.Unit.SECONDS
            )
        );

        assertTrue(command.honkTime.getValue()?.value == 0.864);
        assertTrue(command.honkTime.getValue()?.unit == Duration.Unit.SECONDS);

        /*
          - id: 0x03
            name: flash_times
            name_cased: flashTimes
            name_pretty: Flash times
            type: uinteger
            size: 1
            description: Number of times to flash the lights
            examples:
              - data_component: '05'
                value: 5
                description: Flash the lights 5 times
          - id: 0x05
            name: honk_time
            name_cased: honkTime
            name_pretty: Honk time
            type: unit.duration
            size: 10
            description: Time to honk the horn
            examples:
              - data_component: '07004000000000000000'
                value:
                  seconds: 2.0
                description: Honk the horn for 2.0s
         */

        val bytes = Bytes(COMMAND_HEADER + "002601" + "05000A07004000000000000000");
        assertTrue(command == bytes);
    }

    @Test
    fun testTemp() {
        val temp = Temperature(
            4.5,
            Temperature.Unit.FAHRENHEIT
        )

        val inCelsius = String.format("%.02f", temp.inCelsius())
        assertTrue(inCelsius == "-15.28")

        val generated = Temperature(Bytes("170140440ccccccccccd"))
        assertTrue(generated.unit == Temperature.Unit.CELSIUS)
        assertTrue(generated.value == 40.1)
    }

    @Test
    fun testAcceleration() {
        val bytes = Bytes("01013feba5e353f7ced9")
        // gravity, 0.864
        val unit = AccelerationUnit(bytes)


        // TODO: 3/7/20 test similarly other 2 types of units
    }

    @Test
    fun <T> testBytesCtor() {
        val valueClass = Light::class.java
        val valueBytes = Bytes("0001") // front, inactive

        val constructor: Constructor<*> = valueClass.getConstructor(Bytes::class.java)
        val parsedValue: T = constructor.newInstance(valueBytes) as T
        val light = parsedValue as Light
        println("${light.state}")
    }

    @Test
    fun aa() {
        val t = "    "

        val valuesCtor =
            """
                constructor(value: Double, unit: Unit) : super() {\n
                    aa()
                """.trimIndent()
        val vCtor =
            """
                constructor(value: Double, unit: Unit) : super() {
                   this.value = value
                   this.unit = unit
                
                   bytes = ByteArray(length)
                   bytes[0] = MEASUREMENT_TYPE_ID
                   bytes[1] = unit.id
                   set(2, Property.doubleToBytes(value))
                }
                """.trimIndent()
                .trimIndent()

        println("")


    }
}