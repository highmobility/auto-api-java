/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.Property;
import com.highmobility.utils.Bytes;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by ttiganik on 16/12/2016.
 */

public class Climate extends FeatureState {
    float insideTemperature;
    float outsideTemperature;
    boolean hvacActive;
    boolean defoggingActive;
    float defrostingTemperature;
    boolean defrostingActive;
    boolean isAutoHvacConstant;
    boolean[] hvacActiveOnDays;

    /**
     *
     * @return Inside temperature in celsius
     */
    public float getInsideTemperature() {
        return insideTemperature;
    }

    /**
     *
     * @return Outside temperature in celsius
     */
    public float getOutsideTemperature() {
        return outsideTemperature;
    }

    /**
     *
     * @return Whether the HVAC is active or not
     */
    public boolean isHvacActive() {
        return hvacActive;
    }

    /**
     *
     * @return Whether the Defogging is active or not
     */
    public boolean isDefoggingActive() {
        return defoggingActive;
    }

    /**
     *
     * @return Whether Defrosting is active or not
     */
    public boolean isDefrostingActive() {
        return defrostingActive;
    }

    /**
     *
     * @return The defrosting temperature
     */
    public float getDefrostingTemperature() {
        return defrostingTemperature;
    }

    /**
     *
     * @return Whether autoHVAC is constant(based on the car surroundings)
     */
    public boolean isAutoHvacConstant() {
        return isAutoHvacConstant;
    }

    /**
     *
     * @return Array of 7 booleans indicating whether the HVAC is active on a specific weekday.
     */
    public boolean[] getHvacActiveOnDays() {
        return hvacActiveOnDays;
    }

    public Climate(float insideTemperature,
                   float outsideTemperature,
                   boolean hvacActive,
                   boolean defoggingActive,
                   boolean defrostingActive,
                   float defrostingTemperature,
                   boolean[] hvacActiveOnDays,
                   boolean autoHvacConstant) {
        super(Command.Identifier.CLIMATE);
        this.insideTemperature = insideTemperature;
        this.outsideTemperature = outsideTemperature;
        this.hvacActive = hvacActive;
        this.defoggingActive = defoggingActive;
        this.defrostingActive = defrostingActive;
        this.hvacActiveOnDays = hvacActiveOnDays;
        this.defrostingTemperature = defrostingTemperature;

        bytes = getBytesWithMoreThanOneByteLongFields(7, 9);
        Bytes.setBytes(bytes, Property.floatToBytes(insideTemperature), 3);
        Bytes.setBytes(bytes, Property.floatToBytes(outsideTemperature), 7);
        bytes[11] = Property.boolToByte(hvacActive);
        bytes[12] = Property.boolToByte(defoggingActive);
        bytes[13] = Property.boolToByte(defrostingActive);
        Bytes.setBytes(bytes, Property.floatToBytes(defrostingTemperature), 14);

        byte result = 0;
        for (int i = 0; i < hvacActiveOnDays.length; i++) {
            if (hvacActiveOnDays[i]) {
                result |= 1 << i;
            }
        }

        if (autoHvacConstant) {
            result |= 1 << 7;
        }

        bytes[18] = result;
    }

    Climate(byte[] bytes) {
        super(Command.Identifier.CLIMATE);

        insideTemperature = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 3, 3 + 4)).getFloat();
        outsideTemperature = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 7, 7 + 4)).getFloat();

        hvacActive = bytes[11] == 0x00 ? false : true;
        defoggingActive = bytes[12] == 0x00 ? false : true;
        defrostingActive = bytes[13] == 0x00 ? false : true;

        defrostingTemperature = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 14, 14 + 4)).getFloat();

        int hvacActiveOnDays = bytes[18];
        if (Property.getBit(hvacActiveOnDays, 7)) isAutoHvacConstant = true;

        this.hvacActiveOnDays = new boolean[7];
        for (int i = 0; i < 7; i++) {
            this.hvacActiveOnDays[i] = Property.getBit(hvacActiveOnDays, i);
        }
    }
}
