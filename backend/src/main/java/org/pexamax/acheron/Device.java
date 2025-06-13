package org.pexamax.acheron;

import java.time.Instant;

class Device {
    private static int lastDeviceID = 0;  // Replace with value from DB

    private int deviceID;
    private String sessionToken;
    private String deviceToken;
    private Instant sessionTokenExpiry;
    private Instant deviceTokenExpiry;

    public Device(String sessionToken, String deviceToken, Instant sessionTokenExpiry, Instant deviceTokenExpiry) {
        deviceID = lastDeviceID++;
        this.sessionToken = sessionToken;
        this.deviceToken = deviceToken;
        this.sessionTokenExpiry = sessionTokenExpiry;
        this.deviceTokenExpiry = deviceTokenExpiry;
    }
}