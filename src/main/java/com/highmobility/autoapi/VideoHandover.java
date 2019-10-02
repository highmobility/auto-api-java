// TODO: license

package com.highmobility.autoapi;

import com.highmobility.autoapi.property.ByteEnum;
import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.property.PropertyInteger;
import javax.annotation.Nullable;

/**
 * Video handover
 */
public class VideoHandover extends SetCommand {
    Property<String> url = new Property(String.class, 0x01);
    @Nullable PropertyInteger startingSecond = new PropertyInteger(0x02, false);
    @Nullable Property<Screen> screen = new Property(Screen.class, 0x03);

    /**
     * @return The url
     */
    public Property<String> getUrl() {
        return url;
    }
    
    /**
     * @return The starting second
     */
    public @Nullable PropertyInteger getStartingSecond() {
        return startingSecond;
    }
    
    /**
     * @return The screen
     */
    public @Nullable Property<Screen> getScreen() {
        return screen;
    }
    
    /**
     * Video handover
     *
     * @param url The URL string
     * @param startingSecond The starting second
     * @param screen The screen
     */
    public VideoHandover(String url, @Nullable Integer startingSecond, @Nullable Screen screen) {
        super(Identifier.VIDEO_HANDOVER);
    
        addProperty(this.url.update(url));
        addProperty(this.startingSecond.update(false, 2, startingSecond));
        addProperty(this.screen.update(screen), true);
    }

    VideoHandover(byte[] bytes) throws CommandParseException, NoPropertiesException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return url.update(p);
                    case 0x02: return startingSecond.update(p);
                    case 0x03: return screen.update(p);
                }
                return null;
            });
        }
        if (this.url.getValue() == null) 
            throw new NoPropertiesException();
    }

    public enum Screen implements ByteEnum {
        FRONT((byte) 0x00),
        REAR((byte) 0x01);
    
        public static Screen fromByte(byte byteValue) throws CommandParseException {
            Screen[] values = Screen.values();
    
            for (int i = 0; i < values.length; i++) {
                Screen state = values[i];
                if (state.getByte() == byteValue) {
                    return state;
                }
            }
    
            throw new CommandParseException();
        }
    
        private byte value;
    
        Screen(byte value) {
            this.value = value;
        }
    
        @Override public byte getByte() {
            return value;
        }
    }
}