package com.highmobility.autoapi.value

import com.highmobility.autoapi.CommandParseException
import com.highmobility.autoapi.property.ByteEnum
import com.highmobility.utils.ByteUtils.hexFromByte

enum class Brand(val id: Byte):ByteEnum {
    UNKNOWN(0x00),
    ABARTH(0x01),
    ALFAROMEO(0x02),
    ALPINE(0x03),
    AUDI(0x04),
    BMW(0x05),
    CADILLAC(0x06),
    CHEVROLET(0x07),
    CHRYSLER(0x08),
    CITROËN(0x09),
    CUPRA(0x0a),
    DACIA(0x0b),
    DODGE(0x0c),
    DS(0x0d),
    FIAT(0x0e),
    FORD(0x0f),
    HONDA(0x10),
    HYUNDAI(0x11),
    IVECO(0x12),
    JAGUAR(0x13),
    JEEP(0x14),
    KIA(0x15),
    LANCIA(0x16),
    LAND_ROVER(0x17),
    LEXUS(0x18),
    MAN(0x19),
    MAZDA(0x1a),
    MERCEDES_BENZ(0x1b),
    MINI(0x1c),
    MITSUBISHI(0x1d),
    NISSAN(0x1e),
    OPEL(0x1f),
    PEUGEOT(0x20),
    PORSCHE(0x21),
    RENAULT(0x22),
    SEAT(0x23),
    ŠKODA(0x24),
    SMART(0x25),
    SUBARU(0x26),
    TOYOTA(0x27),
    VOLKSWAGEN(0x28),
    VOLVO(0x29),
    EMULATOR(0x2a);

    companion object {
        private val map = values().associateBy(Brand::id)
        fun fromInt(type: Byte) =
            map[type] ?: throw CommandParseException("No brand with id %s", hexFromByte(type))
    }

    override fun getByte(): Byte = id
}