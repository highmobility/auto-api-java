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

import com.highmobility.autoapi.RooftopControlState.ConvertibleRoofState;
import com.highmobility.autoapi.RooftopControlState.SunroofState;
import com.highmobility.autoapi.RooftopControlState.SunroofTiltState;
import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Control rooftop
 */
public class ControlRooftop extends SetCommand {
    public static final Identifier identifier = Identifier.ROOFTOP_CONTROL;

    @Nullable Property<Double> dimming = new Property(Double.class, 0x01);
    @Nullable Property<Double> position = new Property(Double.class, 0x02);
    @Nullable Property<ConvertibleRoofState> convertibleRoofState = new Property(ConvertibleRoofState.class, 0x03);
    @Nullable Property<SunroofTiltState> sunroofTiltState = new Property(SunroofTiltState.class, 0x04);
    @Nullable Property<SunroofState> sunroofState = new Property(SunroofState.class, 0x05);

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
     * @param dimming 100% is opaque, 0% is transparent
     * @param position 100% is fully open, 0% is closed
     * @param convertibleRoofState The convertible roof state
     * @param sunroofTiltState The sunroof tilt state
     * @param sunroofState The sunroof state
     */
    public ControlRooftop(@Nullable Double dimming, @Nullable Double position, @Nullable ConvertibleRoofState convertibleRoofState, @Nullable SunroofTiltState sunroofTiltState, @Nullable SunroofState sunroofState) {
        super(identifier);
    
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
                    case 0x01: return dimming.update(p);
                    case 0x02: return position.update(p);
                    case 0x03: return convertibleRoofState.update(p);
                    case 0x04: return sunroofTiltState.update(p);
                    case 0x05: return sunroofState.update(p);
                }
                return null;
            });
        }
        if (this.dimming.getValue() == null && this.position.getValue() == null && this.convertibleRoofState.getValue() == null && this.sunroofTiltState.getValue() == null && this.sunroofState.getValue() == null) throw new NoPropertiesException();
    }
}