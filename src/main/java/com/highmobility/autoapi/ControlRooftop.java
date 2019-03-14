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

import com.highmobility.autoapi.value.ConvertibleRoofState;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Position;
import com.highmobility.autoapi.value.SunroofTiltState;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Set the rooftop state. The result is sent through the evented Rooftop State command.
 */
public class ControlRooftop extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.ROOFTOP, 0x12);

    private static final byte IDENTIFIER_CONVERTIBLE_ROOFTOP = 0x03;
    private static final byte IDENTIFIER_SUNROOF_TILT = 0x04;
    private static final byte IDENTIFIER_SUNROOF_POSITION = 0x05;

    private Property<Double> dimmingPercentage =
            new Property<>(Double.class, RooftopState.DIMMING_IDENTIFIER);

    private Property<Double> openPercentage =
            new Property<>(Double.class, RooftopState.OPEN_IDENTIFIER);

    private Property<ConvertibleRoofState> convertibleRoofState =
            new Property<>(ConvertibleRoofState.class, IDENTIFIER_CONVERTIBLE_ROOFTOP);
    private Property<SunroofTiltState> sunroofTiltState =
            new Property<>(SunroofTiltState.class, IDENTIFIER_SUNROOF_TILT);

    private Property<Position> sunroofPosition = new Property<>(Position.class,
            IDENTIFIER_SUNROOF_POSITION);

    /**
     * @return The dimming percentage.
     */
    public Property<Double> getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     * @return The open percentage.
     */

    public Property<Double> getOpenPercentage() {
        return openPercentage;
    }

    /**
     * @return The convertible roof state.
     */
    public Property<ConvertibleRoofState> getConvertibleRoofState() {
        return convertibleRoofState;
    }

    /**
     * @return The sunroof tilt state.
     */
    public Property<SunroofTiltState> getSunroofTiltState() {
        return sunroofTiltState;
    }

    /**
     * @return The sunroof position.
     */
    public Property<Position> getSunroofPosition() {
        return sunroofPosition;
    }

    /**
     * @param dimmingPercentage    The dimming percentage.
     * @param openPercentage       The rooftop open percentage.
     * @param convertibleRoofState The convertible roof state.
     * @param sunroofTiltState     The sunroof tilt state.
     * @param sunroofPosition      The sunroof position.
     */

    public ControlRooftop(@Nullable Double dimmingPercentage,
                          @Nullable Double openPercentage,
                          @Nullable ConvertibleRoofState convertibleRoofState,
                          @Nullable SunroofTiltState sunroofTiltState,
                          @Nullable Position sunroofPosition) {
        super(TYPE);

        List<Property> properties = new ArrayList<>();

        if (dimmingPercentage != null) {
            properties.add(this.dimmingPercentage.update(dimmingPercentage));
        }

        if (openPercentage != null) {
            properties.add(this.openPercentage.update(openPercentage));
        }

        if (convertibleRoofState != null) {
            properties.add(this.convertibleRoofState.update(convertibleRoofState));
        }

        if (sunroofTiltState != null) {
            properties.add(this.sunroofTiltState.update(sunroofTiltState));
        }

        if (sunroofPosition != null) {
            properties.add(this.sunroofPosition.update(sunroofPosition));
        }

        createBytes(properties);
    }

    ControlRooftop(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case RooftopState.DIMMING_IDENTIFIER:
                        return dimmingPercentage.update(p);
                    case RooftopState.OPEN_IDENTIFIER:
                        return openPercentage.update(p);
                    case IDENTIFIER_CONVERTIBLE_ROOFTOP:
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
}
