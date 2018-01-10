package com.highmobility.autoapi.property;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

public class WindscreenDamageZone implements HMProperty {
    int damageZoneX;
    int damageZoneY;

    public WindscreenDamageZone(byte valueByte) {
        damageZoneX = valueByte >> 4;
        damageZoneY = valueByte & 0x0F;
    }

    /**
     * @return The horizontal position of the zone in a matrix seen from the inside of the car,
     * starting from index 1
     */
    public int getDamageZoneX() {
        return damageZoneX;
    }

    /**
     *
     * @return The vertical position of the zone from the bottom, starting from index 1
     */
    public int getDamageZoneY() {
        return damageZoneY;
    }

    public WindscreenDamageZone(int damageZoneX, int damageZoneY) {
        this.damageZoneX = damageZoneX;
        this.damageZoneY = damageZoneY;
    }

    public byte getPositionByte() {
        return (byte) (((damageZoneX & 0x0F) << 4) | (damageZoneY & 0x0F));
    }

    @Override public byte getPropertyIdentifier() {
        return 0x05;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(),
                getPositionByte());
    }
}
