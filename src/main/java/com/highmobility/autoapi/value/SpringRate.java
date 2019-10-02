// TODO: license

package com.highmobility.autoapi.value;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

public class SpringRate extends PropertyValueObject {
    public static final int SIZE = 2;

    Axle axle;
    Integer springRate;

    /**
     * @return The axle.
     */
    public Axle getAxle() {
        return axle;
    }

    /**
     * @return The suspension spring rate in N/mm.
     */
    public Integer getSpringRate() {
        return springRate;
    }

    public SpringRate(Axle axle, Integer springRate) {
        super(2);
        update(axle, springRate);
    }

    public SpringRate(Property property) throws CommandParseException {
        super();
        update(property.getValueComponent().getValueBytes());
    }

    public SpringRate() {
        super();
    } // needed for generic ctor

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (bytes.length < 2) throw new CommandParseException();

        int bytePosition = 0;
        axle = Axle.fromByte(get(bytePosition));
        bytePosition += 1;

        springRate = Property.getUnsignedInt(bytes, bytePosition, 1);
    }

    public void update(Axle axle, Integer springRate) {
        this.axle = axle;
        this.springRate = springRate;

        bytes = new byte[getLength()];

        int bytePosition = 0;
        set(bytePosition, axle.getByte());
        bytePosition += 1;

        set(bytePosition, Property.intToBytes(springRate, 1));
    }

    public void update(SpringRate value) {
        update(value.axle, value.springRate);
    }

    @Override public int getLength() {
        return 1 + 1;
    }
}