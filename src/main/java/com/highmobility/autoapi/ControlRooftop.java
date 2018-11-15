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

    private Float dimmingPercentage;
    private Float openPercentage;
    private ConvertibleRoofState convertibleRoofState;
    private SunroofTiltState sunroofTiltState;

    /**
     * @return The dimming percentage.
     */
    public Float getDimmingPercentage() {
        return dimmingPercentage;
    }

    /**
     * @return The open percentage.
     */
    public Float getOpenPercentage() {
        return openPercentage;
    }

    /**
     * @return The convertible roof state.
     */
    public ConvertibleRoofState getConvertibleRoofState() {
        return convertibleRoofState;
    }

    /**
     * @return The sunroof tilt state.
     */
    public SunroofTiltState getSunroofTiltState() {
        return sunroofTiltState;
    }

    /**
     * @param dimmingPercentage    The dimming percentage.
     * @param openPercentage       The rooftop open percentage.
     * @param convertibleRoofState The convertible roof state.
     * @param sunroofTiltState     The sunroof tilt state.
     */
    public ControlRooftop(@Nullable Float dimmingPercentage, @Nullable Float openPercentage,
                          @Nullable ConvertibleRoofState convertibleRoofState, @Nullable
                                  SunroofTiltState sunroofTiltState) {
        super(TYPE, getProperties(dimmingPercentage, openPercentage, convertibleRoofState,
                sunroofTiltState));
        this.dimmingPercentage = dimmingPercentage;
        this.openPercentage = openPercentage;
        this.convertibleRoofState = convertibleRoofState;
        this.sunroofTiltState = sunroofTiltState;
    }

    static Property[] getProperties(@Nullable Float dimmingPercentage, @Nullable Float
            openPercentage, @Nullable ConvertibleRoofState convertibleRoofState, @Nullable
                                              SunroofTiltState sunroofTiltState) {
        List<Property> properties = new ArrayList<>();

        if (dimmingPercentage != null) {
            Property prop = new PercentageProperty(RooftopState.DIMMING_IDENTIFIER,
                    dimmingPercentage);
            properties.add(prop);
        }

        if (openPercentage != null) {
            Property prop = new PercentageProperty(RooftopState.OPEN_IDENTIFIER, openPercentage);
            properties.add(prop);
        }

        if (convertibleRoofState != null) {

            properties.add(new Property(IDENTIFIER_CONVERTIBLE_ROOFTOP, convertibleRoofState.getByte()));
        }

        if (sunroofTiltState != null) {
            properties.add(new Property(IDENTIFIER_SUNROOF_TILT, sunroofTiltState.getByte()));
        }

        return properties.toArray(new Property[0]);
    }

    ControlRooftop(byte[] bytes) throws CommandParseException {
        super(bytes);

        for (Property prop : properties) {
            switch (prop.getPropertyIdentifier()) {
                case RooftopState.DIMMING_IDENTIFIER:
                    dimmingPercentage = Property.getUnsignedInt(prop.getValueByte()) / 100f;
                    break;
                case RooftopState.OPEN_IDENTIFIER:
                    openPercentage = Property.getUnsignedInt(prop.getValueByte()) / 100f;
                    break;
                case IDENTIFIER_CONVERTIBLE_ROOFTOP:
                    convertibleRoofState = ConvertibleRoofState.fromByte(prop.getValueByte());
                    break;
                case IDENTIFIER_SUNROOF_TILT:
                    sunroofTiltState = SunroofTiltState.fromByte(prop.getValueByte());
                    break;
            }
        }
    }
}
