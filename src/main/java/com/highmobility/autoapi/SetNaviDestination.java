// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.Coordinates;
import javax.annotation.Nullable;

/**
 * Set navi destination
 */
public class SetNaviDestination extends SetCommand {
    Property<Coordinates> coordinates = new Property(Coordinates.class, 0x01);
    @Nullable Property<String> destinationName = new Property(String.class, 0x02);

    /**
     * @return The coordinates
     */
    public Property<Coordinates> getCoordinates() {
        return coordinates;
    }
    
    /**
     * @return The destination name
     */
    public @Nullable Property<String> getDestinationName() {
        return destinationName;
    }
    
    /**
     * Set navi destination
     *
     * @param coordinates The coordinates
     * @param destinationName The Destination name
     */
    public SetNaviDestination(Coordinates coordinates, @Nullable String destinationName) {
        super(Identifier.NAVI_DESTINATION);
    
        addProperty(this.coordinates.update(coordinates));
        addProperty(this.destinationName.update(destinationName), true);
    }

    SetNaviDestination(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return coordinates.update(p);
                    case 0x02: return destinationName.update(p);
                }
                return null;
            });
        }
        if (this.coordinates.getValue() == null) 
            throw new NoPropertiesException();
    }
}