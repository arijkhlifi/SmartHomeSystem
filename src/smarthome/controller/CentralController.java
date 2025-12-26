/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

import smarthome.core.*;
import smarthome.home.*;
import smarthome.devices.*;
import java.util.*;

public class CentralController {
    private Home home;
    private List<ScheduledTask> scheduledTasks;

    public CentralController(Home home) {
        this.home = home;
        this.scheduledTasks = new ArrayList<>();
        System.out.println("[CONTROLLER] Central Controller initialized for: " + home.getHomeName());
    }

    // ===============================
    // LISTING METHODS
    // ===============================

    public void listAllDevices() {
        System.out.println("\n=== ALL DEVICES IN HOME ===");
        List<Room> rooms = home.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms in the home.");
            return;
        }

        int totalDevices = 0;
        for (Room room : rooms) {
            List<SmartDevice> devices = room.getDevices();
            if (!devices.isEmpty()) {
                System.out.println("\n[" + room.getRoomName() + "]");
                for (SmartDevice device : devices) {
                    System.out.println(" - " + device.getStatus());
                    totalDevices++;
                }
            }
        }

        if (totalDevices == 0) {
            System.out.println("No devices found in any room.");
        } else {
            System.out.println("\nTotal devices: " + totalDevices);
        }
    }

    public void listDevicesByRoom(String roomName) {
        System.out.println("\n=== DEVICES IN " + roomName.toUpperCase() + " ===");
        Room room = home.getRoomByName(roomName);

        if (room == null) {
            System.out.println("Room \"" + roomName + "\" not found.");
            return;
        }

        List<SmartDevice> devices = room.getDevices();
        if (devices.isEmpty()) {
            System.out.println("No devices in this room.");
        } else {
            for (SmartDevice device : devices) {
                System.out.println(" - " + device.getStatus());
            }
        }
    }

    public void listDevicesByType(String deviceType) {
        System.out.println("\n=== ALL " + deviceType.toUpperCase() + " DEVICES ===");
        List<SmartDevice> devices = home.findDevicesByType(deviceType);

        if (devices.isEmpty()) {
            System.out.println("No " + deviceType + " devices found.");
        } else {
            for (SmartDevice device : devices) {
                System.out.println(" - " + device.getStatus());
            }
            System.out.println("\nTotal " + deviceType + " devices: " + devices.size());
        }
    }

    public void getDeviceStatus(String deviceId) {
        System.out.println("\n=== DEVICE STATUS ===");
        try {
            SmartDevice device = home.findDeviceById(deviceId);
            System.out.println(device.getStatus());
            System.out.println("Details: " + device.getDetailedInfo());
        } catch (DeviceNotFoundException e) {
            System.out.println("Device with ID \"" + deviceId + "\" not found.");
        }
    }

    // ---
    // BATCH ACTIONS (BULK OPERATIONS)
    // ---

    public void turnAllDevicesOn() {
        System.out.println("\n=== TURNING ON ALL DEVICES ===");
        home.turnAllDevicesOn();
        System.out.println("[CONTROLLER] Operation completed.");
    }

    public void turnAllDevicesOff() {
        System.out.println("\n=== TURNING OFF ALL DEVICES ===");
        home.turnAllDevicesOff();
        System.out.println("[CONTROLLER] Operation completed.");
    }

    public void turnAllDevicesInRoomOn(String roomName) {
        System.out.println("\n=== TURNING ON ALL DEVICES IN " + roomName.toUpperCase() + " ===");
        Room room = home.getRoomByName(roomName);

        if (room == null) {
            System.out.println("Room \"" + roomName + "\" not found.");
            return;
        }

        room.turnAllDevicesOn();
        System.out.println("[CONTROLLER] Operation completed.");
    }

    public void turnAllDevicesInRoomOff(String roomName) {
        System.out.println("\n=== TURNING OFF ALL DEVICES IN " + roomName.toUpperCase() + " ===");
        Room room = home.getRoomByName(roomName);

        if (room == null) {
            System.out.println("Room \"" + roomName + "\" not found.");
            return;
        }

        room.turnAllDevicesOff();
        System.out.println("[CONTROLLER] Operation completed.");
    }

    public void turnAllDevicesOfTypeOn(String deviceType) {
        System.out.println("\n=== TURNING ON ALL " + deviceType.toUpperCase() + " DEVICES ===");
        home.turnAllDevicesByTypeOn(deviceType);
        System.out.println("[CONTROLLER] Operation completed.");
    }

    public void turnAllDevicesOfTypeOff(String deviceType) {
        System.out.println("\n=== TURNING OFF ALL " + deviceType.toUpperCase() + " DEVICES ===");
        home.turnAllDevicesByTypeOff(deviceType);
        System.out.println("[CONTROLLER] Operation completed.");
    }

    public void reduceEnergyUsage() {
        System.out.println("\n=== REDUCING ENERGY USAGE ===");
        int count = 0;
        double totalBefore = 0;
        double totalAfter = 0;

        List<SmartDevice> allDevices = home.getAllDevices();
        for (SmartDevice device : allDevices) {
            if (device instanceof EnergyConsumer) {
                EnergyConsumer consumer = (EnergyConsumer) device;
                double before = consumer.getPowerConsumption();
                totalBefore += before;

                // Reduce energy for lights by dimming
                if (device.getDeviceType().equals("LIGHT") && device instanceof smarthome.devices.Light) {
                    smarthome.devices.Light light = (smarthome.devices.Light) device;
                    if (light.getBrightness() > 50) {
                        light.setBrightness(50);
                        count++;
                    }
                }

                double after = consumer.getPowerConsumption();
                totalAfter += after;
            }
        }

        double saved = totalBefore - totalAfter;
        if (count == 0) {
            System.out.println("No optimization needed or possible.");
        } else {
            System.out.println("Optimized " + count + " device(s).");
            System.out.println("Energy saved: " + String.format("%.2f", saved) + " W");
        }
        System.out.println("[CONTROLLER] Energy reduction completed.");
    }

    public double getTotalEnergyConsumption() {
        return home.calculateTotalEnergyConsumption();
    }

    public void displayEnergyReport() {
        System.out.println("\n=== ENERGY CONSUMPTION REPORT ===");

        List<Room> rooms = home.getAllRooms();
        double grandTotal = 0;

        for (Room room : rooms) {
            double roomEnergy = room.calculateRoomEnergyConsumption();
            System.out.println("\n[" + room.getRoomName() + "]");

            List<SmartDevice> devices = room.getDevices();
            for (SmartDevice device : devices) {
                if (device instanceof EnergyConsumer) {
                    EnergyConsumer consumer = (EnergyConsumer) device;
                    double consumption = consumer.getPowerConsumption();
                    System.out.println(" - " + device.getDeviceName() + " (" + device.getDeviceType() + "): " +
                        String.format("%.2f", consumption) + " W");
                }
            }

            if (roomEnergy > 0) {
                System.out.println(" Room Total: " + String.format("%.2f", roomEnergy) + " W");
            }
            grandTotal += roomEnergy;
        }

        System.out.println("\n=== TOTAL ENERGY CONSUMPTION: " + String.format("%.2f", grandTotal) + " W ===");
    }

    // ===============================
    // SCHEDULED TASKS
    // ===============================
    public boolean scheduleTask(String taskName, String time, String action, String targetDevice) {
        try {
            ScheduledTask task = new ScheduledTask(taskName, time, action, targetDevice, this);
            scheduledTasks.add(task);
            System.out.println("[CONTROLLER] Task \"" + taskName + "\" scheduled successfully for " + time);
            return true;
        } catch (Exception e) {
            System.out.println("[CONTROLLER] Error scheduling task: " + e.getMessage());
            return false;
        }
    }

    public boolean scheduleTask(String taskName, String time, String action, String targetDevice, String parameter) {
        try {
            ScheduledTask task = new ScheduledTask(taskName, time, action, targetDevice, parameter, this);
            scheduledTasks.add(task);
            System.out.println("[CONTROLLER] Task \"" + taskName + "\" scheduled successfully for " + time);
            return true;
        } catch (Exception e) {
            System.out.println("[CONTROLLER] Error scheduling task: " + e.getMessage());
            return false;
        }
    }

    public void executeScheduledTasks(String currentTime) {
        System.out.println("\n=== EXECUTING SCHEDULED TASKS FOR " + currentTime + " ===");
        int executed = 0;

        for (ScheduledTask task : scheduledTasks) {
            if (task.getTime().equals(currentTime) && task.isEnabled()) {
                System.out.println("\n[TASK] Executing: " + task.getTaskName());
                task.execute();
                executed++;
            }
        }

        if (executed == 0) {
            System.out.println("No tasks scheduled for this time.");
        } else {
            System.out.println("\n[CONTROLLER] Executed " + executed + " task(s).");
        }
    }

    public void listScheduledTasks() {
        System.out.println("\n=== SCHEDULED TASKS ===");

        if (scheduledTasks.isEmpty()) {
            System.out.println("No scheduled tasks.");
            return;
        }

        for (int i = 0; i < scheduledTasks.size(); i++) {
            ScheduledTask task = scheduledTasks.get(i);
            System.out.println((i + 1) + ". " + task.toString());
        }
    }

    public boolean removeScheduledTask(String taskName) {
        for (int i = 0; i < scheduledTasks.size(); i++) {
            if (scheduledTasks.get(i).getTaskName().equalsIgnoreCase(taskName)) {
                scheduledTasks.remove(i);
                System.out.println("[CONTROLLER] Task \"" + taskName + "\" removed successfully.");
                return true;
            }
        }
        System.out.println("[CONTROLLER] Task \"" + taskName + "\" not found.");
        return false;
    }

    public void setTaskEnabled(String taskName, boolean enabled) {
        for (ScheduledTask task : scheduledTasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)) {
                task.setEnabled(enabled);
                System.out.println("[CONTROLLER] Task \"" + taskName + "\" " +
                    (enabled ? "enabled" : "disabled") + ".");
                return;
            }
        }
        System.out.println("[CONTROLLER] Task \"" + taskName + "\" not found.");
    }

    public void clearAllScheduledTasks() {
        scheduledTasks.clear();
        System.out.println("[CONTROLLER] All scheduled tasks cleared.");
    }

    // ===============================
    // UTILITY METHODS
    // ===============================

    public Home getHome() {
        return home;
    }

    public void displayStatistics() {
        System.out.println("\n=== SMART HOME STATISTICS ===");

        int totalRooms = home.getRoomCount();
        int totalDevices = home.getTotalDeviceCount();
        int devicesOn = home.countPoweredOnDevices();

        System.out.println("Total Rooms: " + totalRooms);
        System.out.println("Total Devices: " + totalDevices);
        System.out.println("Devices ON: " + devicesOn);
        System.out.println("Devices OFF: " + (totalDevices - devicesOn));
        System.out.println("Scheduled Tasks: " + scheduledTasks.size());
        System.out.println("Total Energy Consumption: " +
            String.format("%.2f", getTotalEnergyConsumption()) + " W");
    }
}
