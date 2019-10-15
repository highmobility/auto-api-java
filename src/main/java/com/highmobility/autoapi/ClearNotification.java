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

import com.highmobility.autoapi.NotificationsState.Clear;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * Clear notification
 */
public class ClearNotification extends SetCommand {
    public static final Identifier IDENTIFIER = Identifier.NOTIFICATIONS;

    public static final byte IDENTIFIER_CLEAR = 0x04;

    Property<Clear> clear = new Property(Clear.class, IDENTIFIER_CLEAR);

    /**
     * Clear notification
     */
    public ClearNotification() {
        super(IDENTIFIER);
    
        addProperty(clear.addValueComponent(new Bytes("00")), true);
    }

    ClearNotification(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_CLEAR: clear.update(p);
                }
                return null;
            });
        }
        if ((clear.getValue() == null || clear.getValueComponent().getValueBytes().equals(new Bytes("00")) == false)) 
            throw new NoPropertiesException();
    }
}