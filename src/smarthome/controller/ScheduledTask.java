/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

import smarthome.core.*;
import smarthome.home.*;
import smarthome.devices.*;

public class ScheduledTask {
    private String taskName;
    private String time; // Format: HH:mm (24-hour)
    private String action; // ON, OFF, SET_TEMPERATURE, etc.
    private String targetDevice; // Device ID, device type, or room name
    private String parameter; // Optional parameter (e.g., temperature value)
    private boolean enabled;
    private CentralController controller;

    public ScheduledTask(String taskName, String time, String action,
        String targetDevice, CentralController controller) {
        this(taskName, time, action, targetDevice, null, controller);
    }

    public ScheduledTask(String taskName, String time, String action,
        String targetDevice, String parameter, CentralController controller) {
        this.taskName = taskName;
        this.time = time;
        this.action = action.toUpperCase();
        this.targetDevice = targetDevice;
        this.parameter = parameter;
        this.enabled = true;
        this.controller = controller;
    }

    public void execute() {
        Home home = controller.getHome();

        switch (action) {
            case "ON":
                executeOnAction(home);
                break;
            case "OFF":
                executeOffAction(home);
                break;
            case "SET_TEMPERATURE":
                executeSetTemperature(home);
                break;
            case "SET_BRIGHTNESS":
                executeSetBrightness(home);
                break;
            case "REDUCE_ENERGY":
                controller.reduceEnergyUsage();
                break;
            default:
                System.out.println("  [ERROR] Unknown action: " + action);
        }
    }

    private void executeOnAction(Home home) {
        // Check if target is a device ID
        try {
            SmartDevice device = home.findDeviceById(targetDevice);
            device.turnOn();
            System.out.println("  ✓ Turned ON device: " + device.getDeviceName());
            return;
        } catch (DeviceNotFoundException e) {
            // Not a device ID, continue checking
        }

        // Check if target is a room name
        Room room = home.getRoomByName(targetDevice);
        if (room != null) {
            room.turnAllDevicesOn();
            System.out.println("  ✓ Turned ON all devices in room: " + targetDevice);
            return;
        }

        // Assume target is a device type
        home.turnAllDevicesByTypeOn(targetDevice);
        System.out.println("  ✓ Turned ON all " + targetDevice + " devices");
    }

    private void executeOffAction(Home home) {
        // Check if target is a device ID
        try {
            SmartDevice device = home.findDeviceById(targetDevice);
            device.turnOff();
            System.out.println("  ✓ Turned OFF device: " + device.getDeviceName());
            return;
        } catch (DeviceNotFoundException e) {
            // Not a device ID, continue checking
        }

        // Check if target is a room name
        Room room = home.getRoomByName(targetDevice);
        if (room != null) {
            room.turnAllDevicesOff();
            System.out.println("  ✓ Turned OFF all devices in room: " + targetDevice);
            return;
        }

        // Assume target is a device type
        home.turnAllDevicesByTypeOff(targetDevice);
        System.out.println("  ✓ Turned OFF all " + targetDevice + " devices");
    }

    private void executeSetTemperature(Home home) {
        if (parameter == null) {
            System.out.println("  [ERROR] No temperature parameter provided");
            return;
        }

        try {
            double temp = Double.parseDouble(parameter);
            SmartDevice device = home.findDeviceById(targetDevice);

            if (device instanceof Thermostat) {
                Thermostat thermostat = (Thermostat) device;
                thermostat.setTargetTemperature(temp);
                System.out.println("  ✓ Set temperature to " + temp + "°C for: " + device.getDeviceName());
            } else {
                System.out.println("  [ERROR] Device is not a thermostat");
            }
        } catch (DeviceNotFoundException e) {
            System.out.println("  [ERROR] Device not found: " + targetDevice);
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Invalid temperature value: " + parameter);
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    private void executeSetBrightness(Home home) {
        if (parameter == null) {
            System.out.println("  [ERROR] No brightness parameter provided");
            return;
        }

        try {
            int brightness = Integer.parseInt(parameter);
            SmartDevice device = home.findDeviceById(targetDevice);

            if (device instanceof Light) {
                Light light = (Light) device;
                light.setBrightness(brightness);
                System.out.println("  ✓ Set brightness to " + brightness + "% for: " + device.getDeviceName());
            } else {
                System.out.println("  [ERROR] Device is not a light");
            }
        } catch (DeviceNotFoundException e) {
            System.out.println("  [ERROR] Device not found: " + targetDevice);
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Invalid brightness value: " + parameter);
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // Getters and Setters
    public String getTaskName() {
        return taskName;
    }

    public String getTime() {
        return time;
    }

    public String getAction() {
        return action;
    }

    public String getTargetDevice() {
        return targetDevice;
    }

    public String getParameter() {
        return parameter;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(taskName).append(" - ");
        sb.append("Time: ").append(time).append(", ");
        sb.append("Action: ").append(action).append(", ");
        sb.append("Target: ").append(targetDevice);
        if (parameter != null) {
            sb.append(", Parameter: ").append(parameter);
        }
        sb.append(" [").append(enabled ? "Enabled" : "Disabled").append("]");
        return sb.toString();
    }
}
