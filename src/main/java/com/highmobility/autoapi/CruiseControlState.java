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
import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.PropertyValueSingleByte;

/**
 * This message is sent when a Get Cruise Control State message is received by the car. The new
 * state is included in the message payload and may be the result of user, device or car triggered
 * action.
 */
public class CruiseControlState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CRUISE_CONTROL, 0x01);

    private static final byte IDENTIFIER_ACTIVE = 0x01;
    private static final byte IDENTIFIER_LIMITER = 0x02;
    private static final byte IDENTIFIER_TARGET_SPEED = 0x03;
    private static final byte IDENTIFIER_ADAPTIVE_ACTIVE = 0x04;
    private static final byte IDENTIFIER_ADAPTIVE_TARGET_SPEED = 0x05;

    Property<Boolean> active = new Property(Boolean.class, IDENTIFIER_ACTIVE);
    Property<Limiter> limiter = new Property(Limiter.class, IDENTIFIER_LIMITER);
    IntegerProperty targetSpeed = new IntegerProperty(IDENTIFIER_TARGET_SPEED, false);
    Property<Boolean> adaptiveActive = new Property(Boolean.class, IDENTIFIER_ADAPTIVE_ACTIVE);
    IntegerProperty adaptiveTargetSpeed = new IntegerProperty(IDENTIFIER_ADAPTIVE_TARGET_SPEED,
            false);

    /**
     * @return Whether the cruise control is active.
     */
    public Property<Boolean> isActive() {
        return active;
    }

    /**
     * @return The limiter.
     */
    public Property<Limiter> getLimiter() {
        return limiter;
    }

    /**
     * @return The cruise control target speed.
     */
    public Property<Integer> getTargetSpeed() {
        return targetSpeed;
    }

    /**
     * @return Whether the adaptive cruise control is active.
     */
    public Property<Boolean> isAdaptiveActive() {
        return adaptiveActive;
    }

    /**
     * @return The adaptive cruise control target speed.
     */
    public Property<Integer> getAdaptiveTargetSpeed() {
        return adaptiveTargetSpeed;
    }

    CruiseControlState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_ACTIVE:
                        return active.update(p);
                    case IDENTIFIER_LIMITER:
                        return limiter.update(p);
                    case IDENTIFIER_TARGET_SPEED:
                        return targetSpeed.update(p);
                    case IDENTIFIER_ADAPTIVE_ACTIVE:
                        return adaptiveActive.update(p);
                    case IDENTIFIER_ADAPTIVE_TARGET_SPEED:
                        return adaptiveTargetSpeed.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum Limiter implements PropertyValueSingleByte {
        NOT_SET((byte) 0x00),
        HIGHER_SPEED_REQUESTED((byte) 0x01),
        LOWER_SPEED_REQUESTED((byte) 0x02),
        SPEED_FIXED((byte) 0x03);

        public static Limiter fromByte(byte value) throws CommandParseException {
            Limiter[] values = Limiter.values();

            for (int i = 0; i < values.length; i++) {
                Limiter value1 = values[i];
                if (value1.getByte() == value) {
                    return value1;
                }
            }

            throw new CommandParseException();
        }

        private byte value;

        Limiter(byte value) {
            this.value = value;
        }

        public byte getByte() {
            return value;
        }
    }
}
