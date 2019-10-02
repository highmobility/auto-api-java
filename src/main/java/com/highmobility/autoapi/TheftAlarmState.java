// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.ByteEnum;

public class TheftAlarmState extends SetCommand {
    Property<Status> status = new Property(Status.class, 0x01);

    /**
     * @return The status
     */
    public Property<Status> getStatus() {
        return status;
    }

    TheftAlarmState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private TheftAlarmState(Builder builder) {
        super(builder);

        status = builder.status;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<Status> status;

        public Builder() {
            super(Identifier.THEFT_ALARM);
        }

        public TheftAlarmState build() {
            return new TheftAlarmState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<Status> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(this.status);
            return this;
        }
    }

    public enum Status implements ByteEnum {
        UNARMED((byte) 0x00),
        ARMED((byte) 0x01),
        TRIGGERED((byte) 0x02);
    
        public static Status fromByte(byte byteValue) throws CommandParseException {
            Status[] values = Status.values();
    
            for (int i = 0; i < values.length; i++) {
                Status state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Status(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}