package com.highmobility.autoapi.property;

import com.highmobility.utils.Bytes;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class StringProperty extends Property {
    public static final String CHARSET = "UTF-8";

    public StringProperty(byte identifier, String value) throws UnsupportedEncodingException {
        super(identifier, value.length());
        byte[] stringBytes = value.getBytes(CHARSET);
        Bytes.setBytes(bytes, stringBytes, 3);
    }

    public static HMProperty[] getProperties(String url) throws
            UnsupportedEncodingException {
        List<HMProperty> propertiesBuilder = new ArrayList<>();

        if (url != null) propertiesBuilder.add(new StringProperty((byte) 0x01, url));

        return propertiesBuilder.toArray(new HMProperty[propertiesBuilder.size()]);
    }
}