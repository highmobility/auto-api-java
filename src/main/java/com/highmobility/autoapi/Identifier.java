package com.highmobility.autoapi;

import com.highmobility.utils.Bytes;

// used for generating type bytes, internally only.
// will not use for public because unknown commands need support as well
public enum Identifier {
    FAILURE(new byte[] { 0x00, (byte)0x02 }),
    CAPABILITIES(new byte[] { 0x00, (byte)0x10 }),
    VEHICLE_STATUS(new byte[] { 0x00, (byte)0x11 }),
    DOOR_LOCKS(new byte[] { 0x00, (byte)0x20 }),
    TRUNK_ACCESS(new byte[] { 0x00, (byte)0x21 }),
    WAKE_UP(new byte[] { 0x00, (byte)0x22 }),
    CHARGING(new byte[] { 0x00, (byte)0x23 }),
    CLIMATE(new byte[] { 0x00, (byte)0x24 }),
    ROOFTOP(new byte[] { 0x00, (byte)0x25 }),
    HONK_FLASH(new byte[] { 0x00, (byte)0x26 }),
    REMOTE_CONTROL(new byte[] { 0x00, (byte)0x27 }),
    VALET_MODE(new byte[] { 0x00, (byte)0x28 }),
    HEART_RATE(new byte[] { 0x00, (byte)0x29 }),
    VEHICLE_LOCATION(new byte[] { 0x00, (byte)0x30 }),
    VEHICLE_TIME(new byte[] { 0x00, (byte)0x50 }),
    NAVI_DESTINATION(new byte[] { 0x00, (byte)0x31 }),
    DELIVERED_PARCELS(new byte[] { 0x00, (byte)0x32 }),
    DIAGNOSTICS(new byte[] { 0x00, (byte)0x33 }),
    MAINTENANCE(new byte[] { 0x00, (byte)0x34 }),
    ENGINE(new byte[] { 0x00, (byte)0x35 }),
    LIGHTS(new byte[] { 0x00, (byte)0x36 }),
    MESSAGING(new byte[] { 0x00, (byte)0x37 }),
    NOTIFICATIONS(new byte[] { 0x00, (byte)0x38 }),
    WINDOWS(new byte[] { 0x00, (byte)0x45 }),
    WINDSCREEN(new byte[] { 0x00, (byte)0x42 }),
    VIDEO_HANDOVER(new byte[] { 0x00, (byte)0x43 }),
    BROWSER(new byte[] { 0x00, (byte)0x49 }),
    GRAPHICS(new byte[] { 0x00, (byte)0x51 }),
    TEXT_INPUT(new byte[] { 0x00, (byte)0x44 }),
    FUELING(new byte[] { 0x00, (byte)0x40 }),
    DRIVER_FATIGUE(new byte[] { 0x00, (byte)0x41 }),
    THEFT_ALARM(new byte[] { 0x00, (byte)0x46 }),
    PARKING_TICKET(new byte[] { 0x00, (byte)0x47 }),
    KEYFOB_POSITION(new byte[] { 0x00, (byte)0x48 }),
    FIRMWARE_VERSION(new byte[] { 0x00, (byte)0x03 }),
    SEATS(new byte[] { 0x00, (byte)0x56 }),
    RACE(new byte[] { 0x00, (byte)0x57 }),
    OFF_ROAD(new byte[] { 0x00, (byte)0x52 }),
    PARKING_BRAKE(new byte[] { 0x00, (byte)0x58 }),
    LIGHT_CONDITIONS(new byte[] { 0x00, (byte)0x54 }),
    WEATHER_CONDITIONS(new byte[] { 0x00, (byte)0x55 }),
    CHASSIS_SETTINGS(new byte[] { 0x00, (byte)0x53 });

    static Identifier fromBytes(byte[] bytes) throws CommandParseException {
        return fromBytes(bytes[0], bytes[1]);
    }

    static Identifier fromBytes(byte firstByte, byte secondByte) {
        Identifier[] allValues = Identifier.values();

        for (int i = 0; i < allValues.length; i++) {
            Identifier identifier = allValues[i];
            if (is(identifier, firstByte, secondByte)) {
                return identifier;
            }
        }

        return null;
    }

    Identifier(byte[] identifier) {
        this.identifier = identifier;
    }
    private byte[] identifier;
    public byte[] getBytes() {
        return identifier;
    }

    byte[] getBytesWithType(Type type) {
        return Bytes.concatBytes(identifier, type.getType());
    }

    byte[] getBytesWithType(byte type) {
        return Bytes.concatBytes(identifier, type);
    }

    byte[] getBytesWithType(Type type, byte extraValue) {
        return new byte[] {
            identifier[0],
            identifier[1],
            type.getType(),
            extraValue
        };
    }

    static boolean is(Identifier feature, byte firstByte, byte secondByte) {
        return feature.getBytes()[0] == firstByte && feature.getBytes()[1] == secondByte;
    }
}