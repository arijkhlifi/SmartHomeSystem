/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.home;
import smarthome.core.SmartDevice;
import smarthome.core.DeviceNotFoundException;
import smarthome.exceptions.RoomCapacityException;
import java.util.*;
public class Room {
   private final String roomId;
   private String roomName;
   private String roomType;
   private double roomSize;
   private final List<SmartDevice> devices;
   private final Map<String, SmartDevice> deviceMap;
   private static final int MAX_DEVICES_PER_ROOM = 15;
   private static final double MAX_ENERGY_PER_ROOM = 2000.0; // Watts


   // Calculate max devices based on room size
   private int getMaxDevicesForRoomSize() {
       // 1 device per 2 square meters (reasonable density)
       return (int)(roomSize / 2.0);
   }
   public Room(String roomName) {
       this(roomName, "General", 20.0);
   }
   public Room(String roomName, String roomType, double roomSize) {
       if (roomName == null || roomName.trim().isEmpty()) {
           throw new IllegalArgumentException("Room name cannot be empty");
       }
       if (roomSize <= 0) {
           throw new IllegalArgumentException("Room size must be positive");
       }
       this.roomId = "ROOM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
       this.roomName = roomName;
       this.roomType = roomType;
       this.roomSize = roomSize;
       this.devices = new ArrayList<>();
       this.deviceMap = new HashMap<>();
       System.out.println("[ROOM] Created: " + roomName + " (" + roomType + ", " + roomSize + "m²)");
   }
   public void addDevice(SmartDevice device) throws RoomCapacityException {
       if (device == null) {
           throw new IllegalArgumentException("Device cannot be null");
       }
       // Validate device type
       if (device.getDeviceType() == null || device.getDeviceType().trim().isEmpty()) {
           throw new IllegalArgumentException("Device must have a valid device type");
       }
       String deviceId = device.getDeviceId();
       if (deviceMap.containsKey(deviceId)) {
           throw new IllegalArgumentException("Device already exists in room");
       }
       // CHECK 1: Maximum devices limit (hard limit)
       if (devices.size() >= MAX_DEVICES_PER_ROOM) {
           throw new RoomCapacityException(
               roomId,
               roomName,
               "DEVICE_COUNT",
               devices.size() + 1,
               MAX_DEVICES_PER_ROOM
           );
       }
       // CHECK 2: Room size-based capacity (density check)
       int maxForSize = getMaxDevicesForRoomSize();
       if (devices.size() >= maxForSize) {
           throw new RoomCapacityException(
               roomId,
               roomName,
               "DENSITY",
               devices.size() + 1,
               maxForSize
           );
       }
       // CHECK 3: Energy capacity (if device consumes energy)
       if (device instanceof smarthome.core.EnergyConsumer) {
           double currentEnergy = calculateRoomEnergyConsumption();
           smarthome.core.EnergyConsumer consumer = (smarthome.core.EnergyConsumer) device;
           double deviceEnergy = consumer.getPowerConsumption();
           double newTotalEnergy = currentEnergy + deviceEnergy;
           if (newTotalEnergy > MAX_ENERGY_PER_ROOM) {
               throw new RoomCapacityException(
                   roomId,
                   roomName,
                   newTotalEnergy,
                   MAX_ENERGY_PER_ROOM
               );
           }
       }
       devices.add(device);
       deviceMap.put(deviceId, device);
       device.setLocation(this.roomName);
       System.out.println("[ROOM] Added " + device.getDeviceType() + " to " + roomName + ": " + device.getDeviceName());
   }
   public SmartDevice removeDevice(String deviceId) throws DeviceNotFoundException {
       SmartDevice device = deviceMap.get(deviceId);
       if (device == null) {
           throw new DeviceNotFoundException(deviceId, "in room " + roomName);
       }
       devices.remove(device);
       deviceMap.remove(deviceId);
       device.setLocation("Removed from " + roomName);
       System.out.println("[ROOM] Removed from " + roomName + ": " + device.getDeviceName());
       return device;
   }
   public SmartDevice getDevice(String deviceId) throws DeviceNotFoundException {
       SmartDevice device = deviceMap.get(deviceId);
       if (device == null) {
           throw new DeviceNotFoundException(deviceId, "in room " + roomName);
       }
       return device;
   }
   public List<SmartDevice> findDevicesByType(String deviceType) {
       List<SmartDevice> result = new ArrayList<>();
       for (SmartDevice device : devices) {
           if (device.getDeviceType().equalsIgnoreCase(deviceType)) {
               result.add(device);
           }
       }
       return result;
   }
   public boolean containsDevice(String deviceId) {
       return deviceMap.containsKey(deviceId);
   }
   public void turnAllDevicesOn() {
       System.out.println("[ROOM] Turning ON all devices in " + roomName);
       int count = 0;
       for (SmartDevice device : devices) {
           if (!device.isPoweredOn()) {
               device.turnOn();
               count++;
           }
       }
       System.out.println("[ROOM] Turned ON " + count + " device(s) in " + roomName);
   }
   public void turnAllDevicesOff() {
       System.out.println("[ROOM] Turning OFF all devices in " + roomName);
       int count = 0;
       for (SmartDevice device : devices) {
           if (device.isPoweredOn()) {
               device.turnOff();
               count++;
           }
       }
       System.out.println("[ROOM] Turned OFF " + count + " device(s) in " + roomName);
   }
   public void turnDevicesByTypeOn(String deviceType) {
       System.out.println("[ROOM] Turning ON all " + deviceType + " in " + roomName);
       int count = 0;
       for (SmartDevice device : devices) {
           if (device.getDeviceType().equalsIgnoreCase(deviceType) && !device.isPoweredOn()) {
               device.turnOn();
               count++;
           }
       }
       System.out.println("[ROOM] Turned ON " + count + " " + deviceType + "(s) in " + roomName);
   }
   public void turnDevicesByTypeOff(String deviceType) {
       System.out.println("[ROOM] Turning OFF all " + deviceType + " in " + roomName);
       int count = 0;
       for (SmartDevice device : devices) {
           if (device.getDeviceType().equalsIgnoreCase(deviceType) && device.isPoweredOn()) {
               device.turnOff();
               count++;
           }
       }
       System.out.println("[ROOM] Turned OFF " + count + " " + deviceType + "(s) in " + roomName);
   }
   public void printRoomSummary() {
       System.out.println("\n=== Room: " + roomName + " ===");
       System.out.println("Type: " + roomType);
       System.out.println("Size: " + roomSize + " m²");
       System.out.println("Devices: " + devices.size());
       System.out.println("Room ID: " + roomId);
       if (devices.isEmpty()) {
           System.out.println("No devices in this room.");
       } else {
           System.out.println("\nDevice List:");
           for (SmartDevice device : devices) {
               String powerStatus = device.isPoweredOn() ? "🔴 ON" : "⚪ OFF";
               System.out.println("  " + powerStatus + " | " + device.getDeviceType() +
                                " | " + device.getDeviceName());
           }
       }
       Map<String, Integer> typeCount = getDeviceCountByType();
       if (!typeCount.isEmpty()) {
           System.out.println("\nDevice Count by Type:");
           for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
               System.out.println("  " + entry.getKey() + ": " + entry.getValue());
           }
       }
       double energy = calculateRoomEnergyConsumption();
       System.out.println("Current Energy Consumption: " + energy + " W");
       System.out.println("=====================");
   }
   public Map<String, Integer> getDeviceCountByType() {
       Map<String, Integer> counts = new HashMap<>();
       for (SmartDevice device : devices) {
           String type = device.getDeviceType();
           counts.put(type, counts.getOrDefault(type, 0) + 1);
       }
       return counts;
   }
   public double calculateRoomEnergyConsumption() {
       double total = 0.0;
       for (SmartDevice device : devices) {
           if (device instanceof smarthome.core.EnergyConsumer) {
               smarthome.core.EnergyConsumer consumer = (smarthome.core.EnergyConsumer) device;
               total += consumer.getPowerConsumption();
           }
       }
       return Math.round(total * 100.0) / 100.0;
   }
   public List<String> getAllDeviceIds() {
       return new ArrayList<>(deviceMap.keySet());
   }
   public String getRoomId() { return roomId; }
   public String getRoomName() { return roomName; }
   public String getRoomType() { return roomType; }
   public double getRoomSize() { return roomSize; }
   public List<SmartDevice> getDevices() { return new ArrayList<>(devices); }
   public int getDeviceCount() { return devices.size(); }
   public void setRoomName(String roomName) {
       if (roomName != null && !roomName.trim().isEmpty()) {
           String oldName = this.roomName;
           this.roomName = roomName;
           for (SmartDevice device : devices) {
               device.setLocation(roomName);
           }
           System.out.println("[ROOM] Room renamed from \"" + oldName + "\" to \"" + roomName + "\"");
       } else {
           throw new IllegalArgumentException("Room name cannot be empty");
       }
   }
}
