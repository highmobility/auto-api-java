// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class ActionItem extends PropertyValueObject {
    Integer id;
    String name;

    /**
     * @return The id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Name of the action.
     */
    public String getName() {
        return name;
    }

    public ActionItem(Integer id, String name) {
        super(0);
        update(id, name);
    }

    public ActionItem(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public ActionItem() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 3) throw new CommandParseException();

        int bytePosition = 0;
        id = Property.getUnsignedInt(bytes, bytePosition, 1);
        bytePosition += 1;

        int nameSize = Property.getUnsignedInt(bytes, bytePosition, 2);
        bytePosition += 2;
        name = Property.getString(value, bytePosition, nameSize);
    }

    public void update(Integer id, String name) {
        this.id = id;
        this.name = name;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, Property.intToBytes(id, 1));
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(name.length(), 2));
        bytePosition += 2;
        set(bytePosition, Property.stringToBytes(name));
    }

    public void update(ActionItem value) {
        update(value.id, value.name);
    }

    @Override public int getLength() {
        return 1 + name.length() + 2;
    }
}