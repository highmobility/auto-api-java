/*
 * The MIT License
 *
 * Copyright (c) 2014- High-Mobility GmbH (https://high-mobility.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.highmobility.autoapi;

import org.slf4j.Logger;
import org.slf4j.Marker;

class FakeLogger implements Logger {
    int debugLogCount;
    int errorLogCount;
    int warningLogCount;
    boolean showLogs = false;

    @Override public String getName() {
        return null;
    }

    @Override public boolean isTraceEnabled() {
        return false;
    }

    @Override public void trace(String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void trace(String format, Object arg) {
        debugLogCount++;
        log(String.format(format, arg));
    }

    @Override public void trace(String format, Object arg1, Object arg2) {
        debugLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void trace(String format, Object... arguments) {
        debugLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void trace(String msg, Throwable t) {
        debugLogCount++;
        log(msg);
    }

    @Override public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override public void trace(Marker marker, String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void trace(Marker marker, String format, Object arg) {
        debugLogCount++;
        log(String.format(format, arg));
    }

    @Override public void trace(Marker marker, String format, Object arg1, Object arg2) {
        debugLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void trace(Marker marker, String format, Object... argArray) {
        debugLogCount++;
        log(String.format(format, argArray));
    }

    @Override public void trace(Marker marker, String msg, Throwable t) {
        debugLogCount++;
        log(msg);
    }

    @Override public boolean isDebugEnabled() {
        return false;
    }

    @Override public void debug(String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void debug(String format, Object arg) {
        debugLogCount++;
        log(String.format(format, arg));
    }

    @Override public void debug(String format, Object arg1, Object arg2) {
        debugLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void debug(String format, Object... arguments) {
        debugLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void debug(String msg, Throwable t) {
        debugLogCount++;
        log(msg);
    }

    @Override public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override public void debug(Marker marker, String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void debug(Marker marker, String format, Object arg) {
        debugLogCount++;
        log(String.format(format, arg));
    }

    @Override public void debug(Marker marker, String format, Object arg1, Object arg2) {
        debugLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void debug(Marker marker, String format, Object... arguments) {
        debugLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void debug(Marker marker, String msg, Throwable t) {
        debugLogCount++;
        log(msg);
    }

    @Override public boolean isInfoEnabled() {
        return false;
    }

    @Override public void info(String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void info(String format, Object arg) {
        debugLogCount++;
        log(String.format(format, arg));
    }

    @Override public void info(String format, Object arg1, Object arg2) {
        debugLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void info(String format, Object... arguments) {
        debugLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void info(String msg, Throwable t) {
        debugLogCount++;
        log(msg);
    }

    @Override public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override public void info(Marker marker, String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void info(Marker marker, String format, Object arg) {
        debugLogCount++;
        log(String.format(format, arg));
    }

    @Override public void info(Marker marker, String format, Object arg1, Object arg2) {
        debugLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void info(Marker marker, String format, Object... arguments) {
        debugLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void info(Marker marker, String msg, Throwable t) {
        debugLogCount++;
        log(msg);
    }

    @Override public boolean isWarnEnabled() {
        return false;
    }

    @Override public void warn(String msg) {
        debugLogCount++;
        log(msg);
    }

    @Override public void warn(String format, Object arg) {
        warningLogCount++;
        log(String.format(format, arg));
    }

    @Override public void warn(String format, Object... arguments) {
        warningLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void warn(String format, Object arg1, Object arg2) {
        warningLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void warn(String msg, Throwable t) {
        warningLogCount++;
        log(msg);
    }

    @Override public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override public void warn(Marker marker, String msg) {
        warningLogCount++;
        log(msg);
    }

    @Override public void warn(Marker marker, String format, Object arg) {
        warningLogCount++;
        log(String.format(format, arg));
    }

    @Override public void warn(Marker marker, String format, Object arg1, Object arg2) {
        warningLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void warn(Marker marker, String format, Object... arguments) {
        warningLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void warn(Marker marker, String msg, Throwable t) {
        warningLogCount++;
        log(msg);
    }

    @Override public boolean isErrorEnabled() {
        return false;
    }

    @Override public void error(String msg) {
        errorLogCount++;
        log(msg);
    }

    @Override public void error(String format, Object arg) {
        errorLogCount++;
        log(String.format(format, arg));
    }

    @Override public void error(String format, Object arg1, Object arg2) {
        errorLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void error(String format, Object... arguments) {
        errorLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void error(String msg, Throwable t) {
        errorLogCount++;
        log(msg);
    }

    @Override public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override public void error(Marker marker, String msg) {
        errorLogCount++;
        log(msg);
    }

    @Override public void error(Marker marker, String format, Object arg) {
        errorLogCount++;
        log(String.format(format, arg));
    }

    @Override public void error(Marker marker, String format, Object arg1, Object arg2) {
        errorLogCount++;
        log(String.format(format, arg1));
    }

    @Override public void error(Marker marker, String format, Object... arguments) {
        errorLogCount++;
        log(String.format(format, arguments));
    }

    @Override public void error(Marker marker, String msg, Throwable t) {
        errorLogCount++;
        log(msg);
    }

    private void log(String msg) {
        if (showLogs) System.out.println(msg);
    }
}
