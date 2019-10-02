// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.PropertyInteger;

/**
 * Send heart rate
 */
public class SendHeartRate extends SetCommand {
    PropertyInteger heartRate = new PropertyInteger(0x01, false);

    /**
     * @return The heart rate
     */
    public PropertyInteger getHeartRate() {
        return heartRate;
    }
    
    /**
     * Send heart rate
     *
     * @param heartRate The heart rate
     */
    public SendHeartRate(Integer heartRate) {
        super(Identifier.HEART_RATE);
    
        addProperty(this.heartRate.update(false, 1, heartRate), true);
    }

    SendHeartRate(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return heartRate.update(p);
                }
                return null;
            });
        }
        if (this.heartRate.getValue() == null) 
            throw new NoPropertiesException();
    }
}