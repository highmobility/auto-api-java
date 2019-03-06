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

    private Property<Identifier> failedIdentifier = new Property(Identifier.class,
            IDENTIFIER_IDENTIFIER);

    private Property<Byte> failedTypeByte = new Property(Byte.class, IDENTIFIER_TYPE);
    private Property<FailureReason> failureReason = new Property(FailureReason.class,
            IDENTIFIER_FAILURE_REASON);
    private Property<String> failureDescription = new Property(String.class,
            IDENTIFIER_FAILURE_DESCRIPTION);

    private Type failedType;

    /**
     * @return The failed identifier bytes.
     */
    public Property<Identifier> getFailedIdentifier() {
        return failedIdentifier;
    }

    /**
     * @return The failed type byte.
     */
    public Property<Byte> getFailedTypeByte() {
        return failedTypeByte;
    }

    /**
     * @return The type of the command that failed.
     */
    @Nullable public Type getFailedType() {
        return failedType;
    }

    /**
     * @return The failure reason.
     */
    public Property<FailureReason> getFailureReason() {
        return failureReason;
    }

    /**
     * @return The failure description.
     */
    @Nullable public Property<String> getFailureDescription() {
        return failureDescription;
    }

    Failure(byte[] bytes) {
        super(bytes);

        while (propertiesIterator2.hasNext()) {
            propertiesIterator2.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case IDENTIFIER_IDENTIFIER:
                        return failedIdentifier.update(p);
                    case IDENTIFIER_TYPE:
                        return failedTypeByte.update(p);
                    case IDENTIFIER_FAILURE_REASON:
                        return failureReason.update(p);
                    case IDENTIFIER_FAILURE_DESCRIPTION:
                        return failureDescription.update(p);
                }

                return null;
            });
        }

        createType();
    }

    private void createType() {
        if (failedIdentifier != null && failedTypeByte != null)
            failedType = new Type(failedIdentifier.getValue(), failedTypeByte.getValue());
    }

    private Failure(Builder builder) {
        super(builder);
        failureReason = builder.failureReason;
        failedTypeByte = builder.failedTypeByte;
        failedIdentifier = builder.failedIdentifier;
        failureDescription = builder.failureDescription;

        createType();
    }

    public static final class Builder extends CommandWithProperties.Builder {
        private Property<FailureReason> failureReason;

        private Property<Byte> failedTypeByte;
        private Property<Identifier> failedIdentifier;

        private Property<String> failureDescription;

        public Builder() {
            super(TYPE);
        }

        /**
         * Set the type of the failed command.
         *
         * @param type The failed type.
         * @return The builder.
         */
        public Builder setFailedTypeByte(Property<Byte> type) {
            this.failedTypeByte = type;
            addProperty(this.failedTypeByte.setIdentifier(IDENTIFIER_TYPE));
            return this;
        }

        /**
         * Set the identifier of the failed command.
         *
         * @param failedIdentifier the failed identifier.
         * @return The builder
         */
        public Builder setFailedIdentifier(Property<Identifier> failedIdentifier) {
            this.failedIdentifier = failedIdentifier;
            addProperty(this.failedIdentifier.setIdentifier(IDENTIFIER_IDENTIFIER));
            return this;
        }

        /**
         * Set the failure reason.
         *
         * @param failureReason The failure reason.
         * @return The builder.
         */
        public Builder setFailureReason(Property<FailureReason> failureReason) {
            this.failureReason = failureReason;
            addProperty(failureReason.setIdentifier(IDENTIFIER_FAILURE_REASON));
            return this;
        }

        /**
         * @param description The failure description.
         * @return The builder.
         */
        public Builder setFailureDescription(Property<String> description) {
            addProperty(description.setIdentifier(IDENTIFIER_FAILURE_DESCRIPTION));
            this.failureDescription = description;
            return this;
        }

        public Failure build() {
            return new Failure(this);
        }
    }
}