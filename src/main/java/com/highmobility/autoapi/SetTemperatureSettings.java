// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import javax.annotation.Nullable;

/**
 * Set temperature settings
 */
public class SetTemperatureSettings extends SetCommand {
    @Nullable Property<Float> driverTemperatureSetting = new Property(Float.class, 0x03);
    @Nullable Property<Float> passengerTemperatureSetting = new Property(Float.class, 0x04);
    @Nullable Property<Float> rearTemperatureSetting = new Property(Float.class, 0x0c);

    /**
     * @return The driver temperature setting
     */
    public @Nullable Property<Float> getDriverTemperatureSetting() {
        return driverTemperatureSetting;
    }
    
    /**
     * @return The passenger temperature setting
     */
    public @Nullable Property<Float> getPassengerTemperatureSetting() {
        return passengerTemperatureSetting;
    }
    
    /**
     * @return The rear temperature setting
     */
    public @Nullable Property<Float> getRearTemperatureSetting() {
        return rearTemperatureSetting;
    }
    
    /**
     * Set temperature settings
     *
     * @param driverTemperatureSetting The The driver temperature setting in celsius
     * @param passengerTemperatureSetting The The passenger temperature setting in celsius
     * @param rearTemperatureSetting The The rear temperature in celsius
     */
    public SetTemperatureSettings(@Nullable Float driverTemperatureSetting, @Nullable Float passengerTemperatureSetting, @Nullable Float rearTemperatureSetting) {
        super(Identifier.CLIMATE);
    
        addProperty(this.driverTemperatureSetting.update(driverTemperatureSetting));
        addProperty(this.passengerTemperatureSetting.update(passengerTemperatureSetting));
        addProperty(this.rearTemperatureSetting.update(rearTemperatureSetting), true);
        if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new IllegalArgumentException();
    }

    SetTemperatureSettings(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x03: return driverTemperatureSetting.update(p);
                    case 0x04: return passengerTemperatureSetting.update(p);
                    case 0x0c: return rearTemperatureSetting.update(p);
                }
                return null;
            });
        }
        if (this.driverTemperatureSetting.getValue() == null && this.passengerTemperatureSetting.getValue() == null && this.rearTemperatureSetting.getValue() == null) throw new NoPropertiesException();
    }
}