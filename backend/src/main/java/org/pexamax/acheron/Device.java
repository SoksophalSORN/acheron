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
        // deviceID = lastDeviceID++;
        // this.sessionToken = sessionToken;
        // this.deviceToken = deviceToken;
        // this.sessionTokenExpiry = sessionTokenExpiry;
        // this.deviceTokenExpiry = deviceTokenExpiry;
        this.deviceID(lastDeviceID++);
        this.sessionToken(sessionToken);
        this.deviceToken(deviceToken);
        this.sessionTokenExpiry(sessionTokenExpiry);
        this.deviceTokenExpiry(deviceTokenExpiry);
    }
    
    // Getters & Setters
    public String sessionToken() { return this.sessionToken; }
    public void sessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public int deviceID() { return this.deviceID; }
    public void deviceID(int deviceID) { this.deviceID = deviceID; }

    public String deviceToken() { return this.deviceToken; }
    public void deviceToken(String deviceToken) { this.deviceToken = deviceToken; }

    public Instant sessionTokenExpiry() { return this.sessionTokenExpiry; }
    public void sessionTokenExpiry(Instant sessionTokenExpiry) { this.sessionTokenExpiry = sessionTokenExpiry; }

    public Instant deviceTokenExpiry() { return this.deviceTokenExpiry; }
    public void deviceTokenExpiry(Instant deviceTokenExpiry) { this.deviceTokenExpiry = deviceTokenExpiry; }

}
