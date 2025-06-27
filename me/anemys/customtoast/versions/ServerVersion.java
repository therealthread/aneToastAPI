package me.anemys.anecustomtoast.versions;

public class ServerVersion {

    /**
     * Version type based on server version
     */
    public static VersionType getVersionType(String serverVersion) {
        // LEGACY: 1.16 - 1.20.4
        if (serverVersion.contains("1.16") ||
                serverVersion.contains("1.17") ||
                serverVersion.contains("1.18") ||
                serverVersion.contains("1.19") ||
                serverVersion.contains("1.20.0") ||
                serverVersion.contains("1.20.1") ||
                serverVersion.contains("1.20.2") ||
                serverVersion.contains("1.20.3") ||
                serverVersion.contains("1.20.4")) {
            return VersionType.LEGACY;
        }

        // MIDDLE: 1.20.5 - 1.21.3
        if (serverVersion.contains("1.20.5") ||
                serverVersion.contains("1.20.6") ||
                (serverVersion.contains("1.21") &&
                        !serverVersion.contains("1.21.4") &&
                        !serverVersion.contains("1.21.5") &&
                        !serverVersion.contains("1.21.6") &&
                        !serverVersion.contains("1.21.7") &&
                        !serverVersion.contains("1.21.8") &&
                        !serverVersion.contains("1.21.9"))) { //i hope... i will have a long vacation :'(

            return VersionType.MIDDLE;
        }

        // MODERN: 1.21.4 >>
        return VersionType.MODERN;
    }

    /**
     * @deprecated use getVersionType() instead [oldest]
     */
    @Deprecated
    public static boolean isNewVersion(String serverVersion) {
        VersionType versionType = getVersionType(serverVersion);
        return versionType == VersionType.MODERN;
    }
}
