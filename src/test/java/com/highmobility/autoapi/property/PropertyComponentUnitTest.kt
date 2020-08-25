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
import com.highmobility.autoapi.value.measurement.*
import com.highmobility.value.Bytes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.String.format
import java.lang.reflect.Constructor

class PropertyComponentUnitTest : BaseTest() {

    @Test
    fun parsing() {
        // unit parsing/building:

        // 1: from incoming bytes
        val angleProp = Property(Angle::class.java, 0)
        val updateProperty = Property<Angle>(Bytes("01000D" + "01000A02003FEBA5E353F7CED9").byteArray)
        angleProp.update(updateProperty)

        val angleInRadians = angleProp.getValue()?.inRadians()
        val mathToRadians = Math.toRadians(.864)
        assertTrue(format("%.02f", angleInRadians) == format("%.02f", mathToRadians))
        assertTrue(angleProp.getValue()?.unit == Angle.Unit.DEGREES);

        // property without unit is the same

        // 2) builder
        val builtAngle = Property<Angle>(Angle(0.864, Angle.Unit.DEGREES))
        assertTrue(builtAngle.toString() == "00000D01000A02003FEBA5E353F7CED9");

        // 3: in control commands
        // HonkHornFlashLights.HonkFlash command = new HonkHornFlashLights.HonkFlash(0, 3d);
        val command = HonkHornFlashLights.HonkFlash(null, Duration(0.864, Duration.Unit.SECONDS))

        assertTrue(command.honkTime.getValue()?.value == 0.864);
        assertTrue(command.honkTime.getValue()?.unit == Duration.Unit.SECONDS);

        val bytes = Bytes(COMMAND_HEADER + "002601" + "05000D01000A07003FEBA5E353F7CED9");
        assertTrue(command == bytes);
    }

    @Test
    fun testConversionLinearWithConstant() {
        val bytes = Bytes("17024012000000000000")
        val temp = Temperature(4.5, Temperature.Unit.FAHRENHEIT)
        assertTrue(format("%.02f", temp.inCelsius()) == "-15.28")
        assertTrue(temp == bytes)

        val generated = Temperature(bytes)
        assertTrue(generated.unit == Temperature.Unit.FAHRENHEIT)
        assertTrue(generated.value == 4.5)

        val celsiusTemp = Temperature(-15.28, Temperature.Unit.CELSIUS)
        val fahrenheit = format("%.01f", celsiusTemp.inFahrenheit())
        assertTrue(fahrenheit == "4.5")
    }

    @Test
    fun testConversionLinear() {
        /*- data_component: '0001013feba5e353f7ced9'
        values:
          direction: 'longitudinal'
          acceleration:
            gravity: 0.864*/

        val bytes = Bytes("01013feba5e353f7ced9")
        val acceleration = AccelerationUnit(0.864, AccelerationUnit.Unit.GRAVITY)
        assertTrue(format("%.02f", acceleration.inMetersPerSecondSquared()) == "8.48")
        assertTrue(acceleration == bytes)

        val unit = AccelerationUnit(bytes)
        assertTrue(unit.unit == AccelerationUnit.Unit.GRAVITY)
        assertTrue(unit.value == 0.864)

        val accelerationMs2 = AccelerationUnit(8.48, AccelerationUnit.Unit.METERS_PER_SECOND_SQUARED)
        assertTrue(format("%.02f", accelerationMs2.inGravity()) == "0.86")
    }

    @Test
    fun testConversionInverse() {
        /*
    examples:
      - data_component: '0f00401a000000000000'
        value:
          liters_per_100_kilometers: 6.5
        description: Average fuel consumption is 6.5L per 100km
         */
        val bytes = Bytes("0f00401a000000000000")
        val fuelEfficiency = FuelEfficiency(6.5, FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)

        val mpg = String.format("%.02f", fuelEfficiency.inMilesPerGallon())
        assertTrue(mpg == "36.19")
        assertTrue(fuelEfficiency == bytes)

        val unit = FuelEfficiency(bytes)
        assertTrue(unit.unit == FuelEfficiency.Unit.LITERS_PER_100_KILOMETERS)
        assertTrue(unit.value == 6.5)

        val fuelEfficiencyMpg = FuelEfficiency(36.19, FuelEfficiency.Unit.MILES_PER_GALLON)
        val mpgToLp100 = format("%.01f", fuelEfficiencyMpg.inLitersPer100Kilometers())
        assertTrue(mpgToLp100 == "6.5")
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
}