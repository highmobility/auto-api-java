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
package com.highmobility.autoapi.value.measurement

import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.property.Property
import com.highmobility.value.Bytes

class Volume : MeasurementType {
    val value: Double
    val unit: Unit

    constructor(value: Double, unit: Unit) : super() {
        this.value = value
        this.unit = unit
    
        bytes[0] = 0x19
        bytes[1] = unit.id
        set(2, Property.doubleToBytes(value))
    }

    constructor(valueBytes: Bytes) : super(valueBytes, 0x19) {
        unit = Unit.fromInt(valueBytes[1])
        value = Property.getDouble(valueBytes, 2)
    }

    fun inLiters(): Double {
        return when (unit) {
            Unit.LITERS -> value * 1.0
            Unit.MILLILITERS -> value * 1.0e-3
            Unit.CENTILITERS -> value * 1.0e-2
            Unit.DECILITERS -> value * 1.0e-1
            Unit.CUBIC_MILLIMETERS -> value * 1.0e-6
            Unit.CUBIC_CENTIMETERS -> value * 1.0e-3
            Unit.CUBIC_DECIMETERS -> value * 1.0
            Unit.CUBIC_METERS -> value * 1.0e+3
            Unit.CUBIC_INCHES -> value * 0.016387064
            Unit.CUBIC_FEET -> value * 28.316846592
            Unit.FLUID_OUNCES -> value * 0.0295735296875
            Unit.GALLONS -> value * 3.785411784
            Unit.IMPERIAL_FLUID_OUNCES -> value * 0.0284130625
            Unit.IMPERIAL_GALLONS -> value * 4.54609
        }
    }
    
    fun inMilliliters() = inLiters() / 1.0e-3
    
    fun inCentiliters() = inLiters() / 1.0e-2
    
    fun inDeciliters() = inLiters() / 1.0e-1
    
    fun inCubicMillimeters() = inLiters() / 1.0e-6
    
    fun inCubicCentimeters() = inLiters() / 1.0e-3
    
    fun inCubicDecimeters() = inLiters() / 1.0
    
    fun inCubicMeters() = inLiters() / 1.0e+3
    
    fun inCubicInches() = inLiters() / 0.016387064
    
    fun inCubicFeet() = inLiters() / 28.316846592
    
    fun inFluidOunces() = inLiters() / 0.0295735296875
    
    fun inGallons() = inLiters() / 3.785411784
    
    fun inImperialFluidOunces() = inLiters() / 0.0284130625
    
    fun inImperialGallons() = inLiters() / 4.54609
    
    
    
    enum class Unit(val id: Byte) {
        LITERS(0x02),
        MILLILITERS(0x03),
        CENTILITERS(0x04),
        DECILITERS(0x05),
        CUBIC_MILLIMETERS(0x0a),
        CUBIC_CENTIMETERS(0x09),
        CUBIC_DECIMETERS(0x08),
        CUBIC_METERS(0x07),
        CUBIC_INCHES(0x0b),
        CUBIC_FEET(0x0c),
        FLUID_OUNCES(0x13),
        GALLONS(0x17),
        IMPERIAL_FLUID_OUNCES(0x1a),
        IMPERIAL_GALLONS(0x1d);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}