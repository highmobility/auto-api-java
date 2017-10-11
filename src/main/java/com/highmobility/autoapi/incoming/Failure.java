package com.highmobility.autoapi.incoming;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

/**
 * Created by ttiganik on 28/09/2016.
 */

public class Failure extends IncomingCommand {
    public enum Reason {
        UNSUPPORTED_CAPABILITY, UNAUTHORIZED, INCORRECT_STATE, EXECUTION_TIMEOUT, VEHICLE_ASLEEP, INVALID_COMMAND
    }

    private Command.Type failedType;
    private Reason failureReason;

    public Failure(byte[] bytes) throws CommandParseException {
        super(bytes);

        if (bytes.length != 7) throw new CommandParseException();

        failedType = Command.typeFromBytes(bytes[3], bytes[4], bytes[5]);

        switch (bytes[6]) {
            case 0x00:
                failureReason = Reason.UNSUPPORTED_CAPABILITY;
                break;
            case 0x01:
                failureReason = Reason.UNAUTHORIZED;
                break;
            case 0x02:
                failureReason = Reason.INCORRECT_STATE;
                break;
            case 0x03:
                failureReason = Reason.EXECUTION_TIMEOUT;
                break;
            case 0x04:
                failureReason = Reason.VEHICLE_ASLEEP;
                break;
            case 0x05:
                failureReason = Reason.INVALID_COMMAND;
                break;
            default:
                throw new CommandParseException();
        }
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
     * @return The failure reason.
     */
    public Reason getFailureReason() {
        return failureReason;
    }
}