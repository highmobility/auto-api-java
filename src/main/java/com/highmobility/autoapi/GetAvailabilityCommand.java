package com.highmobility.autoapi;

import com.highmobility.value.Bytes;

class GetAvailabilityCommand extends Command {
    /**
     * The property identifiers the availability is requested for
     */
    Bytes propertyIdentifiers;

    public GetAvailabilityCommand(int identifier, Bytes propertyIdentifiers) {
        super(identifier, Type.GET_AVAILABILITY, propertyIdentifiers.size());
        set(COMMAND_TYPE_POSITION + 1, propertyIdentifiers);
        this.propertyIdentifiers = propertyIdentifiers;
    }

    public GetAvailabilityCommand(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length < COMMAND_TYPE_POSITION + 1 ||
                bytes[COMMAND_TYPE_POSITION] != Type.GET_AVAILABILITY)
            throw new CommandParseException();

        propertyIdentifiers = getRange(COMMAND_TYPE_POSITION + 1, bytes.length);
    }

    public GetAvailabilityCommand(int identifier) {
        super(identifier, Type.GET_AVAILABILITY, 0);
        propertyIdentifiers = new Bytes();
    }
}
