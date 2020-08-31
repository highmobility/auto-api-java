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

class Pressure : MeasurementType {
    val value: Double
    val unit: Unit

    constructor(value: Double, unit: Unit) : super() {
        this.value = value
        this.unit = unit
    
        bytes[0] = 0x15
        bytes[1] = unit.id
        set(2, Property.doubleToBytes(value))
    }

    constructor(valueBytes: Bytes) : super(valueBytes, 0x15) {
        unit = Unit.fromInt(valueBytes[1])
        value = Property.getDouble(valueBytes, 2)
    }

    fun inNewtonsPerMetersSquared(): Double {
        return when (unit) {
            Unit.NEWTONS_PER_METERS_SQUARED -> value * 1.0
            Unit.KILOPASCALS -> value * 1.0e+3
            Unit.INCHES_OF_MERCURY -> value * 3386.39
            Unit.BARS -> value * 1.0e+5
            Unit.MILLIBARS -> value * 1.0e+2
            Unit.MILLIMETERS_OF_MERCURY -> value * 133.322
            Unit.POUNDS_FORCE_PER_SQUARE_INCH -> value * 6894.76
        }
    }
    
    fun inKilopascals() = inNewtonsPerMetersSquared() / 1.0e+3
    
    fun inInchesOfMercury() = inNewtonsPerMetersSquared() / 3386.39
    
    fun inBars() = inNewtonsPerMetersSquared() / 1.0e+5
    
    fun inMillibars() = inNewtonsPerMetersSquared() / 1.0e+2
    
    fun inMillimetersOfMercury() = inNewtonsPerMetersSquared() / 133.322
    
    fun inPoundsForcePerSquareInch() = inNewtonsPerMetersSquared() / 6894.76
    
    enum class Unit(val id: Byte) {
        NEWTONS_PER_METERS_SQUARED(0x00),
        KILOPASCALS(0x03),
        INCHES_OF_MERCURY(0x05),
        BARS(0x06),
        MILLIBARS(0x07),
        MILLIMETERS_OF_MERCURY(0x08),
        POUNDS_FORCE_PER_SQUARE_INCH(0x09);
    
        companion object {
            private val map = values().associateBy(Unit::id)
            fun fromInt(type: Byte) = map[type] ?: throw CommandParseException()
        }
    }
}