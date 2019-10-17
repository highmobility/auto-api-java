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

import com.highmobility.autoapi.RooftopControlState.ConvertibleRoofState;
import com.highmobility.autoapi.RooftopControlState.SunroofState;
import com.highmobility.autoapi.RooftopControlState.SunroofTiltState;
import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Control rooftop
 */
public class ControlRooftop extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.ROOFTOP_CONTROL;

    public static final byte IDENTIFIER_DIMMING = 0x01;
    public static final byte IDENTIFIER_POSITION = 0x02;
    public static final byte IDENTIFIER_CONVERTIBLE_ROOF_STATE = 0x03;
    public static final byte IDENTIFIER_SUNROOF_TILT_STATE = 0x04;
    public static final byte IDENTIFIER_SUNROOF_STATE = 0x05;

    @Nullable Property<Double> dimming = new Property(Double.class, IDENTIFIER_DIMMING);
    @Nullable Property<Double> position = new Property(Double.class, IDENTIFIER_POSITION);
    @Nullable Property<ConvertibleRoofState> convertibleRoofState = new Property(ConvertibleRoofState.class, IDENTIFIER_CONVERTIBLE_ROOF_STATE);
    @Nullable Property<SunroofTiltState> sunroofTiltState = new Property(SunroofTiltState.class, IDENTIFIER_SUNROOF_TILT_STATE);
    @Nullable Property<SunroofState> sunroofState = new Property(SunroofState.class, IDENTIFIER_SUNROOF_STATE);

    /**
     * @return The dimming
     */
    public @Nullable Property<Double> getDimming() {
        return dimming;
    }
    
    /**
     * @return The position
     */
    public @Nullable Property<Double> getPosition() {
        return position;
    }
    
    /**
     * @return The convertible roof state
     */
    public @Nullable Property<ConvertibleRoofState> getConvertibleRoofState() {
        return convertibleRoofState;
    }
    
    /**
     * @return The sunroof tilt state
     */
    public @Nullable Property<SunroofTiltState> getSunroofTiltState() {
        return sunroofTiltState;
    }
    
    /**
     * @return The sunroof state
     */
    public @Nullable Property<SunroofState> getSunroofState() {
        return sunroofState;
    }
    
    /**
     * Control rooftop
     *
     * @param dimming 1.0 (100%) is opaque, 0.0 (0%) is transparent
     * @param position 1.0 (100%) is fully open, 0.0 (0%) is closed
     * @param convertibleRoofState The convertible roof state
     * @param sunroofTiltState The sunroof tilt state
     * @param sunroofState The sunroof state
     */
    public ControlRooftop(@Nullable Double dimming, @Nullable Double position, @Nullable ConvertibleRoofState convertibleRoofState, @Nullable SunroofTiltState sunroofTiltState, @Nullable SunroofState sunroofState) {
        super(IDENTIFIER);
    
        addProperty(this.dimming.update(dimming));
        addProperty(this.position.update(position));
        if (convertibleRoofState == ConvertibleRoofState.EMERGENCY_LOCKED ||
            convertibleRoofState == ConvertibleRoofState.CLOSED_SECURED ||
            convertibleRoofState == ConvertibleRoofState.OPEN_SECURED ||
            convertibleRoofState == ConvertibleRoofState.HARD_TOP_MOUNTED ||
            convertibleRoofState == ConvertibleRoofState.INTERMEDIATE_POSITION ||
            convertibleRoofState == ConvertibleRoofState.LOADING_POSITION ||
            convertibleRoofState == ConvertibleRoofState.LOADING_POSITION_IMMEDIATE) throw new IllegalArgumentException();
    
        addProperty(this.convertibleRoofState.update(convertibleRoofState));
        addProperty(this.sunroofTiltState.update(sunroofTiltState));
        addProperty(this.sunroofState.update(sunroofState), true);
        if (this.dimming.getValue() == null && this.position.getValue() == null && this.convertibleRoofState.getValue() == null && this.sunroofTiltState.getValue() == null && this.sunroofState.getValue() == null) throw new IllegalArgumentException();
    }

    ControlRooftop(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_DIMMING: return dimming.update(p);
                    case IDENTIFIER_POSITION: return position.update(p);
                    case IDENTIFIER_CONVERTIBLE_ROOF_STATE: return convertibleRoofState.update(p);
                    case IDENTIFIER_SUNROOF_TILT_STATE: return sunroofTiltState.update(p);
                    case IDENTIFIER_SUNROOF_STATE: return sunroofState.update(p);
                }
                return null;
            });
        }
        if (this.dimming.getValue() == null && this.position.getValue() == null && this.convertibleRoofState.getValue() == null && this.sunroofTiltState.getValue() == null && this.sunroofState.getValue() == null) throw new NoPropertiesException();
    }
}