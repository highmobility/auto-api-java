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

import com.highmobility.autoapi.property.IntegerProperty;
import com.highmobility.autoapi.property.ObjectProperty;

import javax.annotation.Nullable;

/**
 * This message is sent when a Get Cruise Control State message is received by the car. The new
 * state is included in the message payload and may be the result of user, device or car triggered
 * action.
 */
public class CruiseControlState extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.CRUISE_CONTROL, 0x01);

    private static final byte ACTIVE_IDENTIFIER = 0x01;
    private static final byte LIMITER_IDENTIFIER = 0x02;
    private static final byte TARGET_SPEED_IDENTIFIER = 0x03;
    private static final byte ADAPTIVE_ACTIVE_IDENTIFIER = 0x04;
    private static final byte ADAPTIVE_TARGET_SPEED_IDENTIFIER = 0x05;

    ObjectProperty<Boolean> active;
    Limiter limiter;
    IntegerProperty targetSpeed;
    ObjectProperty<Boolean> adaptiveActive;
    IntegerProperty adaptiveTargetSpeed;

    /**
     * @return Whether the cruise control is active.
     */
    @Nullable public ObjectProperty<Boolean> isActive() {
        return active;
    }

    /**
     * @return The limiter.
     */
    @Nullable public Limiter getLimiter() {
        return limiter;
    }

    /**
     * @return The cruise control target speed.
     */
    @Nullable public IntegerProperty getTargetSpeed() {
        return targetSpeed;
    }

    /**
     * @return Whether the adaptive cruise control is active.
     */
    @Nullable public ObjectProperty<Boolean> isAdaptiveActive() {
        return adaptiveActive;
    }

    /**
     * @return The adaptive cruise control target speed.
     */
    @Nullable public IntegerProperty getAdaptiveTargetSpeed() {
        return adaptiveTargetSpeed;
    }

    CruiseControlState(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case ACTIVE_IDENTIFIER:
                        active = new ObjectProperty<>(Boolean.class, p);
                        return active;
                    case LIMITER_IDENTIFIER:
                        limiter = Limiter.fromByte(p.getValueByte());
                        return limiter;
                    case TARGET_SPEED_IDENTIFIER:
                        targetSpeed = new IntegerProperty(p, false);
                        return targetSpeed;
                    case ADAPTIVE_ACTIVE_IDENTIFIER:
                        adaptiveActive = new ObjectProperty<>(Boolean.class, p);
                        return adaptiveActive;
                    case ADAPTIVE_TARGET_SPEED_IDENTIFIER:
                        adaptiveTargetSpeed = new IntegerProperty(p, false);
                        return adaptiveTargetSpeed;
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    public enum Limiter {
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
