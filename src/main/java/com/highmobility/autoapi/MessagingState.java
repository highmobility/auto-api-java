// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

public class MessagingState extends SetCommand {
    Property<String> text = new Property(String.class, 0x01);
    Property<String> handle = new Property(String.class, 0x02);

    /**
     * @return The text
     */
    public Property<String> getText() {
        return text;
    }

    /**
     * @return The optional handle of message
     */
    public Property<String> getHandle() {
        return handle;
    }

    MessagingState(byte[] bytes) throws CommandParseException {
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
    }

    @Override public boolean isState() {
        return true;
    }

    private MessagingState(Builder builder) {
        super(builder);

        text = builder.text;
        handle = builder.handle;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<String> text;
        private Property<String> handle;

        public Builder() {
            super(Identifier.MESSAGING);
        }

        public MessagingState build() {
            return new MessagingState(this);
        }

        /**
         * @param text The text
         * @return The builder
         */
        public Builder setText(Property<String> text) {
            this.text = text.setIdentifier(0x01);
            addProperty(this.text);
            return this;
        }
        
        /**
         * @param handle The optional handle of message
         * @return The builder
         */
        public Builder setHandle(Property<String> handle) {
            this.handle = handle.setIdentifier(0x02);
            addProperty(this.handle);
            return this;
        }
    }
}