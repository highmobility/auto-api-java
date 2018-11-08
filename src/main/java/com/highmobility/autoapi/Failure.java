/*
 * HMKit Auto API - Auto API Parser for Java
 * Copyright (C) 2018 High-Mobility <licensing@high-mobility.com>
 *
 * This file is part of HMKit Auto API.
 *
 * HMKit Auto API is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HMKit Auto API is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HMKit Auto API.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.FailureReason;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.StringProperty;

import javax.annotation.Nullable;

/**
 * For different reasons a command sent to the car might not be successful. When this happens, a
 * failure command is sent to the smart device with details about what went wrong.
 */
public class Failure extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.FAILURE, 0x01);

    private static final byte IDENTIFIER_IDENTIFIER = 0x01;
    private static final byte IDENTIFIER_TYPE = 0x02;
    private static final byte IDENTIFIER_FAILURE_REASON = 0x03;
    private static final byte IDENTIFIER_FAILURE_DESCRIPTION = 0x04;

    byte[] identifier;
    Byte failedTypeByte;

    private Type failedType;
    private FailureReason failureReason;

    private String failureDescription;

    /**
     * @return The failed identifier bytes.
     */
    public byte[] getFailedIdentifier() {
        return identifier;
    }

    /**
     * @return The failed type byte.
     */
    public Byte getFailedTypeByte() {
        return failedTypeByte;
    }

    /**
     * @return The type of the command that failed.
     */
    public Type getFailedType() {
        return failedType;
    }

    /**
     * @return The failure reason.
     */
    public FailureReason getFailureReason() {
        return failureReason;
    }

    /**
     * @return The failure description.
     */
    @Nullable public String getFailureDescription() {
        return failureDescription;
    }

    public Failure(byte[] bytes) {
        super(bytes);

        while (propertiesIterator.hasNext()) {
            propertiesIterator.parseNext(property -> {
                switch (property.getPropertyIdentifier()) {
                    case IDENTIFIER_IDENTIFIER:
                        identifier = property.getValueBytes();
                        break;
                    case IDENTIFIER_TYPE:
                        failedTypeByte = property.getValueByte();
                        break;
                    case IDENTIFIER_FAILURE_REASON:
                        failureReason = FailureReason.fromByte(property.getValueByte());
                        break;
                    case IDENTIFIER_FAILURE_DESCRIPTION:
                        failureDescription = Property.getString(property.getValueBytes());
                        break;

                }
            });
        }

        if (identifier != null && failedTypeByte != null)
            failedType = new Type(identifier, failedTypeByte);
    }

    private Failure(Builder builder) {
        super(builder);
        failureReason = builder.failureReason;
        failedType = builder.failedType;
        failureDescription = builder.failureDescription;
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private FailureReason failureReason;
        private Type failedType;
        private String failureDescription;

        public Builder() {
            super(TYPE);
        }

        /**
         * Set the type of the failed command.
         *
         * @param type The failed type.
         * @return The builder.
         */
        public Builder setFailedType(Type type) {
            this.failedType = type;
            addProperty(new Property(IDENTIFIER_IDENTIFIER, type.getIdentifier()));
            addProperty(new Property(IDENTIFIER_TYPE, type.getType()));
            return this;
        }

        /**
         * Set the failure reason.
         *
         * @param failureReason The failure reason.
         * @return The builder.
         */
        public Builder setFailureReason(FailureReason failureReason) {
            this.failureReason = failureReason;
            addProperty(new Property(IDENTIFIER_FAILURE_REASON, failureReason.getByte()));
            return this;
        }

        /**
         * @param description The failure description.
         * @return The builder.
         */
        public Builder setFailureDescription(String description) {
            this.failureDescription = description;
            addProperty(new StringProperty(IDENTIFIER_FAILURE_DESCRIPTION, description));
            return this;
        }

        public Failure build() {
            return new Failure(this);
        }
    }
}