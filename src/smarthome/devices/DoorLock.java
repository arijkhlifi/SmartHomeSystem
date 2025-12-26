/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

import smarthome.core.SmartDevice;

public class DoorLock extends SmartDevice {
    private boolean locked;
    
    public DoorLock(String id, String name) {
        super(id, name);
        this.locked = true; // Default to locked for security
    }
    
    public void lock() {
        locked = true;
        System.out.println("[ACTION] " + getDeviceName() + " locked");
    }
    
    public void unlock() {
        locked = false;
        System.out.println("[ACTION] " + getDeviceName() + " unlocked");
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    @Override
    public String getDeviceType() {
        return "DoorLock";
    }
    
    @Override
    public String getDetailedInfo() {
        return String.format("Lock Status: %s | Powered: %s", 
            locked ? "LOCKED" : "UNLOCKED", 
            isPoweredOn() ? "ON" : "OFF");
    }
    
    @Override
    public void turnOn() {
        super.turnOn(); // Calls the parent method
        // Auto-unlock when powered on? Maybe not for security
        // unlock(); // Uncomment if you want auto-unlock
    }
    
    @Override
    public void turnOff() {
        super.turnOff(); // Calls the parent method
        // Auto-lock when powered off for security
        lock();
    }
}
