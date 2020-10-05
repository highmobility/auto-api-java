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

class Length : MeasurementType {
    val value: Double
    val unit: Unit

    constructor(value: Double, unit: Unit) : super() {
        this.value = value
        this.unit = unit
    
        bytes[0] = 0x12
        bytes[1] = unit.id
        set(2, Property.doubleToBytes(value))
    }

    constructor(valueBytes: Bytes) : super(valueBytes, 0x12) {
        unit = Unit.fromInt(valueBytes[1])
        value = Property.getDouble(valueBytes, 2)
    }

    fun inMeters(): Double {
        return when (unit) {
            Unit.METERS -> value * 1.0
            Unit.MILLIMETERS -> value * 1.0e-3
            Unit.CENTIMETERS -> value * 1.0e-2
            Unit.DECIMETERS -> value * 1.0e-1
            Unit.KILOMETERS -> value * 1.0e+3
            Unit.MEGAMETERS -> value * 1.0e+6
            Unit.INCHES -> value * 0.0254
            Unit.FEET -> value * 0.3048
            Unit.YARDS -> value * 0.9144
            Unit.MILES -> value * 1609.344
            Unit.SCANDINAVIAN_MILES -> value * 1.0e+4
            Unit.NAUTICAL_MILES -> value * 1852.0
        }
    }
    
    fun inMillimeters() = inMeters() / 1.0e-3
    
    fun inCentimeters() = inMeters() / 1.0e-2
    
    fun inDecimeters() = inMeters() / 1.0e-1
    
    fun inKilometers() = inMeters() / 1.0e+3
    
    fun inMegameters() = inMeters() / 1.0e+6
    
    fun inInches() = inMeters() / 0.0254
    
    fun inFeet() = inMeters() / 0.3048
    
    fun inYards() = inMeters() / 0.9144
    
    fun inMiles() = inMeters() / 1609.344
    
    fun inScandinavianMiles() = inMeters() / 1.0e+4
    
    fun inNauticalMiles() = inMeters() / 1852.0
    
    enum class Unit(val id: Byte) {
        METERS(0x00),
        MILLIMETERS(0x01),
        CENTIMETERS(0x02),
        DECIMETERS(0x03),
        KILOMETERS(0x04),
        MEGAMETERS(0x05),
        INCHES(0x0b),
        FEET(0x0c),
        YARDS(0x0d),
        MILES(0x0e),
        SCANDINAVIAN_MILES(0x0f),
        NAUTICAL_MILES(0x11);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}