// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.LightsState.FrontExteriorLight;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.ActiveState;
import com.highmobility.autoapi.value.Light;
import com.highmobility.autoapi.value.ReadingLamp;
import com.highmobility.autoapi.value.RgbColour;
import java.util.ArrayList;
import javax.annotation.Nullable;

/**
 * Control lights
 */
public class ControlLights extends SetCommand {
    @Nullable Property<FrontExteriorLight> frontExteriorLight = new Property(FrontExteriorLight.class, 0x01);
    @Nullable Property<ActiveState> rearExteriorLight = new Property(ActiveState.class, 0x02);
    @Nullable Property<RgbColour> ambientLightColour = new Property(RgbColour.class, 0x04);
    @Nullable Property<Light>[] fogLights;
    @Nullable Property<ReadingLamp>[] readingLamps;
    @Nullable Property<Light>[] interiorLights;

    /**
     * @return The front exterior light
     */
    public @Nullable Property<FrontExteriorLight> getFrontExteriorLight() {
        return frontExteriorLight;
    }
    
    /**
     * @return The rear exterior light
     */
    public @Nullable Property<ActiveState> getRearExteriorLight() {
        return rearExteriorLight;
    }
    
    /**
     * @return The ambient light colour
     */
    public @Nullable Property<RgbColour> getAmbientLightColour() {
        return ambientLightColour;
    }
    
    /**
     * @return The fog lights
     */
    public @Nullable Property<Light>[] getFogLights() {
        return fogLights;
    }
    
    /**
     * @return The reading lamps
     */
    public @Nullable Property<ReadingLamp>[] getReadingLamps() {
        return readingLamps;
    }
    
    /**
     * @return The interior lights
     */
    public @Nullable Property<Light>[] getInteriorLights() {
        return interiorLights;
    }
    
    /**
     * Control lights
     *
     * @param frontExteriorLight The front exterior light
     * @param rearExteriorLight The rear exterior light
     * @param ambientLightColour The ambient light colour
     * @param fogLights The fog lights
     * @param readingLamps The reading lamps
     * @param interiorLights The interior lights
     */
    public ControlLights(@Nullable FrontExteriorLight frontExteriorLight, @Nullable ActiveState rearExteriorLight, @Nullable RgbColour ambientLightColour, @Nullable Light[] fogLights, @Nullable ReadingLamp[] readingLamps, @Nullable Light[] interiorLights) {
        super(Identifier.LIGHTS);
    
        addProperty(this.frontExteriorLight.update(frontExteriorLight));
        addProperty(this.rearExteriorLight.update(rearExteriorLight));
        addProperty(this.ambientLightColour.update(ambientLightColour));
        ArrayList<Property> fogLightsBuilder = new ArrayList<>();
        if (fogLights != null) {
            for (Light fogLight : fogLights) {
                Property prop = new Property(0x07, fogLight);
                fogLightsBuilder.add(prop);
                addProperty(prop);
            }
        }
        this.fogLights = fogLightsBuilder.toArray(new Property[0]);
        
        ArrayList<Property> readingLampsBuilder = new ArrayList<>();
        if (readingLamps != null) {
            for (ReadingLamp readingLamp : readingLamps) {
                Property prop = new Property(0x08, readingLamp);
                readingLampsBuilder.add(prop);
                addProperty(prop);
            }
        }
        this.readingLamps = readingLampsBuilder.toArray(new Property[0]);
        
        ArrayList<Property> interiorLightsBuilder = new ArrayList<>();
        if (interiorLights != null) {
            for (Light interiorLight : interiorLights) {
                Property prop = new Property(0x09, interiorLight);
                interiorLightsBuilder.add(prop);
                addProperty(prop, true);
            }
        }
        this.interiorLights = interiorLightsBuilder.toArray(new Property[0]);
        if (this.frontExteriorLight.getValue() == null && this.rearExteriorLight.getValue() == null && this.ambientLightColour.getValue() == null && this.fogLights.length == 0 && this.readingLamps.length == 0 && this.interiorLights.length == 0) throw new IllegalArgumentException();
    }

    ControlLights(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
    
        ArrayList<Property<Light>> fogLightsBuilder = new ArrayList<>();
        ArrayList<Property<ReadingLamp>> readingLampsBuilder = new ArrayList<>();
        ArrayList<Property<Light>> interiorLightsBuilder = new ArrayList<>();
    
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return frontExteriorLight.update(p);
                    case 0x02: return rearExteriorLight.update(p);
                    case 0x04: return ambientLightColour.update(p);
                    case 0x07: {
                        Property fogLight = new Property(Light.class, p);
                        fogLightsBuilder.add(fogLight);
                        return fogLight;
                    }
                    case 0x08: {
                        Property readingLamp = new Property(ReadingLamp.class, p);
                        readingLampsBuilder.add(readingLamp);
                        return readingLamp;
                    }
                    case 0x09: {
                        Property interiorLight = new Property(Light.class, p);
                        interiorLightsBuilder.add(interiorLight);
                        return interiorLight;
                    }
                }
                return null;
            });
        }
    
        fogLights = fogLightsBuilder.toArray(new Property[0]);
        readingLamps = readingLampsBuilder.toArray(new Property[0]);
        interiorLights = interiorLightsBuilder.toArray(new Property[0]);
        if (this.frontExteriorLight.getValue() == null && this.rearExteriorLight.getValue() == null && this.ambientLightColour.getValue() == null && this.fogLights.length == 0 && this.readingLamps.length == 0 && this.interiorLights.length == 0) throw new NoPropertiesException();
    }
}