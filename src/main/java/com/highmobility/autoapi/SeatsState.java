// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.PersonDetected;
import com.highmobility.autoapi.value.SeatbeltState;
import javax.annotation.Nullable;
import com.highmobility.autoapi.value.SeatLocation;
import java.util.ArrayList;
import java.util.List;

public class SeatsState extends SetCommand {
    Property<PersonDetected>[] personsDetected;
    Property<SeatbeltState>[] seatbeltsState;

    /**
     * @return The persons detected
     */
    public Property<PersonDetected>[] getPersonsDetected() {
        return personsDetected;
    }

    /**
     * @return The seatbelts state
     */
    public Property<SeatbeltState>[] getSeatbeltsState() {
        return seatbeltsState;
    }

    /**
     * @param location The seat location.
     * @return A person detection on a seat.
     */
    @Nullable public Property<PersonDetected> getPersonDetection(SeatLocation location) {
        for (int i = 0; i < personsDetected.length; i++) {
            Property<PersonDetected> property = personsDetected[i];
            if (property.getValue() != null && property.getValue().getSeatLocation() == location)
                return property;
        }

        return null;
    }

    /**
     * @param location The seat location.
     * @return The seat belt state.
     */
    @Nullable public Property<SeatbeltState> getSeatBeltFastened(SeatLocation location) {
        for (int i = 0; i < seatbeltsState.length; i++) {
            Property<SeatbeltState> property = seatbeltsState[i];
            if (property.getValue() != null && property.getValue().getSeatLocation() == location)
                return property;
        }

        return null;
    }

    SeatsState(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Property> personsDetectedBuilder = new ArrayList<>();
        ArrayList<Property> seatbeltsStateBuilder = new ArrayList<>();

        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x02:
                        Property<PersonDetected> personsDetected = new Property(PersonDetected.class, p);
                        personsDetectedBuilder.add(personsDetected);
                        return personsDetected;
                    case 0x03:
                        Property<SeatbeltState> seatbeltsState = new Property(SeatbeltState.class, p);
                        seatbeltsStateBuilder.add(seatbeltsState);
                        return seatbeltsState;
                }

                return null;
            });
        }

        personsDetected = personsDetectedBuilder.toArray(new Property[0]);
        seatbeltsState = seatbeltsStateBuilder.toArray(new Property[0]);
    }

    @Override public boolean isState() {
        return true;
    }

    private SeatsState(Builder builder) {
        super(builder);

        personsDetected = builder.personsDetected.toArray(new Property[0]);
        seatbeltsState = builder.seatbeltsState.toArray(new Property[0]);
    }

    public static final class Builder extends SetCommand.Builder {
        private List<Property> personsDetected = new ArrayList<>();
        private List<Property> seatbeltsState = new ArrayList<>();

        public Builder() {
            super(Identifier.SEATS);
        }

        public SeatsState build() {
            return new SeatsState(this);
        }

        /**
         * Add an array of persons detected.
         * 
         * @param personsDetected The persons detected
         * @return The builder
         */
        public Builder setPersonsDetected(Property<PersonDetected>[] personsDetected) {
            this.personsDetected.clear();
            for (int i = 0; i < personsDetected.length; i++) {
                addPersonsDetecte(personsDetected[i]);
            }
        
            return this;
        }
        
        /**
         * Add a single persons detecte.
         * 
         * @param personsDetecte The persons detecte
         * @return The builder
         */
        public Builder addPersonsDetecte(Property<PersonDetected> personsDetecte) {
            personsDetecte.setIdentifier(0x02);
            addProperty(personsDetecte);
            personsDetected.add(personsDetecte);
            return this;
        }
        
        /**
         * Add an array of seatbelts state.
         * 
         * @param seatbeltsState The seatbelts state
         * @return The builder
         */
        public Builder setSeatbeltsState(Property<SeatbeltState>[] seatbeltsState) {
            this.seatbeltsState.clear();
            for (int i = 0; i < seatbeltsState.length; i++) {
                addSeatbeltsStat(seatbeltsState[i]);
            }
        
            return this;
        }
        /**
         * Add a single seatbelts stat.
         * 
         * @param seatbeltsStat The seatbelts stat
         * @return The builder
         */
        public Builder addSeatbeltsStat(Property<SeatbeltState> seatbeltsStat) {
            seatbeltsStat.setIdentifier(0x03);
            addProperty(seatbeltsStat);
            seatbeltsState.add(seatbeltsStat);
            return this;
        }
    }
}