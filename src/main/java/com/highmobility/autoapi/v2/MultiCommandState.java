// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import java.util.ArrayList;
import java.util.List;

public class MultiCommandState extends Command {
    Property<Command> multiStates[];

    /**
     * @return The bytes of incoming capabilities (states)
     */
    public Property<Command>[] getMultiStates() {
        return multiStates;
    }

    MultiCommandState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> multiStatesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01:
                        Property<Command> multiState = new Property(Command.class, p);
                        multiStatesBuilder.add(multiState);
                        return multiState;
                }

                return null;
            });
        }

        multiStates = multiStatesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private MultiCommandState(Builder builder) {
        super(builder);

        multiStates = builder.multiStates.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        private List<Property> multiStates = new ArrayList<>();

        public Builder() {
            super(Identifier.MULTI_COMMAND);
        }

        public MultiCommandState build() {
            return new MultiCommandState(this);
        }

        /**
         * Add an array of multi states.
         * 
         * @param multiStates The multi states. The bytes of incoming capabilities (states)
         * @return The builder
         */
        public Builder setMultiStates(Property<Command>[] multiStates) {
            this.multiStates.clear();
            for (int i = 0; i < multiStates.length; i++) {
                addMultiState(multiStates[i]);
            }
        
            return this;
        }
        /**
         * Add a single multi state.
         * 
         * @param multiState The multi state. The bytes of incoming capabilities (states)
         * @return The builder
         */
        public Builder addMultiState(Property<Command> multiState) {
            multiState.setIdentifier(0x01);
            addProperty(multiState);
            multiStates.add(multiState);
            return this;
        }
    }
}