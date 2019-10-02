// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Load url
 */
public class LoadUrl extends SetCommand {
    Property<String> url = new Property(String.class, 0x01);

    /**
     * @return The url
     */
    public Property<String> getUrl() {
        return url;
    }
    
    /**
     * Load url
     *
     * @param url The The UTF8 bytes of the URL string
     */
    public LoadUrl(String url) {
        super(Identifier.BROWSER);
    
        addProperty(this.url.update(url), true);
    }

    LoadUrl(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return url.update(p);
                }
                return null;
            });
        }
        if (this.url.getValue() == null) 
            throw new NoPropertiesException();
    }
}