// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.WindowOpenPercentage;
import com.highmobility.autoapi.v2.value.WindowPosition;
import java.util.ArrayList;
import java.util.List;

public class WindowsState extends Command {
    Property<WindowOpenPercentage> openPercentages[];
    Property<WindowPosition> positions[];

    /**
     * @return The open percentages
     */
    public Property<WindowOpenPercentage>[] getOpenPercentages() {
        return openPercentages;
    }

    /**
     * @return The positions
     */
    public Property<WindowPosition>[] getPositions() {
        return positions;
    }

    WindowsState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> openPercentagesBuilder = new ArrayList<>();
        ArrayList<Property> positionsBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02:
                        Property<WindowOpenPercentage> openPercentage = new Property(WindowOpenPercentage.class, p);
                        openPercentagesBuilder.add(openPercentage);
                        return openPercentage;
                    case 0x03:
                        Property<WindowPosition> position = new Property(WindowPosition.class, p);
                        positionsBuilder.add(position);
                        return position;
                }

                return null;
            });
        }

        openPercentages = openPercentagesBuilder.toArray(new Property[0]);
        positions = positionsBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private WindowsState(Builder builder) {
        super(builder);

        openPercentages = builder.openPercentages.toArray(new Property[0]);
        positions = builder.positions.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        private List<Property> openPercentages = new ArrayList<>();
        private List<Property> positions = new ArrayList<>();

        public Builder() {
            super(Identifier.WINDOWS);
        }

        public WindowsState build() {
            return new WindowsState(this);
        }

        /**
         * Add an array of open percentages.
         * 
         * @param openPercentages The open percentages
         * @return The builder
         */
        public Builder setOpenPercentages(Property<WindowOpenPercentage>[] openPercentages) {
            this.openPercentages.clear();
            for (int i = 0; i < openPercentages.length; i++) {
                addOpenPercentage(openPercentages[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single open percentage.
         * 
         * @param openPercentage The open percentage
         * @return The builder
         */
        public Builder addOpenPercentage(Property<WindowOpenPercentage> openPercentage) {
            openPercentage.setIdentifier(0x02);
            addProperty(openPercentage);
            openPercentages.add(openPercentage);
            return this;
        }
        
        /**
         * Add an array of positions.
         * 
         * @param positions The positions
         * @return The builder
         */
        public Builder setPositions(Property<WindowPosition>[] positions) {
            this.positions.clear();
            for (int i = 0; i < positions.length; i++) {
                addPosition(positions[i]);
            }
        
            return this;
        }
        /**
         * Add a single position.
         * 
         * @param position The position
         * @return The builder
         */
        public Builder addPosition(Property<WindowPosition> position) {
            position.setIdentifier(0x03);
            addProperty(position);
            positions.add(position);
            return this;
        }
    }
}