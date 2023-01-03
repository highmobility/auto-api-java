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
import com.highmobility.autoapi.value.WindowLocation;
import com.highmobility.autoapi.value.WindowOpenPercentage;
import com.highmobility.autoapi.value.WindowPosition;
import com.highmobility.value.Bytes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/**
 * The Windows capability
 */
public class Windows {
    public static final int IDENTIFIER = Identifier.WINDOWS;

    public static final byte PROPERTY_OPEN_PERCENTAGES = 0x02;
    public static final byte PROPERTY_POSITIONS = 0x03;

    /**
     * Get Windows property availability information
     */
    public static class GetWindowsAvailability extends GetAvailabilityCommand {
        /**
         * Get every Windows property availability
         */
        public GetWindowsAvailability() {
            super(IDENTIFIER);
        }
    
        /**
         * Get specific Windows property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetWindowsAvailability(Bytes propertyIdentifiers) {
            super(IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Windows property availabilities
         * 
         * @param propertyIdentifiers The property identifierBytes
         */
        public GetWindowsAvailability(byte... propertyIdentifiers) {
            super(IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetWindowsAvailability(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(bytes);
        }
    }

    /**
     * Get windows
     */
    public static class GetWindows extends GetCommand<State> {
        /**
         * Get all Windows properties
         */
        public GetWindows() {
            super(State.class, IDENTIFIER);
        }
    
        /**
         * Get specific Windows properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetWindows(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * Get specific Windows properties
         * 
         * @param propertyIdentifiers The property identifiers
         */
        public GetWindows(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetWindows(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }
    
    /**
     * Get specific Windows properties
     * 
     * @deprecated use {@link GetWindows#GetWindows(byte...)} instead
     */
    @Deprecated
    public static class GetWindowsProperties extends GetCommand<State> {
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetWindowsProperties(Bytes propertyIdentifiers) {
            super(State.class, IDENTIFIER, propertyIdentifiers);
        }
    
        /**
         * @param propertyIdentifiers The property identifiers
         */
        public GetWindowsProperties(byte... propertyIdentifiers) {
            super(State.class, IDENTIFIER, new Bytes(propertyIdentifiers));
        }
    
        GetWindowsProperties(byte[] bytes, @SuppressWarnings("unused") boolean fromRaw) throws CommandParseException {
            super(State.class, bytes);
        }
    }

    /**
     * Control windows
     */
    public static class ControlWindows extends SetCommand {
        List<Property<WindowOpenPercentage>> openPercentages;
        List<Property<WindowPosition>> positions;
    
        /**
         * @return The open percentages
         */
        public List<Property<WindowOpenPercentage>> getOpenPercentages() {
            return openPercentages;
        }
        
        /**
         * @return The positions
         */
        public List<Property<WindowPosition>> getPositions() {
            return positions;
        }
        
        /**
         * Control windows
         * 
         * @param openPercentages The open percentages
         * @param positions The positions
         */
        public ControlWindows(@Nullable List<WindowOpenPercentage> openPercentages, @Nullable List<WindowPosition> positions) {
            super(IDENTIFIER);
        
            final ArrayList<Property<WindowOpenPercentage>> openPercentagesBuilder = new ArrayList<>();
            if (openPercentages != null) {
                for (WindowOpenPercentage openPercentage : openPercentages) {
                    Property<WindowOpenPercentage> prop = new Property<>(0x02, openPercentage);
                    openPercentagesBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.openPercentages = openPercentagesBuilder;
            
            final ArrayList<Property<WindowPosition>> positionsBuilder = new ArrayList<>();
            if (positions != null) {
                for (WindowPosition position : positions) {
                    Property<WindowPosition> prop = new Property<>(0x03, position);
                    positionsBuilder.add(prop);
                    addProperty(prop);
                }
            }
            this.positions = positionsBuilder;
            if (this.openPercentages.size() == 0 && this.positions.size() == 0) throw new IllegalArgumentException();
            createBytes();
        }
    
        ControlWindows(byte[] bytes) throws PropertyParseException {
            super(bytes);
        
            final ArrayList<Property<WindowOpenPercentage>> openPercentagesBuilder = new ArrayList<>();
            final ArrayList<Property<WindowPosition>> positionsBuilder = new ArrayList<>();
        
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextSetter(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_OPEN_PERCENTAGES: {
                            Property<WindowOpenPercentage> openPercentage = new Property<>(WindowOpenPercentage.class, p);
                            openPercentagesBuilder.add(openPercentage);
                            return openPercentage;
                        }
                        case PROPERTY_POSITIONS: {
                            Property<WindowPosition> position = new Property<>(WindowPosition.class, p);
                            positionsBuilder.add(position);
                            return position;
                        }
                    }
        
                    return null;
                });
            }
        
            openPercentages = openPercentagesBuilder;
            positions = positionsBuilder;
            if (this.openPercentages.size() == 0 && this.positions.size() == 0) {
                throw new PropertyParseException(optionalPropertyErrorMessage(getClass()));
            }
        }
    }

    /**
     * The windows state
     */
    public static class State extends SetCommand {
        List<Property<WindowOpenPercentage>> openPercentages;
        List<Property<WindowPosition>> positions;
    
        /**
         * @return The open percentages
         */
        public List<Property<WindowOpenPercentage>> getOpenPercentages() {
            return openPercentages;
        }
    
        /**
         * @return The positions
         */
        public List<Property<WindowPosition>> getPositions() {
            return positions;
        }
    
        /**
         * @param location The window location.
         * @return The window position.
         */
        @Nullable public Property<WindowPosition> getPosition(WindowLocation location) {
            for (Property<WindowPosition> windowPosition : positions) {
                if (windowPosition.getValue() != null && windowPosition.getValue().getLocation() == location)
                    return windowPosition;
            }
            return null;
        }
    
        /**
         * @param location The window location.
         * @return The window open percentage.
         */
        @Nullable public Property<WindowOpenPercentage> getOpenPercentage(WindowLocation location) {
            for (Property<WindowOpenPercentage> windowOpenPercentage : openPercentages) {
                if (windowOpenPercentage.getValue() != null && windowOpenPercentage.getValue().getLocation() == location)
                    return windowOpenPercentage;
            }
            return null;
        }
    
        State(byte[] bytes) {
            super(bytes);
    
            final ArrayList<Property<WindowOpenPercentage>> openPercentagesBuilder = new ArrayList<>();
            final ArrayList<Property<WindowPosition>> positionsBuilder = new ArrayList<>();
    
            while (propertyIterator.hasNext()) {
                propertyIterator.parseNextState(p -> {
                    switch (p.getPropertyIdentifier()) {
                        case PROPERTY_OPEN_PERCENTAGES:
                            Property<WindowOpenPercentage> openPercentage = new Property<>(WindowOpenPercentage.class, p);
                            openPercentagesBuilder.add(openPercentage);
                            return openPercentage;
                        case PROPERTY_POSITIONS:
                            Property<WindowPosition> position = new Property<>(WindowPosition.class, p);
                            positionsBuilder.add(position);
                            return position;
                    }
    
                    return null;
                });
            }
    
            openPercentages = openPercentagesBuilder;
            positions = positionsBuilder;
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
             * Add an array of open percentages
             * 
             * @param openPercentages The open percentages
             * @return The builder
             */
            public Builder setOpenPercentages(Property<WindowOpenPercentage>[] openPercentages) {
                for (int i = 0; i < openPercentages.length; i++) {
                    addOpenPercentage(openPercentages[i]);
                }
            
                return this;
            }
            
            /**
             * Add a single open percentage
             * 
             * @param openPercentage The open percentage
             * @return The builder
             */
            public Builder addOpenPercentage(Property<WindowOpenPercentage> openPercentage) {
                openPercentage.setIdentifier(PROPERTY_OPEN_PERCENTAGES);
                addProperty(openPercentage);
                return this;
            }
            
            /**
             * Add an array of positions
             * 
             * @param positions The positions
             * @return The builder
             */
            public Builder setPositions(Property<WindowPosition>[] positions) {
                for (int i = 0; i < positions.length; i++) {
                    addPosition(positions[i]);
                }
            
                return this;
            }
            /**
             * Add a single position
             * 
             * @param position The position
             * @return The builder
             */
            public Builder addPosition(Property<WindowPosition> position) {
                position.setIdentifier(PROPERTY_POSITIONS);
                addProperty(position);
                return this;
            }
        }
    }
}