// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ConnectionState;

public class MobileState extends SetCommand {
    Property<ConnectionState> connection = new Property(ConnectionState.class, 0x01);

    /**
     * @return The connection
     */
    public Property<ConnectionState> getConnection() {
        return connection;
    }

    MobileState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return connection.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private MobileState(Builder builder) {
        super(builder);

        connection = builder.connection;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<ConnectionState> connection;

        public Builder() {
            super(Identifier.MOBILE);
        }

        public MobileState build() {
            return new MobileState(this);
        }

        /**
         * @param connection The connection
         * @return The builder
         */
        public Builder setConnection(Property<ConnectionState> connection) {
            this.connection = connection.setIdentifier(0x01);
            addProperty(this.connection);
            return this;
        }
    }
}