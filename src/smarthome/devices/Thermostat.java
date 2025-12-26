/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

import smarthome.core.SmartDevice;
import smarthome.core.EnergyConsumer;
import smarthome.core.Schedulable;

public class Thermostat extends SmartDevice implements EnergyConsumer, Schedulable {
    private double currentTemperature;
    private double targetTemperature;
    private String mode;
    private String fanSpeed;
    private double humidity;
    private String scheduledTime;
    private String scheduledAction;
    
    public Thermostat(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.currentTemperature = 22.0;
        this.targetTemperature = 22.0;
        this.mode = "AUTO";
        this.fanSpeed = "AUTO";
        this.humidity = 45.0;
        this.scheduledTime = null;
        this.scheduledAction = null;
    }
    
    public Thermostat(String deviceId, String deviceName, double initialTemperature) {
        super(deviceId, deviceName);
        this.currentTemperature = initialTemperature;
        this.targetTemperature = initialTemperature;
        this.mode = "AUTO";
        this.fanSpeed = "AUTO";
        this.humidity = 45.0;
        this.scheduledTime = null;
        this.scheduledAction = null;
    }
    
    @Override
    public String getDeviceType() {
        return "THERMOSTAT";
    }
    
    @Override
    public String getDetailedInfo() {
        return String.format("Current: %.1f°C | Target: %.1f°C | Mode: %s | Fan: %s | Humidity: %.1f%%",
            currentTemperature, targetTemperature, mode, fanSpeed, humidity);
    }
    
    @Override
    public double getPowerConsumption() {
        if (!isPoweredOn() || mode.equals("OFF")) {
            return 2.0;
        }
        
        double consumption = 50.0;
        double tempDiff = Math.abs(targetTemperature - currentTemperature);
        consumption += tempDiff * 15.0;
        
        switch (fanSpeed) {
            case "LOW": consumption += 20.0; break;
            case "MEDIUM": consumption += 40.0; break;
            case "HIGH": consumption += 60.0; break;
            case "AUTO": consumption += 30.0; break;
        }
        
        return Math.round(consumption * 100.0) / 100.0;
    }
    
    @Override
    public void schedule(String time, String action) {
        this.scheduledTime = time;
        this.scheduledAction = action;
        System.out.println("[LOG] " + getDeviceName() + " scheduled: " + action + " at " + time);
    }
    
    @Override
    public void cancelSchedule(String scheduleId) {
        if (this.scheduledTime != null) {
            System.out.println("[LOG] " + getDeviceName() + " schedule cancelled: " + scheduledAction);
            this.scheduledTime = null;
            this.scheduledAction = null;
        }
    }
    
    public void setTargetTemperature(double temperature) {
        if (temperature < 10.0 || temperature > 35.0) {
            throw new IllegalArgumentException("Temperature must be between 10°C and 35°C");
        }
        
        double oldTarget = this.targetTemperature;
        this.targetTemperature = temperature;
        System.out.println("[LOG] " + getDeviceName() + " target temperature changed from " + oldTarget + "°C to " + temperature + "°C");
        
        if (isPoweredOn()) {
            adjustTemperature();
        }
    }
    
    public void setMode(String mode) {
        String[] validModes = {"HEAT", "COOL", "AUTO", "OFF"};
        boolean isValid = false;
        
        for (String validMode : validModes) {
            if (validMode.equalsIgnoreCase(mode)) {
                isValid = true;
                mode = validMode;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("Invalid mode. Use: HEAT, COOL, AUTO, OFF");
        }
        
        String oldMode = this.mode;
        this.mode = mode;
        System.out.println("[LOG] " + getDeviceName() + " mode changed from " + oldMode + " to " + mode);
        
        if (mode.equals("OFF")) {
            super.turnOff();
        } else if (!isPoweredOn()) {
            super.turnOn();
        }
    }
    
    public void setFanSpeed(String speed) {
        String[] validSpeeds = {"AUTO", "LOW", "MEDIUM", "HIGH"};
        boolean isValid = false;
        
        for (String validSpeed : validSpeeds) {
            if (validSpeed.equalsIgnoreCase(speed)) {
                isValid = true;
                speed = validSpeed;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("Invalid fan speed. Use: AUTO, LOW, MEDIUM, HIGH");
        }
        
        String oldSpeed = this.fanSpeed;
        this.fanSpeed = speed;
        System.out.println("[LOG] " + getDeviceName() + " fan speed changed from " + oldSpeed + " to " + speed);
    }
    
    private void adjustTemperature() {
        System.out.println("[THERMOSTAT] " + getDeviceName() + " adjusting to " + targetTemperature + "°C...");
    }
    
    @Override
    public void turnOn() {
        super.turnOn();
        if (mode.equals("OFF")) {
            mode = "AUTO";
        }
        System.out.println("[THERMOSTAT] " + getDeviceName() + " is now maintaining " + targetTemperature + "°C");
    }
    
    @Override
    public void turnOff() {
        super.turnOff();
        mode = "OFF";
        System.out.println("[THERMOSTAT] " + getDeviceName() + " is now OFF");
    }
    
    // Getters
    public double getCurrentTemperature() { return currentTemperature; }
    public double getTargetTemperature() { return targetTemperature; }
    public String getMode() { return mode; }
    public String getFanSpeed() { return fanSpeed; }
    public double getHumidity() { return humidity; }
    public String getScheduledTime() { return scheduledTime; }
    public String getScheduledAction() { return scheduledAction; }
}