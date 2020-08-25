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
    int logCount;
    int errorLogCount;

    @Override public String getName() {
        return null;
    }

    @Override public boolean isTraceEnabled() {
        return false;
    }

    @Override public void trace(String msg) {
        logCount++;
    }

    @Override public void trace(String format, Object arg) {
        logCount++;
    }

    @Override public void trace(String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void trace(String format, Object... arguments) {
        logCount++;
    }

    @Override public void trace(String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override public void trace(Marker marker, String msg) {
        logCount++;
    }

    @Override public void trace(Marker marker, String format, Object arg) {
        logCount++;
    }

    @Override public void trace(Marker marker, String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void trace(Marker marker, String format, Object... argArray) {
        logCount++;
    }

    @Override public void trace(Marker marker, String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isDebugEnabled() {
        return false;
    }

    @Override public void debug(String msg) {
        logCount++;
    }

    @Override public void debug(String format, Object arg) {
        logCount++;
    }

    @Override public void debug(String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void debug(String format, Object... arguments) {
        logCount++;
    }

    @Override public void debug(String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override public void debug(Marker marker, String msg) {
        logCount++;
    }

    @Override public void debug(Marker marker, String format, Object arg) {
        logCount++;
    }

    @Override public void debug(Marker marker, String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void debug(Marker marker, String format, Object... arguments) {
        logCount++;
    }

    @Override public void debug(Marker marker, String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isInfoEnabled() {
        return false;
    }

    @Override public void info(String msg) {
        logCount++;
    }

    @Override public void info(String format, Object arg) {
        logCount++;
    }

    @Override public void info(String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void info(String format, Object... arguments) {
        logCount++;
    }

    @Override public void info(String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override public void info(Marker marker, String msg) {
        logCount++;
    }

    @Override public void info(Marker marker, String format, Object arg) {
        logCount++;
    }

    @Override public void info(Marker marker, String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void info(Marker marker, String format, Object... arguments) {
        logCount++;
    }

    @Override public void info(Marker marker, String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isWarnEnabled() {
        return false;
    }

    @Override public void warn(String msg) {
        logCount++;
    }

    @Override public void warn(String format, Object arg) {
        logCount++;
    }

    @Override public void warn(String format, Object... arguments) {
        logCount++;
    }

    @Override public void warn(String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void warn(String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override public void warn(Marker marker, String msg) {
        logCount++;
    }

    @Override public void warn(Marker marker, String format, Object arg) {
        logCount++;
    }

    @Override public void warn(Marker marker, String format, Object arg1, Object arg2) {
        logCount++;
    }

    @Override public void warn(Marker marker, String format, Object... arguments) {
        logCount++;
    }

    @Override public void warn(Marker marker, String msg, Throwable t) {
        logCount++;
    }

    @Override public boolean isErrorEnabled() {
        return false;
    }

    @Override public void error(String msg) {
        errorLogCount++;
    }

    @Override public void error(String format, Object arg) {
        errorLogCount++;
    }

    @Override public void error(String format, Object arg1, Object arg2) {
        errorLogCount++;
    }

    @Override public void error(String format, Object... arguments) {
        errorLogCount++;
    }

    @Override public void error(String msg, Throwable t) {
        errorLogCount++;
    }

    @Override public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override public void error(Marker marker, String msg) {
        errorLogCount++;
    }

    @Override public void error(Marker marker, String format, Object arg) {
        errorLogCount++;
    }

    @Override public void error(Marker marker, String format, Object arg1, Object arg2) {
        errorLogCount++;
    }

    @Override public void error(Marker marker, String format, Object... arguments) {
        errorLogCount++;
    }

    @Override public void error(Marker marker, String msg, Throwable t) {
        errorLogCount++;
    }
}
