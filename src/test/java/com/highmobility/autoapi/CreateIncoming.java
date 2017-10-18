package com.highmobility.autoapi;

import com.highmobility.autoapi.incoming.Failure;
import com.highmobility.utils.Bytes;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class CreateIncoming {
    @Test public void failure() {
        byte[] expectedBytes = Bytes.bytesFromHex("00020100210001");
        byte[] bytes = Failure.createBytes(Command.TrunkAccess.GET_TRUNK_STATE, Failure.Reason.UNAUTHORIZED);
        assertTrue(Arrays.equals(expectedBytes, bytes));
    }

    

}
