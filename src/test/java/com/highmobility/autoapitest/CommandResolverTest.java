package com.highmobility.autoapitest;

import com.highmobility.autoapi.Command;
import com.highmobility.autoapi.CommandResolver;
import com.highmobility.value.Bytes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommandResolverTest {
    @Test public void testReturnsNullFor2Bytes() {
        Bytes bytes = new Bytes("0049");
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command == null);
    }

    @Test public void testReturnsBaseClassForUnknownBytes() {
        Bytes bytes = new Bytes("00200208"); // invalid door lock state
        Command command = CommandResolver.resolve(bytes);
        assertTrue(command instanceof Command);
    }
}
