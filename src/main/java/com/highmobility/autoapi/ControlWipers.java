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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.windscreen.WiperIntensity;
import com.highmobility.autoapi.value.windscreen.WiperState;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Control the wipers. The result is sent through the Windscreen State message.
 */
public class ControlWipers extends Command {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x14);

    private static final byte IDENTIFIER_WIPER_STATE = 0x01;
    private static final byte IDENTIFIER_WIPER_INTENSITY = 0x02;

    private Property<WiperState> state = new Property(WiperState.class, IDENTIFIER_WIPER_STATE);
    private Property<WiperIntensity> intensity = new Property(WiperIntensity.class,
            IDENTIFIER_WIPER_INTENSITY);

    /**
     * @return The wipers state.
     */
    public Property<WiperState> getState() {
        return state;
    }

    /**
     * @return The wipers intensity.
     */
    public Property<WiperIntensity> getIntensity() {
        return intensity;
    }

    public ControlWipers(WiperState state, @Nullable WiperIntensity intensity) {
        super(TYPE);
        List<Property> builder = new ArrayList<>();
        this.state.update(state);
        builder.add(this.state);

        if (intensity != null) {
            this.intensity.update(intensity);
            builder.add(this.intensity);
        }

        createBytes(builder);
    }

    ControlWipers(byte[] bytes) {
        super(bytes);

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WIPER_STATE:
                        return state.update(p);
                    case IDENTIFIER_WIPER_INTENSITY:
                        return intensity.update(p);
                }
                return null;
            });
        }
    }

    @Override protected boolean propertiesExpected() {
        return true;
    }
}
