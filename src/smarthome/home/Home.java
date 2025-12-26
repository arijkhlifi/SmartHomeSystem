/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.home;
import smarthome.exceptions.RoomCapacityException;
import smarthome.core.SmartDevice;
import smarthome.core.DeviceNotFoundException;
import java.util.*;
public class Home {
   private String homeName;
   private String homeAddress;
   private final Map<String, Room> rooms;
   private final Map<String, SmartDevice> allDevices;
   private final Map<String, String> deviceToRoomMap;
   public Home() {
       this("Smart Home", "123 Automation Street");
   }
   public Home(String homeName, String homeAddress) {
       if (homeName == null || homeName.trim().isEmpty()) {
           throw new IllegalArgumentException("Home name cannot be empty");
       }
       this.homeName = homeName;
       this.homeAddress = homeAddress;
       this.rooms = new LinkedHashMap<>();
       this.allDevices = new HashMap<>();
       this.deviceToRoomMap = new HashMap<>();
       System.out.println("[HOME] Created: " + homeName + " at " + homeAddress);
   }
   public void addRoom(Room room) {
       if (room == null) {
           throw new IllegalArgumentException("Room cannot be null");
       }
       String roomId = room.getRoomId();
       if (rooms.containsKey(roomId)) {
           throw new IllegalArgumentException("Room already exists");
       }
       rooms.put(roomId, room);
       // Sync devices from the room
       for (SmartDevice device : room.getDevices()) {
           String deviceId = device.getDeviceId();
           if (!allDevices.containsKey(deviceId)) {
               allDevices.put(deviceId, device);
               deviceToRoomMap.put(deviceId, roomId);
               System.out.println("[HOME] Synced device: " + device.getDeviceName() + " from room " + room.getRoomName());
           }
       }
       System.out.println("[HOME] Added room: " + room.getRoomName() + " with " + room.getDeviceCount() + " devices");
   }
   public Room removeRoom(String roomId) throws DeviceNotFoundException {
       Room room = rooms.get(roomId);
       if (room == null) {
           throw new DeviceNotFoundException(roomId, "(room not found)");
       }
       for (String deviceId : room.getAllDeviceIds()) {
           allDevices.remove(deviceId);
           deviceToRoomMap.remove(deviceId);
       }
       rooms.remove(roomId);
       System.out.println("[HOME] Removed room: " + room.getRoomName());
       return room;
   }
   public Room getRoom(String roomId) throws DeviceNotFoundException {
       Room room = rooms.get(roomId);
       if (room == null) {
           throw new DeviceNotFoundException(roomId, "(room not found)");
       }
       return room;
   }
   public Room getRoomByName(String roomName) {
       for (Room room : rooms.values()) {
           if (room.getRoomName().equalsIgnoreCase(roomName)) {
               return room;
           }
       }
       return null;
   }
   public Room findRoomContainingDevice(String deviceId) {
       String roomId = deviceToRoomMap.get(deviceId);
       return roomId != null ? rooms.get(roomId) : null;
   }
   public void addDeviceToRoom(String roomId, SmartDevice device) throws DeviceNotFoundException, RoomCapacityException {
       Room room = getRoom(roomId);
       String deviceId = device.getDeviceId();
       if (allDevices.containsKey(deviceId)) {
           throw new IllegalArgumentException("Device already exists in home");
       }
       room.addDevice(device);
       allDevices.put(deviceId, device);
       deviceToRoomMap.put(deviceId, roomId);
       System.out.println("[HOME] Added " + device.getDeviceType() + " to room " + room.getRoomName());
   }
   public void addDeviceToRoomByName(String roomName, SmartDevice device) throws DeviceNotFoundException, RoomCapacityException {
       Room room = getRoomByName(roomName);
       if (room == null) {
           throw new DeviceNotFoundException(roomName, "(room not found by name)");
       }
       addDeviceToRoom(room.getRoomId(), device);
   }
   public SmartDevice removeDeviceFromHome(String deviceId) throws DeviceNotFoundException {
       SmartDevice device = allDevices.get(deviceId);
       if (device == null) {
           throw new DeviceNotFoundException(deviceId);
       }
       String roomId = deviceToRoomMap.get(deviceId);
       if (roomId != null) {
           Room room = rooms.get(roomId);
           if (room != null) {
               room.removeDevice(deviceId);
           }
           deviceToRoomMap.remove(deviceId);
       }
       allDevices.remove(deviceId);
       System.out.println("[HOME] Removed device: " + device.getDeviceName());
       return device;
   }
   public void moveDeviceToRoom(String deviceId, String newRoomId) throws DeviceNotFoundException, RoomCapacityException {
       SmartDevice device = allDevices.get(deviceId);
       Room newRoom = getRoom(newRoomId);
       if (device == null) {
           throw new DeviceNotFoundException(deviceId);
       }
       String oldRoomId = deviceToRoomMap.get(deviceId);
       if (oldRoomId != null) {
           Room oldRoom = rooms.get(oldRoomId);
           if (oldRoom != null) {
               oldRoom.removeDevice(deviceId);
           }
       }
       newRoom.addDevice(device);
       deviceToRoomMap.put(deviceId, newRoomId);
       System.out.println("[HOME] Moved " + device.getDeviceName() + " to " + newRoom.getRoomName());
   }
   public SmartDevice findDeviceById(String deviceId) throws DeviceNotFoundException {
       SmartDevice device = allDevices.get(deviceId);
       if (device == null) {
           throw new DeviceNotFoundException(deviceId);
       }
       return device;
   }
   public List<SmartDevice> findDevicesByType(String deviceType) {
       List<SmartDevice> result = new ArrayList<>();
       for (SmartDevice device : allDevices.values()) {
           if (device.getDeviceType().equalsIgnoreCase(deviceType)) {
               result.add(device);
           }
       }
       return result;
   }
   public List<SmartDevice> findDevicesByName(String name) {
       List<SmartDevice> result = new ArrayList<>();
       for (SmartDevice device : allDevices.values()) {
           if (device.getDeviceName().toLowerCase().contains(name.toLowerCase())) {
               result.add(device);
           }
       }
       return result;
   }
   public void turnAllDevicesOn() {
       System.out.println("[HOME] Turning ON all devices");
       for (Room room : rooms.values()) {
           room.turnAllDevicesOn();
       }
   }
   public void turnAllDevicesOff() {
       System.out.println("[HOME] Turning OFF all devices");
       for (Room room : rooms.values()) {
           room.turnAllDevicesOff();
       }
   }
   public void turnAllDevicesByTypeOn(String deviceType) {
       System.out.println("[HOME] Turning ON all " + deviceType);
       List<SmartDevice> devices = findDevicesByType(deviceType);
       int count = 0;
       for (SmartDevice device : devices) {
           if (!device.isPoweredOn()) {
               device.turnOn();
               count++;
           }
       }
       System.out.println("[HOME] Turned ON " + count + " " + deviceType + "(s)");
   }
   public void turnAllDevicesByTypeOff(String deviceType) {
       System.out.println("[HOME] Turning OFF all " + deviceType);
       List<SmartDevice> devices = findDevicesByType(deviceType);
       int count = 0;
       for (SmartDevice device : devices) {
           if (device.isPoweredOn()) {
               device.turnOff();
               count++;
           }
       }
       System.out.println("[HOME] Turned OFF " + count + " " + deviceType + "(s)");
   }
   public void printHomeSummary() {
       System.out.println("\n" + "=".repeat(50));
       System.out.println("  🏠 HOME: " + homeName);
       System.out.println("  📍 Address: " + homeAddress);
       System.out.println("  📊 Statistics:");
       System.out.println("  • Total Rooms: " + rooms.size());
       System.out.println("  • Total Devices: " + allDevices.size());
       Map<String, Integer> deviceTypeCount = getDeviceCountByType();
       if (!deviceTypeCount.isEmpty()) {
           System.out.println("\n  Devices by Type:");
           for (Map.Entry<String, Integer> entry : deviceTypeCount.entrySet()) {
               System.out.println("  • " + entry.getKey() + ": " + entry.getValue());
           }
       }
       System.out.println("\n  Rooms:");
       for (Room room : rooms.values()) {
           double energy = room.calculateRoomEnergyConsumption();
           System.out.println("  • " + room.getRoomName() +
                            " (" + room.getRoomType() + ", " + room.getRoomSize() + "m²) - " +
                            room.getDeviceCount() + " devices, " + energy + " W");
       }
      
       double totalEnergy = calculateTotalEnergyConsumption();
       System.out.println("\n  Total Energy Consumption: " + totalEnergy + " W");
       System.out.println("=".repeat(50));
   }
   public Map<String, Integer> getDeviceCountByType() {
       Map<String, Integer> counts = new HashMap<>();
       for (SmartDevice device : allDevices.values()) {
           String type = device.getDeviceType();
           counts.put(type, counts.getOrDefault(type, 0) + 1);
       }
       return counts;
   }
   public double calculateTotalEnergyConsumption() {
       double total = 0.0;
       for (SmartDevice device : allDevices.values()) {
           if (device instanceof smarthome.core.EnergyConsumer) {
               smarthome.core.EnergyConsumer consumer = (smarthome.core.EnergyConsumer) device;
               total += consumer.getPowerConsumption();
           }
       }
       return Math.round(total * 100.0) / 100.0;
   }
   public void printDeviceLocations() {
       System.out.println("\n📍 Device Locations (" + allDevices.size() + " devices):");
       if (allDevices.isEmpty()) {
           System.out.println("  No devices in home.");
           return;
       }
       for (Map.Entry<String, String> entry : deviceToRoomMap.entrySet()) {
           String deviceId = entry.getKey();
           String roomId = entry.getValue();
           SmartDevice device = allDevices.get(deviceId);
           Room room = rooms.get(roomId);
           if (device != null && room != null) {
               String powerStatus = device.isPoweredOn() ? "🔴" : "⚪";
               System.out.println("  " + powerStatus + " " + device.getDeviceName() +
                                " (" + device.getDeviceType() + ") → " + room.getRoomName());
           }
       }
   }
   public String getHomeName() { return homeName; }
   public String getHomeAddress() { return homeAddress; }
   public int getRoomCount() { return rooms.size(); }
   public int getTotalDeviceCount() { return allDevices.size(); }
   public List<Room> getAllRooms() { return new ArrayList<>(rooms.values()); }
   public List<SmartDevice> getAllDevices() { return new ArrayList<>(allDevices.values()); }
   public int countPoweredOnDevices() {
       int count = 0;
       for (SmartDevice device : allDevices.values()) {
           if (device.isPoweredOn()) {
               count++;
           }
       }
       return count;
   }
}

