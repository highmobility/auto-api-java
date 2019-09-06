// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.ConnectionState;

public class MobileState extends Command {
    Property<ConnectionState> connection = new Property(ConnectionState.class, 0x01);

    /**
     * @return The connection
     */
    public Property<ConnectionState> getConnection() {
        return connection;
    }

    MobileState(byte[] bytes) {
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

    public static final class Builder extends Command.Builder {
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
            addProperty(connection);
            return this;
        }
    }
}