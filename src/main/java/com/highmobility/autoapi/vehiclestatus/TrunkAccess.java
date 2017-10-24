package com.highmobility.autoapi.vehiclestatus;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandParseException;

import com.highmobility.autoapi.incoming.TrunkState;

/**
 * Created by ttiganik on 14/12/2016.
 */

public class TrunkAccess extends FeatureState {
    TrunkState.LockState lockState;
    TrunkState.Position position;

    public TrunkAccess(TrunkState.LockState lockState, TrunkState.Position position) {
        super(Command.Identifier.TRUNK_ACCESS);
        this.lockState = lockState;
        this.position = position;

        bytes = getBytesWithOneByteLongFields(2);
        bytes[3] = lockState.getByte();
        bytes[4] = position.getByte();
    }

    TrunkAccess(byte[] bytes) throws CommandParseException {
        super(Command.Identifier.TRUNK_ACCESS);

        lockState = TrunkState.LockState.fromByte(bytes[3]);
        position = TrunkState.Position.fromByte(bytes[4]);
        this.bytes = bytes;
    }

    public TrunkState.LockState getLockState() {
        return lockState;
    }

    public TrunkState.Position getPosition() {
        return position;
    }
}
