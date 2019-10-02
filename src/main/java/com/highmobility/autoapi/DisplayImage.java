// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Display image
 */
public class DisplayImage extends SetCommand {
    Property<String> imageURL = new Property(String.class, 0x01);

    /**
     * @return The image url
     */
    public Property<String> getImageURL() {
        return imageURL;
    }
    
    /**
     * Display image
     *
     * @param imageURL The The UTF8 bytes of the image URL string
     */
    public DisplayImage(String imageURL) {
        super(Identifier.GRAPHICS);
    
        addProperty(this.imageURL.update(imageURL), true);
    }

    DisplayImage(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return imageURL.update(p);
                }
                return null;
            });
        }
        if (this.imageURL.getValue() == null) 
            throw new NoPropertiesException();
    }
}