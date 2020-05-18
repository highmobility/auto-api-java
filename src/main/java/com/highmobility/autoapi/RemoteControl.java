/*
 * The MIT License
 * 
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import com.highmobility.autoapi.capability.DisabledIn;
import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import com.highmobility.value.Bytes;
import javax.annotation.Nullable;

/**
 * The Remote Control capability
 */
public class RemoteControl {
    public static final int IDENTIFIER = Identifier.REMOTE_CONTROL;

    public static final byte PROPERTY_CONTROL_MODE = 0x01;
    public static final byte PROPERTY_ANGLE = 0x02;
    public static final byte PROPERTY_SPEED = 0x03;

    public static final DisabledIn[] disabledIn = new DisabledIn[] { DisabledIn.WEB };

    /**
     * Get control state
     */
    public static class GetControlState extends GetCommand {
        public GetControlState() {
            super(State.class, IDENTIFIER);
        }
    
        GetControlState(byte[] bytes) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * The remote control state
     */
    public static class State extends SetCommand {
        Property<ControlMode> controlMode = new Property(ControlMode.class, PROPERTY_CONTROL_MODE);
        PropertyInteger angle = new PropertyInteger(PROPERTY_ANGLE, true);
    
        /**
         * @return The control mode
         */
        public Property<ControlMode> getControlMode() {
            return controlMode;
        }
    
        /**
         * @return Wheel base angle in degrees
         */
        public PropertyInteger getAngle() {
            return angle;
        }
    
        State(byte[] bytes) throws CommandParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CONTROL_MODE: return controlMode.update(p);
                        case PROPERTY_ANGLE: return angle.update(p);
                    }
    
                    return null;
                });
            }
        }
    }

    /**
     * Control command
     */
    public static class ControlCommand extends SetCommand {
        PropertyInteger angle = new PropertyInteger(PROPERTY_ANGLE, true);
        PropertyInteger speed = new PropertyInteger(PROPERTY_SPEED, true);
    
        /**
         * @return The angle
         */
        public PropertyInteger getAngle() {
            return angle;
        }
        
        /**
         * @return The speed
         */
        public PropertyInteger getSpeed() {
            return speed;
        }
        
        /**
         * Control command
         *
         * @param angle Wheel base angle in degrees
         * @param speed Speed in km/h
         */
        public ControlCommand(@Nullable Integer angle, @Nullable Integer speed) {
            super(IDENTIFIER);
        
            addProperty(this.angle.update(true, 2, angle));
            addProperty(this.speed.update(true, 1, speed));
            if (this.angle.getValue() == null && this.speed.getValue() == null) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlCommand(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_ANGLE: return angle.update(p);
                        case PROPERTY_SPEED: return speed.update(p);
                    }
                    return null;
                });
            }
            if (this.angle.getValue() == null && this.speed.getValue() == null) throw new NoPropertiesException();
        }
    }
    
    /**
     * Start control
     */
    public static class StartControl extends SetCommand {
        Property<ControlMode> controlMode = new Property(ControlMode.class, PROPERTY_CONTROL_MODE);
    
        /**
         * Start control
         */
        public StartControl() {
            super(IDENTIFIER);
        
            addProperty(controlMode.addValueComponent(new Bytes("02")));
            createBytes();
        }
    
        StartControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CONTROL_MODE: controlMode.update(p);
                    }
                    return null;
                });
            }
            if ((controlMode.getValue() == null || controlMode.getValueComponent().getValueBytes().equals(new Bytes("02")) == false)) 
                throw new NoPropertiesException();
        }
    }
    
    /**
     * Stop control
     */
    public static class StopControl extends SetCommand {
        Property<ControlMode> controlMode = new Property(ControlMode.class, PROPERTY_CONTROL_MODE);
    
        /**
         * Stop control
         */
        public StopControl() {
            super(IDENTIFIER);
        
            addProperty(controlMode.addValueComponent(new Bytes("05")));
            createBytes();
        }
    
        StopControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNext(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_CONTROL_MODE: controlMode.update(p);
                    }
                    return null;
                });
            }
            if ((controlMode.getValue() == null || controlMode.getValueComponent().getValueBytes().equals(new Bytes("05")) == false)) 
                throw new NoPropertiesException();
        }
    }

    public enum ControlMode implements ByteEnum {
        UNAVAILABLE((byte) 0x00),
        AVAILABLE((byte) 0x01),
        STARTED((byte) 0x02),
        FAILED_TO_START((byte) 0x03),
        ABORTED((byte) 0x04),
        ENDED((byte) 0x05);
    
        public static ControlMode fromByte(byte byteValue) throws CommandParseException {
            ControlMode[] values = ControlMode.values();
    
            for (int i = 0; i < values.length; i++) {
                ControlMode state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        ControlMode(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}