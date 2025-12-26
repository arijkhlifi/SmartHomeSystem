/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.exceptions;

public class RoomCapacityException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String roomId;
    private final String roomName;
    private final String capacityType;
    private final int currentValue;
    private final int maxValue;

    public RoomCapacityException(String roomId, String roomName, String capacityType,
                                 int currentValue, int maxValue) {
        super(String.format("Room '%s' capacity exceeded [%s]: Current=%d, Maximum=%d", 
              roomName, capacityType, currentValue, maxValue));
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacityType = capacityType;
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public RoomCapacityException(String roomId, String roomName, 
                                 double currentEnergy, double maxEnergy) {
        super(String.format("Room '%s' energy capacity exceeded: Current=%.2fW, Maximum=%.2fW", 
              roomName, currentEnergy, maxEnergy));
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacityType = "ENERGY";
        this.currentValue = (int)currentEnergy;
        this.maxValue = (int)maxEnergy;
    }

    public RoomCapacityException(String message) {
        super(message);
        this.roomId = null;
        this.roomName = null;
        this.capacityType = "GENERAL";
        this.currentValue = 0;
        this.maxValue = 0;
    }

    public String getRoomId() { return roomId; }
    public String getRoomName() { return roomName; }
    public String getCapacityType() { return capacityType; }
    public int getCurrentValue() { return currentValue; }
    public int getMaxValue() { return maxValue; }
    
    public int getOverage() {
        return currentValue - maxValue;
    }
    
    public double getOveragePercentage() {
        return ((double)(currentValue - maxValue) / maxValue) * 100;
    }
    
    @Override
    public String toString() {
        return "RoomCapacityException: " + getMessage();
    }
}
