package com.highmobility.autoapi;

import com.highmobility.autoapi.property.HMProperty;
import com.highmobility.autoapi.property.WindscreenDamage;
import com.highmobility.autoapi.property.WindscreenDamageZone;
import com.highmobility.autoapi.property.WindscreenReplacementState;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the windscreen damage. This is for instance used to reset the glass damage or correct it. The
 * result is sent through the Windscreen State message. Damage confidence percentage is
 * automatically set to either 0% or 100%.
 */
public class SetWindscreenDamage extends CommandWithProperties {
    public static final Type TYPE = new Type(Identifier.WINDSCREEN, 0x02);

    public SetWindscreenDamage(WindscreenDamage damage, WindscreenDamageZone zone,
                               WindscreenReplacementState replacementState) throws CommandParseException {
        super(TYPE, getProperties(damage, zone, replacementState));
    }

    SetWindscreenDamage(byte[] bytes) throws CommandParseException {
        super(bytes);
        if (bytes.length != 4) throw new CommandParseException();
    }

    static HMProperty[] getProperties(WindscreenDamage damage, WindscreenDamageZone zone,
                                    WindscreenReplacementState replacementState) {

        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (damage != null) propertiesBuilder.add(damage);
        if (zone != null) propertiesBuilder.add(zone);
        if (replacementState != null) propertiesBuilder.add(replacementState);

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }
}
