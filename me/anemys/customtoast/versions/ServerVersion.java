package me.anemys.anecustomtoast.versions;

public enum ServerVersion {
    V1_20_4("1.20.4"),
    V1_20_5("1.20.5"),
    V1_20_6("1.20.6"),
    V1_21("1.21"),
    V1_22("1.22"); //hope :'(

    private final String version;

    ServerVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public static boolean isNewVersion(String serverVersion) {
        for (ServerVersion server : ServerVersion.values()) {
            if (serverVersion.contains(server.getVersion())) {
                return true;
            }
        }
        return false;
    }
}
