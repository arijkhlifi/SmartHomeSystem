/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

import smarthome.core.SmartDevice;
import smarthome.core.EnergyConsumer;

public class Light extends SmartDevice implements EnergyConsumer {
    private int brightness;
    private String colorTemperature;
    private boolean isDimmable;
    private double powerRating;
    
    public Light(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.brightness = 70;
        this.colorTemperature = "NEUTRAL";
        this.isDimmable = true;
        this.powerRating = 12.0;
    }
    
    public Light(String deviceId, String deviceName, int brightness, double powerRating) {
        super(deviceId, deviceName);
        this.isDimmable = true;
        this.colorTemperature = "NEUTRAL";
        this.powerRating = powerRating;
        this.brightness = brightness;
    }
    
    @Override
    public String getDeviceType() {
        return "LIGHT";
    }
    
    @Override
    public String getDetailedInfo() {
        return String.format("Brightness: %d%% | Color: %s | Power: %.1fW | Dimmable: %s",
            brightness, colorTemperature, powerRating, isDimmable ? "Yes" : "No");
    }
    
    @Override
    public double getPowerConsumption() {
        if (!isPoweredOn()) return 0.0;
        
        double consumption = powerRating * (brightness / 100.0);
        
        switch (colorTemperature) {
            case "WARM": consumption *= 0.95; break;
            case "COOL": consumption *= 1.05; break;
            case "DAYLIGHT": consumption *= 1.10; break;
        }
        
        return Math.round(consumption * 100.0) / 100.0;
    }
    
    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException("Brightness must be between 0 and 100");
        }
        
        if (!isDimmable && brightness != 0 && brightness != 100) {
            throw new IllegalStateException("This light is not dimmable. Use 0 or 100 only.");
        }
        
        int oldBrightness = this.brightness;
        this.brightness = brightness;
        System.out.println("[LOG] " + getDeviceName() + " brightness changed from " +
            oldBrightness + "% to " + brightness + "%");
    }
    
    public void setColorTemperature(String temperature) {
        String[] validTemps = {"WARM", "NEUTRAL", "COOL", "DAYLIGHT"};
        boolean isValid = false;
        
        for (String temp : validTemps) {
            if (temp.equalsIgnoreCase(temperature)) {
                isValid = true;
                temperature = temp;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("Invalid color temperature. Use: WARM, NEUTRAL, COOL, DAYLIGHT");
        }
        
        String oldTemp = this.colorTemperature;
        this.colorTemperature = temperature;
        System.out.println("[LOG] " + getDeviceName() + " color temperature changed from " + oldTemp + " to " + temperature);
    }
    
    public void setDimmable(boolean dimmable) {
        this.isDimmable = dimmable;
    }
    
    // Getters
    public int getBrightness() { return brightness; }
    public String getColorTemperature() { return colorTemperature; }
    public boolean isDimmable() { return isDimmable; }
    public double getPowerRating() { return powerRating; }
}
