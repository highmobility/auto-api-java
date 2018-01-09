package com.highmobility.autoapi;

/**
 * Heart rate can be sent to the car from a health accessory of a smartwatch. This is only possible
 * to send through a direct Bluetooth link for privacy reasons.
 */
public class SendHeartRate extends Command {
    public static final Type TYPE = new Type(Identifier.HEART_RATE, 0x02);

    /**
     * Send the driver heart rate to the car.
     *
     * @param heartRate The heart rate.
     * @return The command bytes.
     */
    public SendHeartRate(int heartRate) {
        super(TYPE.addByte((byte) heartRate), true);
    }

    public SendHeartRate(byte[] bytes) throws CommandParseException {
        super(bytes);
    }
}
