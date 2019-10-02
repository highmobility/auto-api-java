// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.RemoteControlState.ControlMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * Stop control
 */
public class StopControl extends SetCommand {
    Property<ControlMode> controlMode = new Property(ControlMode.class, 0x01);

    /**
     * Stop control
     */
    public StopControl() {
        super(Identifier.REMOTE_CONTROL);
    
        addProperty(controlMode.addValueComponent(new Bytes("05")), true);
    }

    StopControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: controlMode.update(p);
                }
                return null;
            });
        }
        if ((controlMode.getValue() == null || controlMode.getValueComponent().getValueBytes().equals(new Bytes("05")) == false)) 
            throw new NoPropertiesException();
    }
}