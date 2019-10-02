// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.RemoteControlState.ControlMode;
import com.highmobility.autoapi.property.Property;
import com.highmobility.value.Bytes;

/**
 * Start control
 */
public class StartControl extends SetCommand {
    Property<ControlMode> controlMode = new Property(ControlMode.class, 0x01);

    /**
     * Start control
     */
    public StartControl() {
        super(Identifier.REMOTE_CONTROL);
    
        addProperty(controlMode.addValueComponent(new Bytes("02")), true);
    }

    StartControl(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: controlMode.update(p);
                }
                return null;
            });
        }
        if ((controlMode.getValue() == null || controlMode.getValueComponent().getValueBytes().equals(new Bytes("02")) == false)) 
            throw new NoPropertiesException();
    }
}