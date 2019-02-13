package com.highmobility.autoapi.property;

import com.highmobility.autoapi.CommandParseException;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyValueObject;
import com.highmobility.value.Bytes;

/**
 * A representation of color with rgba values.
 */
public class Color extends PropertyValueObject {
    float[] rgba;

    /**
     * @return The red component.
     */
    public float getRed() {
        return rgba[0];
    }

    /**
     * @return The red component.
     */
    public float getGreen() {
        return rgba[1];
    }

    /**
     * @return The blue component.
     */
    public float getBlue() {
        return rgba[2];
    }

    /**
     * @return The alpha component.
     */
    public float getAlpha() {
        return rgba[3];
    }

    public Color() {
    }

    public Color(float[] rgba) {
        super(3);
        if (rgba.length < 3) throw new IllegalArgumentException();
        this.rgba = rgba;

        set(0, (byte) ((int) rgba[0] * 255f));
        set(1, (byte) ((int) rgba[1] * 255f));
        set(2, (byte) ((int) rgba[2] * 255f));
    }

    private Color(float red, float green, float blue, float alpha) {
        this(new float[]{
                red, green, blue, 1f
        });
    }

    @Override public void update(Bytes value) throws CommandParseException {
        super.update(value);
        if (getLength() < 3) throw new CommandParseException();

        rgba = new float[4];
        rgba[0] = Property.getUnsignedInt(get(0)) / 255f;
        rgba[1] = Property.getUnsignedInt(get(1)) / 255f;
        rgba[2] = Property.getUnsignedInt(get(2)) / 255f;
        rgba[3] = 1f;
    }
}

