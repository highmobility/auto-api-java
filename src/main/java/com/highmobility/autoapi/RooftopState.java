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

import com.highmobility.autoapi.property.FloatProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.value.Position;
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
    public static final byte IDENTIFIER_SUNROOF_POSITION = 0x05;

    PercentageProperty dimmingPercentage;
    PercentageProperty openPercentage;
    ConvertibleRoofState convertibleRoofState;
    SunroofTiltState sunroofTiltState;
    Position sunroofPosition;

    /**
     * @return The dim percentage of the rooftop.
     */
    @Nullable public PercentageProperty getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     * @return The percentage of how much the rooftop is open.
     */
    @Nullable public PercentageProperty getOpenPercentage() {
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

    /**
     * @return The sunroof position.
     */
    @Nullable public Position getSunroofPosition() {
        return sunroofPosition;
    }

    RooftopState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case DIMMING_IDENTIFIER:
                        dimmingPercentage = new PercentageProperty(p);
                        return dimmingPercentage;
                    case OPEN_IDENTIFIER:
                        openPercentage = new PercentageProperty(p);
                        return openPercentage;
                    case IDENTIFIER_CONVERTIBLE_ROOF:
                        convertibleRoofState = ConvertibleRoofState.fromByte(p.getValueByte());
                        return convertibleRoofState;
                    case IDENTIFIER_SUNROOF_TILT:
                        sunroofTiltState = SunroofTiltState.fromByte(p.getValueByte());
                        return sunroofTiltState;
                    case IDENTIFIER_SUNROOF_POSITION:
                        sunroofPosition = Position.fromByte(p.getValueByte());
                        return sunroofPosition;
                }

                return null;
            });
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
        sunroofPosition = builder.sunroofPosition;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private PercentageProperty openPercentage;
        private PercentageProperty dimmingPercentage;
        private ConvertibleRoofState convertibleRoofState;
        private SunroofTiltState sunroofTiltState;
        private Position sunroofPosition;

        public Builder() {
            super(TYPE);
        }

        /**
         * @param openPercentage The open percentage.
         * @return The builder.
         */
        public Builder setOpenPercentage(PercentageProperty openPercentage) {
            this.openPercentage = openPercentage;
            openPercentage.setIdentifier(OPEN_IDENTIFIER);
            addProperty(openPercentage);
            return this;
        }

        /**
         * @param dimmingPercentage The dimming percentage.
         * @return The builder.
         */
        public Builder setDimmingPercentage(PercentageProperty dimmingPercentage) {
            this.dimmingPercentage = dimmingPercentage;
            dimmingPercentage.setIdentifier(DIMMING_IDENTIFIER);
            addProperty(dimmingPercentage);
            return this;
        }

        /**
         * @param convertibleRoofState The convertible roof state.
         * @return The builder.
         */
        public Builder setConvertibleRoofState(ConvertibleRoofState convertibleRoofState) {
            this.convertibleRoofState = convertibleRoofState;
            addProperty(new Property(IDENTIFIER_CONVERTIBLE_ROOF, convertibleRoofState.getByte()));
            return this;
        }

        /**
         * @param sunroofTiltState The sunroof tilt state.
         * @return The builder.
         */
        public Builder setSunroofTiltState(SunroofTiltState sunroofTiltState) {
            this.sunroofTiltState = sunroofTiltState;
            addProperty(new Property(IDENTIFIER_SUNROOF_TILT, sunroofTiltState.getByte()));
            return this;
        }

        /**
         * @param sunroofPosition The sunroof position.
         * @return The builder.
         */
        public Builder setSunroofPosition(Position sunroofPosition) {
            this.sunroofPosition = sunroofPosition;
            addProperty(new Property(IDENTIFIER_SUNROOF_POSITION, sunroofPosition.getByte()));
            return this;
        }

        public RooftopState build() {
            return new RooftopState(this);
        }
    }
}
