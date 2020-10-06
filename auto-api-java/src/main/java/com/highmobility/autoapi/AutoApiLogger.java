package com.highmobility.autoapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoApiLogger {
    private static Logger logger = null;

    public static Logger getLogger() {
        if (logger == null) logger = LoggerFactory.getLogger(AutoApiLogger.class);
        return logger;
    }
}
