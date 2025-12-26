/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.devices;

import smarthome.core.SmartDevice;
import smarthome.core.EnergyConsumer;
import smarthome.core.Schedulable;

public class SmartTV extends SmartDevice implements EnergyConsumer, Schedulable {
    private int volume;
    private int channel;
    private String currentApp;
    private boolean isMuted;
    private int screenBrightness;
    private String pictureMode;
    private String recordingSchedule;
    private String recordingProgram;
    
    public SmartTV(String deviceId, String deviceName) {
        super(deviceId, deviceName);
        this.volume = 25;
        this.channel = 1;
        this.currentApp = "Home";
        this.isMuted = false;
        this.screenBrightness = 50;
        this.pictureMode = "Standard";
        this.recordingSchedule = null;
        this.recordingProgram = null;
    }
    
    @Override
    public String getDeviceType() {
        return "SMART_TV";
    }
    
    @Override
    public String getDetailedInfo() {
        return String.format("Channel: %d | App: %s | Volume: %d | Brightness: %d%% | Picture: %s",
            channel, currentApp, volume, screenBrightness, pictureMode);
    }
    
    @Override
    public double getPowerConsumption() {
        if (!isPoweredOn()) return 1.0;
        
        double baseConsumption = 80.0;
        baseConsumption *= (screenBrightness / 100.0);
        
        switch (pictureMode.toLowerCase()) {
            case "movie": baseConsumption *= 1.1; break;
            case "sports": baseConsumption *= 1.15; break;
            case "game": baseConsumption *= 1.2; break;
        }
        
        if (!isMuted) {
            baseConsumption += (volume * 0.05);
        }
        
        if (currentApp.equals("Netflix") || currentApp.equals("YouTube")) {
            baseConsumption += 15.0;
        }
        
        return Math.round(baseConsumption * 100.0) / 100.0;
    }
    
    @Override
    public void schedule(String time, String action) {
        this.recordingSchedule = time;
        this.recordingProgram = action;
        System.out.println("[LOG] " + getDeviceName() + " recording scheduled: " + action + " at " + time);
    }
    
    @Override
    public void cancelSchedule(String scheduleId) {
        if (this.recordingSchedule != null) {
            System.out.println("[LOG] " + getDeviceName() + " recording cancelled: " + recordingProgram);
            this.recordingSchedule = null;
            this.recordingProgram = null;
        }
    }
    
    public void setVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        
        int oldVolume = this.volume;
        this.volume = volume;
        this.isMuted = (volume == 0);
        System.out.println("[LOG] " + getDeviceName() + " volume changed from " + oldVolume + " to " + volume);
    }
    
    public void changeChannel(int channel) {
        if (channel < 1 || channel > 999) {
            throw new IllegalArgumentException("Channel must be between 1 and 999");
        }
        
        int oldChannel = this.channel;
        this.channel = channel;
        this.currentApp = "Live TV";
        System.out.println("[LOG] " + getDeviceName() + " channel changed from " + oldChannel + " to " + channel);
    }
    
    public void openApp(String appName) {
        String[] availableApps = {"Netflix", "YouTube", "Disney+", "Prime Video", "Browser", "Settings", "Home"};
        boolean isValid = false;
        
        for (String app : availableApps) {
            if (app.equalsIgnoreCase(appName)) {
                isValid = true;
                appName = app;
                break;
            }
        }
        
        if (!isValid) {
            throw new IllegalArgumentException("App not available: " + appName);
        }
        
        String oldApp = this.currentApp;
        this.currentApp = appName;
        System.out.println("[LOG] " + getDeviceName() + " app changed from " + oldApp + " to " + appName);
    }
    
    public void mute() {
        if (!isMuted) {
            this.isMuted = true;
            System.out.println("[LOG] " + getDeviceName() + " muted");
        }
    }
    
    public void unmute() {
        if (isMuted) {
            this.isMuted = false;
            System.out.println("[LOG] " + getDeviceName() + " unmuted");
        }
    }
    
    public void toggleMute() {
        if (isMuted) {
            unmute();
        } else {
            mute();
        }
    }
    
    @Override
    public void turnOn() {
        super.turnOn();
        System.out.println("[TV] Welcome to " + getDeviceName() + "! Starting up...");
        System.out.println("[TV] Loading smart features...");
        System.out.println("[TV] Ready to use!");
    }
    
    @Override
    public void turnOff() {
        if (recordingSchedule != null) {
            System.out.println("[TV WARNING] Recording in progress. Use cancelSchedule() first.");
            return;
        }
        super.turnOff();
        System.out.println("[TV] Goodbye!");
    }
    
    // Getters
    public int getVolume() { return volume; }
    public int getChannel() { return channel; }
    public String getCurrentApp() { return currentApp; }
    public boolean isMuted() { return isMuted; }
    public int getScreenBrightness() { return screenBrightness; }
    public String getPictureMode() { return pictureMode; }
    public String getRecordingSchedule() { return recordingSchedule; }
    public String getRecordingProgram() { return recordingProgram; }
}
