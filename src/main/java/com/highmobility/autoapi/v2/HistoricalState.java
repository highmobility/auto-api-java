// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import java.util.ArrayList;
import java.util.List;

public class HistoricalState extends Command {
    Property<Command> states[];

    /**
     * @return The states
     */
    public Property<Command>[] getStates() {
        return states;
    }

    HistoricalState(byte[] bytes) {
        super(bytes);

        ArrayList<Property> statesBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01:
                        Property<Command> state = new Property(Command.class, p);
                        statesBuilder.add(state);
                        return state;
                }

                return null;
            });
        }

        states = statesBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private HistoricalState(Builder builder) {
        super(builder);

        states = builder.states.toArray(new Property[0]);
    }

    public static final class Builder extends Command.Builder {
        private List<Property> states = new ArrayList<>();

        public Builder() {
            super(Identifier.HISTORICAL);
        }

        public HistoricalState build() {
            return new HistoricalState(this);
        }

        /**
         * Add an array of states.
         * 
         * @param states The states
         * @return The builder
         */
        public Builder setStates(Property<Command>[] states) {
            this.states.clear();
            for (int i = 0; i < states.length; i++) {
                addState(states[i]);
            }
        
            return this;
        }
        /**
         * Add a single state.
         * 
         * @param state The state
         * @return The builder
         */
        public Builder addState(Property<Command> state) {
            state.setIdentifier(0x01);
            addProperty(state);
            states.add(state);
            return this;
        }
    }
}