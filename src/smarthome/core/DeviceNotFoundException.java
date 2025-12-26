/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.core;

public class DeviceNotFoundException extends Exception {
    private final String deviceId;
    
    public DeviceNotFoundException(String deviceId) {
        super("Device with ID \"" + deviceId + "\" not found in the system.");
        this.deviceId = deviceId;
    }
    
    public DeviceNotFoundException(String deviceId, String additionalInfo) {
        super("Device with ID \"" + deviceId + "\" not found. " + additionalInfo);
        this.deviceId = deviceId;
    }
    
    public String getDeviceId() { return deviceId; }
    
    @Override
    public String toString() {
        return "DeviceNotFoundException: " + getMessage();
    }
}
