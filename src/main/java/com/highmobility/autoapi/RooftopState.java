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

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.rooftop.ConvertibleRoofState;
import com.highmobility.autoapi.rooftop.SunroofTiltState;

import javax.annotation.Nullable;

/**
 * Command sent from the car every time the rooftop state changes or when a Get Rooftop State is
 * received.
 */
public class RooftopState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x01);
    static final byte DIMMING_IDENTIFIER = 0x01;
    static final byte OPEN_IDENTIFIER = 0x02;

    public static final byte IDENTIFIER_CONVERTIBLE_ROOF = 0x03;
    public static final byte IDENTIFIER_SUNROOF_TILT = 0x04;

    Float dimmingPercentage;
    Float openPercentage;
    ConvertibleRoofState convertibleRoofState;
    SunroofTiltState sunroofTiltState;

    /**
     * @return The dim percentage of the rooftop.
     */
    @Nullable public Float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     * @return The percentage of how much the rooftop is open.
     */
    @Nullable public Float getOpenPercentage() {
        return openPercentage;
    }

    /**
     * @return The convertible roof state.
     */
    @Nullable public ConvertibleRoofState getConvertibleRoofState() {
        return convertibleRoofState;
    }

    /**
     * @return The sunroof tilt state.
     */
    @Nullable public SunroofTiltState getSunroofTiltState() {
        return sunroofTiltState;
    }

    RooftopState(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            switch (property.getPropertyIdentifier()) {
                case DIMMING_IDENTIFIER:
                    dimmingPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case OPEN_IDENTIFIER:
                    openPercentage = Property.getUnsignedInt(property.getValueByte()) / 100f;
                    break;
                case IDENTIFIER_CONVERTIBLE_ROOF:
                    convertibleRoofState = ConvertibleRoofState.fromByte(property.getValueByte());
                    break;
                case IDENTIFIER_SUNROOF_TILT:
                    sunroofTiltState = SunroofTiltState.fromByte(property.getValueByte());
                    break;
            }
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private RooftopState(Builder builder) {
        super(builder);
        openPercentage = builder.openPercentage;
        dimmingPercentage = builder.dimmingPercentage;
        convertibleRoofState = builder.convertibleRoofState;
        sunroofTiltState = builder.sunroofTiltState;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Float openPercentage;
        private Float dimmingPercentage;
        private ConvertibleRoofState convertibleRoofState;
        private SunroofTiltState sunroofTiltState;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param openPercentage The open percentage.
         * @return The builder.
         */
        public Builder setOpenPercentage(Float openPercentage) {
            this.openPercentage = openPercentage;
            addProperty(new PercentageProperty(OPEN_IDENTIFIER, openPercentage));
            return this;
        }

        /**
         * @param dimmingPercentage The dimming percentage.
         * @return The builder.
         */
        public Builder setDimmingPercentage(Float dimmingPercentage) {
            this.dimmingPercentage = dimmingPercentage;
            addProperty(new PercentageProperty(DIMMING_IDENTIFIER, dimmingPercentage));
            return this;
        }

        public Builder setConvertibleRoofState(ConvertibleRoofState convertibleRoofState) {
            this.convertibleRoofState = convertibleRoofState;
            addProperty(new Property(IDENTIFIER_CONVERTIBLE_ROOF, convertibleRoofState.getByte()));
            return this;
        }

        public Builder setSunroofTiltState(SunroofTiltState sunroofTiltState) {
            this.sunroofTiltState = sunroofTiltState;
            addProperty(new Property(IDENTIFIER_SUNROOF_TILT, sunroofTiltState.getByte()));
            return this;
        }

        public RooftopState build() {
            return new RooftopState(this);
        }
    }
}
