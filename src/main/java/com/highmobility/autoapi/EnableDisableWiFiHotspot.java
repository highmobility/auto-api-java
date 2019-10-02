// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.EnabledState;

/**
 * Enable disable wi fi hotspot
 */
public class EnableDisableWiFiHotspot extends SetCommand {
    Property<EnabledState> wifiHotspotEnabled = new Property(EnabledState.class, 0x08);

    /**
     * @return The wi fi hotspot enabled
     */
    public Property<EnabledState> getWifiHotspotEnabled() {
        return wifiHotspotEnabled;
    }
    
    /**
     * Enable disable wi fi hotspot
     *
     * @param wifiHotspotEnabled The wi fi hotspot enabled
     */
    public EnableDisableWiFiHotspot(EnabledState wifiHotspotEnabled) {
        super(Identifier.HOME_CHARGER);
    
        addProperty(this.wifiHotspotEnabled.update(wifiHotspotEnabled), true);
    }

    EnableDisableWiFiHotspot(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x08: return wifiHotspotEnabled.update(p);
                }
                return null;
            });
        }
        if (this.wifiHotspotEnabled.getValue() == null) 
            throw new NoPropertiesException();
    }
}