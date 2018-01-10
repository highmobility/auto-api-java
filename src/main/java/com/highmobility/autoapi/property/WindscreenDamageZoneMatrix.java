package com.highmobility.autoapi.property;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.Property;

/**
 * Created by root on 6/29/17.
 */

public class WindscreenDamageZoneMatrix implements HMProperty {
    int windscreenSizeHorizontal;
    int windscreenSizeVertical;

    public int getWindscreenSizeHorizontal() {
        return windscreenSizeHorizontal;
    }

    public int getWindscreenSizeVertical() {
        return windscreenSizeVertical;
    }

    public WindscreenDamageZoneMatrix(byte valueByte) {
        windscreenSizeHorizontal = valueByte >> 4;
        windscreenSizeVertical = valueByte & 0x0F;
    }

    public WindscreenDamageZoneMatrix(int windscreenSizeHorizontal, int windscreenSizeVertical) {
        this.windscreenSizeHorizontal = windscreenSizeHorizontal;
        this.windscreenSizeVertical = windscreenSizeVertical;
    }

    public byte getSizeByte() {
        return (byte) (((windscreenSizeHorizontal & 0x0F) << 4) | (windscreenSizeVertical & 0x0F));
    }

    @Override public byte getPropertyIdentifier() {
        return 4;
    }

    @Override public int getPropertyLength() {
        return 1;
    }

    @Override public byte[] getPropertyBytes() {
        return Property.getPropertyBytes(getPropertyIdentifier(), getPropertyLength(),
                getSizeByte());
    }
}
