// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Message received
 */
public class MessageReceived extends SetCommand {
    Property<String> text = new Property(String.class, 0x01);
    @Nullable Property<String> handle = new Property(String.class, 0x02);

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }
    
    /**
     * @return The handle
     */
    public @Nullable Property<String> getHandle() {
        return handle;
    }
    
    /**
     * Message received
     *
     * @param text The The text
     * @param handle The The optional handle of message
     */
    public MessageReceived(String text, @Nullable String handle) {
        super(Identifier.MESSAGING);
    
        addProperty(this.text.update(text));
        addProperty(this.handle.update(handle), true);
    }

    MessageReceived(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return text.update(p);
                    case 0x02: return handle.update(p);
                }
                return null;
            });
        }
        if (this.text.getValue() == null) 
            throw new NoPropertiesException();
    }
}