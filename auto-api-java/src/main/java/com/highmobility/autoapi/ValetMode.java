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

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;

/**
 * The Valet Mode capability
 */
public class ValetMode {
    public static final int IDENTIFIER = Identifier.VALET_MODE;

    public static final byte PROPERTY_STATUS = 0x01;

    /**
     * Get Valet Mode property availability information
     */
    public static class GetValetModeAvailability extends GetAvailabilityCommand {
        /**
         * Get every Valet Mode property availability
         */
        public GetValetModeAvailability() {
            super(IDENTIFIER);
        }
    
        GetValetModeAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get valet mode
     */
    public static class GetValetMode extends GetCommand<State> {
        /**
         * Get all Valet Mode properties
         */
        public GetValetMode() {
            super(State.class, IDENTIFIER);
        }
    
        GetValetMode(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Activate deactivate valet mode
     */
    public static class ActivateDeactivateValetMode extends SetCommand {
        Property<ActiveState> status = new Property<>(ActiveState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
        
        /**
         * Activate deactivate valet mode
         * 
         * @param status The status
         */
        public ActivateDeactivateValetMode(ActiveState status) {
            super(IDENTIFIER);
        
            addProperty(this.status.update(status));
            createBytes();
        }
    
        ActivateDeactivateValetMode(byte[] bytes) throws PropertyParseException {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    if (p.getPropertyIdentifier() == PROPERTY_STATUS) return status.update(p);
                    
                    return null;
                });
            }
            if (this.status.getValue() == null) {
                throw new PropertyParseException(mandatoryPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The valet mode state
     */
    public static class State extends SetCommand {
        Property<ActiveState> status = new Property<>(ActiveState.class, PROPERTY_STATUS);
    
        /**
         * @return The status
         */
        public Property<ActiveState> getStatus() {
            return status;
        }
    
        State(byte[] bytes) {
            super(bytes);
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_STATUS: return status.update(p);
                    }
    
                    return null;
                });
            }
        }
    
        public static final class Builder extends SetCommand.Builder<Builder> {
            public Builder() {
                super(IDENTIFIER);
            }
    
            public State build() {
                SetCommand baseSetCommand = super.build();
                Command resolved = CommandResolver.resolve(baseSetCommand.getByteArray());
                return (State) resolved;
            }
    
            /**
             * @param status The status
             * @return The builder
             */
            public Builder setStatus(Property<ActiveState> status) {
                Property property = status.setIdentifier(PROPERTY_STATUS);
                addProperty(property);
                return this;
            }
        }
    }
}