// TODO: license
package com.highmobility.autoapi.v2;

import com.highmobility.autoapi.v2.property.Property;
import com.highmobility.autoapi.v2.value.HmkitVersion;

public class FirmwareVersionState extends Command {
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
     * @return HMKit version build name bytes formatted in UTF-8
     */
    public Property<String> getHmKitBuildName() {
        return hmKitBuildName;
    }

    /**
     * @return Application version bytes formatted in UTF-8
     */
    public Property<String> getApplicationVersion() {
        return applicationVersion;
    }

    FirmwareVersionState(byte[] bytes) {
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

    public static final class Builder extends Command.Builder {
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
            addProperty(hmKitVersion);
            return this;
        }
        
        /**
         * @param hmKitBuildName HMKit version build name bytes formatted in UTF-8
         * @return The builder
         */
        public Builder setHmKitBuildName(Property<String> hmKitBuildName) {
            this.hmKitBuildName = hmKitBuildName.setIdentifier(0x02);
            addProperty(hmKitBuildName);
            return this;
        }
        
        /**
         * @param applicationVersion Application version bytes formatted in UTF-8
         * @return The builder
         */
        public Builder setApplicationVersion(Property<String> applicationVersion) {
            this.applicationVersion = applicationVersion.setIdentifier(0x03);
            addProperty(applicationVersion);
            return this;
        }
    }
}