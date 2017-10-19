package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;
import com.highmobility.utils.Bytes;

/**
 * Created by ttiganik on 28/09/2016.
 */
public class Failure extends IncomingCommand {
    public enum Reason {
        UNSUPPORTED_CAPABILITY((byte)0x00),
        UNAUTHORIZED((byte)0x01),
        INCORRECT_STATE((byte)0x02),
        EXECUTION_TIMEOUT((byte)0x03),
        VEHICLE_ASLEEP((byte)0x04),
        INVALID_COMMAND((byte)0x05);

        public byte getByte() {
            return reasonByte;
        }

        public static Reason fromByte(byte reasonByte) throws CommandParseException {
            Reason[] allValues = Reason.values();

            for (int i = 0; i < allValues.length; i++) {
                Reason reason = allValues[i];

                if (reason.getByte() == reasonByte) {
                    return reason;
                }
            }

            throw new CommandParseException();
        }

        Reason(byte reason) {
            this.reasonByte = reason;
        }
        private byte reasonByte;
    }

    private Command.Type failedType;
    private Reason failureReason;

    /**
     * Create the failure message command bytes
     *
     * @param failedType the command that failed
     * @param failureReason the failure reason
     * @return command bytes
     */
    public static byte[] getCommandBytes(Command.Type failedType, Reason failureReason) {
        byte[] bytes = Command.FailureMessage.FAILURE_MESSAGE.getIdentifierAndType();

        bytes = Bytes.concatBytes(bytes, failedType.getIdentifierAndType());
        bytes = Bytes.concatBytes(bytes, failureReason.getByte());

        return bytes;
    }

    public Failure(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 7) throw new CommandParseException();

        failedType = Command.typeFromBytes(bytes[3], bytes[4], bytes[5]);
        failureReason = Reason.fromByte(bytes[6]);
    }

    /**
     *
     * @return The type of the failed command.
     */
    public Command.Type getFailedType() {
        return failedType;
    }

    /**
     *
     * @return The failure reasonByte.
     */
    public Reason getFailureReason() {
        return failureReason;
    }
}