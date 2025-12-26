/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

import smarthome.core.SmartDevice;
import smarthome.core.EnergyConsumer;

public class SmartPlug extends SmartDevice implements EnergyConsumer {
    private final double maxWattage;
    private double currentConsumption;
    
    public SmartPlug(String id, String name, double maxWattage) {
        super(id, name);
        if (maxWattage <= 0) {
            throw new IllegalArgumentException("Max wattage must be positive");
        }
        this.maxWattage = maxWattage;
        this.currentConsumption = 0.0;
    }
    
    // Method to set current consumption (for connected device)
    public void setCurrentConsumption(double watts) {
        if (watts < 0 || watts > maxWattage) {
            throw new IllegalArgumentException(
                String.format("Consumption must be between 0 and %.1fW", maxWattage)
            );
        }
        this.currentConsumption = watts;
    }
    
    @Override
    public String getDeviceType() {
        return "SmartPlug";
    }
    
    @Override
    public String getDetailedInfo() {
        return String.format(
            "Max Wattage: %.1fW | Current: %.1fW | Connected Device Power: %.1fW",
            maxWattage, 
            getPowerConsumption(),
            currentConsumption
        );
    }
    
    // Implement EnergyConsumer interface
    @Override
    public double getPowerConsumption() {
        // Smart plug consumes some power itself plus the connected device
        double basePower = isPoweredOn() ? 1.0 : 0.1; // 1W when on, 0.1W standby
        return basePower + (isPoweredOn() ? currentConsumption : 0);
    }
    
    @Override
    public void turnOn() {
        super.turnOn();
        System.out.println("[SMARTPLUG] " + getDeviceName() + " powered outlet");
    }
    
    @Override
    public void turnOff() {
        super.turnOff();
        currentConsumption = 0.0; // Reset when turned off
        System.out.println("[SMARTPLUG] " + getDeviceName() + " disconnected power");
    }
    
    // Getters
    public double getMaxWattage() {
        return maxWattage;
    }
    
    public double getCurrentConsumption() {
        return currentConsumption;
    }
    
    // Helper method to simulate plugging in a device
    public void plugInDevice(String deviceName, double wattage) {
        if (wattage > maxWattage) {
            System.out.println("[WARNING] " + deviceName + " (" + wattage + 
                             "W) exceeds plug capacity (" + maxWattage + "W)");
            return;
        }
        
        setCurrentConsumption(wattage);
        System.out.println("[SMARTPLUG] " + deviceName + " (" + wattage + 
                          "W) plugged into " + getDeviceName());
    }
}