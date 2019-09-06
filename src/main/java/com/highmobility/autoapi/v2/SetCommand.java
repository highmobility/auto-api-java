package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;

import java.util.List;

class SetCommand extends Command {
    SetCommand(Identifier identifier) {
        super(identifier, 3);

        set(0, identifier.getBytes());
        set(2, (byte) 0x01);

        type = Type.SET;
    }

    /*SetCommand(Identifier identifier, byte[] propertyIdentifiers) {
        super(identifier, 3 + (propertyIdentifiers != null ? propertyIdentifiers.length : 0));
        setBaseBytes();
        set(3, propertyIdentifiers);
    }*/

    protected void addProperty(Property property) {
        concat(property);
        // TODO: 04/09/2019 should add to base properties as well
    }

    protected void addProperties(List<Property> properties) {
        for (int i = 0; i < properties.size(); i++) {
            concat(properties.get(i));
        }
    }

    private static int getPropertiesLength(Property[] properties) {
        int length = 0;

        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            length += property.size();
        }

        return length;
    }

    SetCommand(byte[] bytes) {
        super(bytes);
    }

    public SetCommand(Command.Builder builder) {
        super(builder);
    }

    @Override protected boolean propertiesExpected() {
        return true;
    }
}
