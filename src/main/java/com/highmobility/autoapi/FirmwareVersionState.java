// TODO: license
package com.highmobility.autoapi;

import com.highmobility.autoapi.property.Property;
import com.highmobility.autoapi.value.HmkitVersion;

public class FirmwareVersionState extends SetCommand {
    Property<HmkitVersion> hmKitVersion = new Property(HmkitVersion.class, 0x01);
    Property<String> hmKitBuildName = new Property(String.class, 0x02);
    Property<String> applicationVersion = new Property(String.class, 0x03);

    /**
     * @return HMKit version
     */
    public Property<HmkitVersion> getHmKitVersion() {
        return hmKitVersion;
    }

    /**
     * @return HMKit version build name
     */
    public Property<String> getHmKitBuildName() {
        return hmKitBuildName;
    }

    /**
     * @return Application version
     */
    public Property<String> getApplicationVersion() {
        return applicationVersion;
    }

    FirmwareVersionState(byte[] bytes) throws CommandParseException {
        super(bytes);
        while (propertyIterator.hasNext()) {
            propertyIterator.parseNext(p -> {
                switch (p.getPropertyIdentifier()) {
                    case 0x01: return hmKitVersion.update(p);
                    case 0x02: return hmKitBuildName.update(p);
                    case 0x03: return applicationVersion.update(p);
                }

                return null;
            });
        }
    }

    @Override public boolean isState() {
        return true;
    }

    private FirmwareVersionState(Builder builder) {
        super(builder);

        hmKitVersion = builder.hmKitVersion;
        hmKitBuildName = builder.hmKitBuildName;
        applicationVersion = builder.applicationVersion;
    }

    public static final class Builder extends SetCommand.Builder {
        private Property<HmkitVersion> hmKitVersion;
        private Property<String> hmKitBuildName;
        private Property<String> applicationVersion;

        public Builder() {
            super(Identifier.FIRMWARE_VERSION);
        }

        public FirmwareVersionState build() {
            return new FirmwareVersionState(this);
        }

        /**
         * @param hmKitVersion HMKit version
         * @return The builder
         */
        public Builder setHmKitVersion(Property<HmkitVersion> hmKitVersion) {
            this.hmKitVersion = hmKitVersion.setIdentifier(0x01);
            addProperty(this.hmKitVersion);
            return this;
        }
        
        /**
         * @param hmKitBuildName HMKit version build name
         * @return The builder
         */
        public Builder setHmKitBuildName(Property<String> hmKitBuildName) {
            this.hmKitBuildName = hmKitBuildName.setIdentifier(0x02);
            addProperty(this.hmKitBuildName);
            return this;
        }
        
        /**
         * @param applicationVersion Application version
         * @return The builder
         */
        public Builder setApplicationVersion(Property<String> applicationVersion) {
            this.applicationVersion = applicationVersion.setIdentifier(0x03);
            addProperty(this.applicationVersion);
            return this;
        }
    }
}