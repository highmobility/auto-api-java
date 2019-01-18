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

import com.highmobility.autoapi.property.ConvertibleRoofStateProperty;
import com.highmobility.autoapi.property.PercentageProperty;
import com.highmobility.autoapi.property.PositionProperty;
import com.highmobility.autoapi.property.SunroofTiltStateProperty;

import javax.annotation.Nullable;

/**
 * Command sent from the car every time the rooftop state changes or when a Get Rooftop State is
 * received.
 */
public class RooftopState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x01);

    static final byte DIMMING_IDENTIFIER = 0x01;
    static final byte OPEN_IDENTIFIER = 0x02;
    private static final byte IDENTIFIER_CONVERTIBLE_ROOF = 0x03;
    private static final byte IDENTIFIER_SUNROOF_TILT = 0x04;
    private static final byte IDENTIFIER_SUNROOF_POSITION = 0x05;

    PercentageProperty dimmingPercentage = new PercentageProperty(DIMMING_IDENTIFIER);
    PercentageProperty openPercentage = new PercentageProperty(OPEN_IDENTIFIER);
    ConvertibleRoofStateProperty convertibleRoofState =
            new ConvertibleRoofStateProperty(IDENTIFIER_CONVERTIBLE_ROOF);
    SunroofTiltStateProperty sunroofTiltState =
            new SunroofTiltStateProperty(IDENTIFIER_SUNROOF_TILT);
    PositionProperty sunroofPosition = new PositionProperty(IDENTIFIER_SUNROOF_POSITION);

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
    @Nullable public ConvertibleRoofStateProperty getConvertibleRoofState() {
        return convertibleRoofState;
    }

    /**
     * @return The sunroof tilt state.
     */
    @Nullable public SunroofTiltStateProperty getSunroofTiltState() {
        return sunroofTiltState;
    }

    /**
     * @return The sunroof position.
     */
    @Nullable public PositionProperty getSunroofPosition() {
        return sunroofPosition;
    }

    RooftopState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                // can update with failure, timestamp or the real property
                switch (p.getPropertyIdentifier()) {
                    case DIMMING_IDENTIFIER:
                        return dimmingPercentage.update(p);
                    case OPEN_IDENTIFIER:
                        return openPercentage.update(p);
                    case IDENTIFIER_CONVERTIBLE_ROOF:
                        return convertibleRoofState.update(p);
                    case IDENTIFIER_SUNROOF_TILT:
                        return sunroofTiltState.update(p);
                    case IDENTIFIER_SUNROOF_POSITION:
                        return sunroofPosition.update(p);
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
        private ConvertibleRoofStateProperty convertibleRoofState;
        private SunroofTiltStateProperty sunroofTiltState;
        private PositionProperty sunroofPosition;

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
        public Builder setConvertibleRoofState(ConvertibleRoofStateProperty convertibleRoofState) {
            this.convertibleRoofState = convertibleRoofState;
            convertibleRoofState.setIdentifier(IDENTIFIER_CONVERTIBLE_ROOF);
            addProperty(convertibleRoofState);
            return this;
        }

        /**
         * @param sunroofTiltState The sunroof tilt state.
         * @return The builder.
         */
        public Builder setSunroofTiltState(SunroofTiltStateProperty sunroofTiltState) {
            this.sunroofTiltState = sunroofTiltState;
            sunroofTiltState.setIdentifier(IDENTIFIER_SUNROOF_TILT);
            addProperty(sunroofTiltState);
            return this;
        }

        /**
         * @param sunroofPosition The sunroof position.
         * @return The builder.
         */
        public Builder setSunroofPosition(PositionProperty sunroofPosition) {
            this.sunroofPosition = sunroofPosition;
            sunroofPosition.setIdentifier(IDENTIFIER_SUNROOF_POSITION);
            addProperty(sunroofPosition);
            return this;
        }

        public RooftopState build() {
            return new RooftopState(this);
        }
    }
}
