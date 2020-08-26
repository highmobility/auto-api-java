package com.highmobility.autoapi.value.measurement

import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.property.PropertyValueObject
import com.highmobility.utils.ByteUtils.hexFromByte
import com.highmobility.value.Bytes

open class MeasurementType : PropertyValueObject {
    constructor() : super(SIZE)

    constructor(valueBytes: Bytes, measurementId: Byte) : super(valueBytes) {
        if (valueBytes.length != length) {
            throw CommandParseException("$valueBytes: Measurement type bytes length should be $SIZE")
        } else if (valueBytes[0] != measurementId) {
            throw CommandParseException(
                "$valueBytes: Measurement type id is not ${hexFromByte(measurementId)}"
            )
        }
    }

    override fun getLength(): Int {
        return SIZE
    }

    companion object {
        const val SIZE = 10
    }
}
