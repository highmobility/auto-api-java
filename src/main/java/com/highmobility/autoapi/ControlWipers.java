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

import com.highmobility.autoapi.WindscreenState.WipersIntensity;
import com.highmobility.autoapi.WindscreenState.WipersStatus;
import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Control wipers
 */
public class ControlWipers extends SetCommand {
    public static final Integer IDENTIFIER = Identifier.WINDSCREEN;

    public static final byte IDENTIFIER_WIPERS_STATUS = 0x01;
    public static final byte IDENTIFIER_WIPERS_INTENSITY = 0x02;

    Property<WipersStatus> wipersStatus = new Property(WipersStatus.class, IDENTIFIER_WIPERS_STATUS);
    @Nullable Property<WipersIntensity> wipersIntensity = new Property(WipersIntensity.class, IDENTIFIER_WIPERS_INTENSITY);

    /**
     * @return The wipers status
     */
    public Property<WipersStatus> getWipersStatus() {
        return wipersStatus;
    }
    
    /**
     * @return The wipers intensity
     */
    public @Nullable Property<WipersIntensity> getWipersIntensity() {
        return wipersIntensity;
    }
    
    /**
     * Control wipers
     *
     * @param wipersStatus The wipers status
     * @param wipersIntensity The wipers intensity
     */
    public ControlWipers(WipersStatus wipersStatus, @Nullable WipersIntensity wipersIntensity) {
        super(IDENTIFIER);
    
        addProperty(this.wipersStatus.update(wipersStatus));
        addProperty(this.wipersIntensity.update(wipersIntensity));
        createBytes();
    }

    ControlWipers(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_WIPERS_STATUS: return wipersStatus.update(p);
                    case IDENTIFIER_WIPERS_INTENSITY: return wipersIntensity.update(p);
                }
                return null;
            });
        }
        if (this.wipersStatus.getValue() == null) 
            throw new NoPropertiesException();
    }
}