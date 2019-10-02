// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

/**
 * Text input
 */
public class TextInput extends SetCommand {
    Property<String> text = new Property(String.class, 0x01);

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }
    
    /**
     * Text input
     *
     * @param text The The UTF8 bytes of the string
     */
    public TextInput(String text) {
        super(Identifier.TEXT_INPUT);
    
        addProperty(this.text.update(text), true);
    }

    TextInput(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return text.update(p);
                }
                return null;
            });
        }
        if (this.text.getValue() == null) 
            throw new NoPropertiesException();
    }
}