// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.ActiveState;

public class PowerTakeoffState extends Command {
    Property<ActiveState> status = new Property(ActiveState.class, 0x01);
    Property<Engaged> engaged = new Property(Engaged.class, 0x02);

    /**
     * @return The status
     */
    public Property<ActiveState> getStatus() {
        return status;
    }

    /**
     * @return The engaged
     */
    public Property<Engaged> getEngaged() {
        return engaged;
    }

    PowerTakeoffState(byte[] bytes) {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return status.update(p);
                    case 0x02: return engaged.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private PowerTakeoffState(Builder builder) {
        super(builder);

        status = builder.status;
        engaged = builder.engaged;
    }

    public static final class Builder extends Command.Builder {
        private Property<ActiveState> status;
        private Property<Engaged> engaged;

        public Builder() {
            super(Identifier.POWER_TAKEOFF);
        }

        public PowerTakeoffState build() {
            return new PowerTakeoffState(this);
        }

        /**
         * @param status The status
         * @return The builder
         */
        public Builder setStatus(Property<ActiveState> status) {
            this.status = status.setIdentifier(0x01);
            addProperty(status);
            return this;
        }
        
        /**
         * @param engaged The engaged
         * @return The builder
         */
        public Builder setEngaged(Property<Engaged> engaged) {
            this.engaged = engaged.setIdentifier(0x02);
            addProperty(engaged);
            return this;
        }
    }

    public enum Engaged {
        NOT_ENGAGED((byte)0x00),
        ENGAGED((byte)0x01);
    
        public static Engaged fromByte(byte byteValue) throws CommandParseException {
            Engaged[] values = Engaged.values();
    
            for (int i = 0; i < values.length; i++) {
                Engaged state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Engaged(byte value) {
            this.value = value;
        }
    
        public byte getByte() {
            return value;
        }
    }
}