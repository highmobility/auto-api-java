/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2019 High-Mobility <licensing@high-mobility.com>
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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Set temperature settings
 */
public class SetTemperatureSettings extends SetCommand {
    public static final Identifier identifier = Identifier.CLIMATE;

    @Nullable Property<Float> driverTemperatureSetting = new Property(Float.class, 0x03);
    @Nullable Property<Float> passengerTemperatureSetting = new Property(Float.class, 0x04);
    @Nullable Property<Float> rearTemperatureSetting = new Property(Float.class, 0x0c);

    /**
     * @return The driver temperature setting
     */
    public @Nullable Property<Float> getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }
    
    /**
     * @return The passenger temperature setting
     */
    public @Nullable Property<Float> getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }
    
    /**
     * @return The rear temperature setting
     */
    public @Nullable Property<Float> getRearTemperatureSetting() {
        return rearTemperatureSetting;
    }
    
    /**
     * Set temperature settings
     *
     * @param driverTemperatureSetting The driver temperature setting in celsius
     * @param passengerTemperatureSetting The passenger temperature setting in celsius
     * @param rearTemperatureSetting The rear temperature in celsius
     */
    public SetTemperatureSettings(@Nullable Float driverTemperatureSetting, @Nullable Float passengerTemperatureSetting, @Nullable Float rearTemperatureSetting) {
        super(identifier);
    
        addProperty(this.driverTemperatureSetting.update(driverTemperatureSetting));
        addProperty(this.passengerTemperatureSetting.update(passengerTemperatureSetting));
        addProperty(this.rearTemperatureSetting.update(rearTemperatureSetting), true);
        if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new IllegalArgumentException();
    }

    SetTemperatureSettings(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return driverTemperatureSetting.update(p);
                    case 0x04: return passengerTemperatureSetting.update(p);
                    case 0x0c: return rearTemperatureSetting.update(p);
                }
                return null;
            });
        }
        if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new NoPropertiesException();
    }
}