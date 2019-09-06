// TODO: license

package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.WindowOpenPercentage;
import com.highmobility.autoapi.v2.value.WindowPosition;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class ControlWindows extends SetCommand {
    @Nullable Property<WindowOpenPercentage>[] openPercentages;
    @Nullable Property<WindowPosition>[] positions;

    /**
     * @return The open percentages
     */
    public @Nullable Property<WindowOpenPercentage>[] getOpenPercentages() {
        return openPercentages;
    }

    /**
     * @return The positions
     */
    public @Nullable Property<WindowPosition>[] getPositions() {
        return positions;
    }

    /**
     * Control windows
     *
     * @param openPercentages The open percentages
     * @param positions The positions
     */
    public ControlWindows(WindowOpenPercentage[] openPercentages, WindowPosition[] positions) {
        super(Identifier.WINDOWS);

        ArrayList<Property> openPercentagesBuilder = new ArrayList<>();
        for (WindowOpenPercentage openPercentage : openPercentages) {
            Property prop = new Property(0x02, openPercentage);
            openPercentagesBuilder.add(prop);
            addProperty(prop);
        }
        this.openPercentages = openPercentagesBuilder.toArray(new Property[0]);

        ArrayList<Property> positionsBuilder = new ArrayList<>();
        for (WindowPosition position : positions) {
            Property prop = new Property(0x03, position);
            positionsBuilder.add(prop);
            addProperty(prop);
        }
        this.positions = positionsBuilder.toArray(new Property[0]);
    }

    ControlWindows(byte[] bytes) {
        super(bytes);
    }
}