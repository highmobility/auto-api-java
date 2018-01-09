package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This message is sent when a Get Vehicle Status is received by the car. The states are passed
 * along as an array of all states that the vehicle possesses. No states are included for
 * Capabilities that are unsupported.
 */
public class VehicleStatus extends Command {
    public static final Type TYPE = new Type(Identifier.VEHICLE_STATUS, 0x01);

    public enum PowerTrain {
        UNKNOWN((byte) 0x00),
        ALLELECTRIC((byte) 0x01),
        COMBUSTIONENGINE((byte) 0x02),
        PLUGINHYBRID((byte) 0x03),
        HYDROGEN((byte) 0x04),
        HYDROGENHYBRID((byte) 0x05);

        public static PowerTrain fromByte(byte value) throws CommandParseException {
            PowerTrain[] values = PowerTrain.values();

            for (int i = 0; i < values.length; i++) {
                PowerTrain capability = values[i];
                if (capability.getByte() == value) {
                    return capability;
                }
            }

            throw new CommandParseException();
        }

        private byte capabilityByte;

        PowerTrain(byte capabilityByte) {
            this.capabilityByte = capabilityByte;
        }

        public byte getByte() {
            return capabilityByte;
        }
    }

    /*public static byte[] getCommandBytes(String vin,
                                         PowerTrain powerTrain,
                                         String modelName,
                                         String name,
                                         String licensePlate,
                                         Command[] states) throws UnsupportedEncodingException {
        // TODO:
        byte[] bytes = Bytes.concatBytes(Identifier.VEHICLE_STATUS.getAllBytes(), TYPE.value);

        bytes = Bytes.concatBytes(bytes, vin.getAllBytes(CHARSET));

        bytes = Bytes.concatBytes(bytes, powerTrain.getByte());

        bytes = Bytes.concatBytes(bytes, (byte) modelName.length());
        bytes = Bytes.concatBytes(bytes, modelName.getAllBytes(CHARSET));

        bytes = Bytes.concatBytes(bytes, (byte) name.length());
        bytes = Bytes.concatBytes(bytes, name.getAllBytes(CHARSET));

        bytes = Bytes.concatBytes(bytes, (byte) licensePlate.length());
        bytes = Bytes.concatBytes(bytes, licensePlate.getAllBytes(CHARSET));

        bytes = Bytes.concatBytes(bytes, (byte) states.length);

        for (int i = 0; i < states.length; i++) {
            byte[] capabilityBytes = states[i].getAllBytes();
            bytes = Bytes.concatBytes(bytes, capabilityBytes);
        }

        return bytes;
    }
    */

    /**
     * @return The specific states for the vehicle's features.
     */
    public Command[] getStates() {
        return states;
    }

    /**
     * @return The vehicle's VIN number
     */
    public String getVin() {
        return vin;
    }

    /**
     * @return The vehicle's power train
     */
    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    /**
     * @return The vehicle's model name
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @return The vehicle's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The vehicle's license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     *
     * @return The sales designation of the model
     */
    public String getSalesDesignation() {
        return salesDesignation;
    }

    /**
     *
     * @return The car model manufacturing year number
     */
    public Integer getModelYear() {
        return modelYear;
    }

    /**
     *
     * @return The color name
     */
    public String getColorName() {
        return color;
    }

    /**
     *
     * @return The power of the car measured in kw
     */
    public Integer getPower() {
        return power;
    }

    /**
     *
     * @return The number of doors
     */
    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    /**
     *
     * @return The number of seats
     */
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    Command[] states;

    String vin;
    PowerTrain powerTrain;
    String modelName;
    String name;
    String licensePlate;

    String salesDesignation;
    Integer modelYear;
    String color;
    Integer power;
    Integer numberOfDoors;
    Integer numberOfSeats;

    VehicleStatus(byte[] bytes) throws CommandParseException {
        super(bytes);

        ArrayList<Command> states = new ArrayList<>();

        for (int i = 0; i < getProperties().length; i++) {
            Property property = getProperties()[i];
            try {
                switch (property.getPropertyIdentifier()) {
                    case 0x01:
                        vin = Property.getString(property.getValueBytes());
                        break;
                    case 0x02:
                        powerTrain = PowerTrain.fromByte(property.getValueByte());
                        break;
                    case 0x03:
                        modelName = Property.getString(property.getValueBytes());
                        break;
                    case 0x04:
                        name = Property.getString(property.getValueBytes());
                        break;
                    case 0x05:
                        licensePlate = Property.getString(property.getValueBytes());
                        break;
                    case 0x06:
                        salesDesignation = Property.getString(property.getValueBytes());
                        break;
                    case 0x07:
                        modelYear = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case 0x08:
                        color = Property.getString(property.getValueBytes());
                        break;
                    case 0x09:
                        power = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case 0x0A:
                        numberOfDoors = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case 0x0B:
                        numberOfSeats = Property.getUnsignedInt(property.getValueBytes());
                        break;
                    case (byte) 0x99:
                        byte[] commandBytes = property.getValueBytes();
                        Command command = CommandResolver.resolve(commandBytes);
                        states.add(command);
                        break;
                }
            }
            catch (UnsupportedEncodingException e) {
                throw new CommandParseException(CommandParseException.CommandExceptionCode.UNSUPPORTED_VALUE_TYPE);
            }
        }

        this.states = states.toArray(new Command[states.size()]);
    }
}
