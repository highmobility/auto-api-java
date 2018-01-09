package com.highmobility.autoapi;

/**
 * Created by ttiganik on 07/06/16.
 */
public class CommandParseException extends Exception{
    public enum CommandExceptionCode {
        // the command bytes could not be parsed
        PARSE_ERROR,
        // A property value was expected to be of a certain type that it wasn't
        UNSUPPORTED_VALUE_TYPE
    }

    public CommandExceptionCode code;

    public CommandParseException() {
        this.code = CommandExceptionCode.PARSE_ERROR;
    }

    public CommandParseException(CommandExceptionCode code) {
        this.code = code;
    }
}