/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.core;

import java.time.LocalDateTime;

public abstract class SmartDevice implements Controllable {
    private final String deviceId;
    private String deviceName;
    private String location;
    private boolean isPoweredOn;
    private boolean isOnline;
    private LocalDateTime lastUpdated;

    public SmartDevice(String deviceId, String deviceName) {
        if (deviceId == null || deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Device ID cannot be empty");
        }
        if (deviceName == null || deviceName.trim().isEmpty()) {
            throw new IllegalArgumentException("Device name cannot be empty");
        }
        
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.location = "Unassigned";
        this.isPoweredOn = false;
        this.isOnline = true;
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Abstract methods
    public abstract String getDeviceType();
    public abstract String getDetailedInfo();
    
    // Implement Controllable interface
    @Override
    public void turnOn() {
        if (!isOnline) {
            System.out.println("[ERROR] " + deviceName + " is offline. Cannot turn on.");
            return;
        }
        
        if (!isPoweredOn) {
            isPoweredOn = true;
            lastUpdated = LocalDateTime.now();
            System.out.println("[ACTION] " + deviceName + " turned ON at " + location);
        }
    }
    
    @Override
    public void turnOff() {
        if (isPoweredOn) {
            isPoweredOn = false;
            lastUpdated = LocalDateTime.now();
            System.out.println("[ACTION] " + deviceName + " turned OFF at " + location);
        }
    }
    
    @Override
    public String getStatus() {
        String powerStatus = isPoweredOn ? "ON" : "OFF";
        String onlineStatus = isOnline ? "Online" : "Offline";
        
        return String.format("[%s] %s | Location: %s | Power: %s | Status: %s",
            getDeviceType(), deviceName, location, powerStatus, onlineStatus);
    }
    
    // Getters and setters
    public void setLocation(String location) {
        this.location = location;
        lastUpdated = LocalDateTime.now();
    }
    
    public void setOnline(boolean online) {
        this.isOnline = online;
        lastUpdated = LocalDateTime.now();
    }
    
    public String getDeviceId() { return deviceId; }
    public String getDeviceName() { return deviceName; }
    public String getLocation() { return location; }
    public boolean isPoweredOn() { return isPoweredOn; }
    public boolean isOnline() { return isOnline; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    
    public void printDeviceInfo() {
        System.out.println("=== Device Information ===");
        System.out.println("ID: " + deviceId);
        System.out.println("Name: " + deviceName);
        System.out.println("Type: " + getDeviceType());
        System.out.println("Location: " + location);
        System.out.println("Status: " + (isPoweredOn ? "Powered ON" : "Powered OFF"));
        System.out.println("Connection: " + (isOnline ? "Online" : "Offline"));
        System.out.println("Last Updated: " + lastUpdated.toString().substring(0, 16));
        System.out.println("Detailed Info: " + getDetailedInfo());
        System.out.println("==================");
    }
}
