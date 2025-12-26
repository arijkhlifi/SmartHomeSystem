/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.exceptions;

public class InvalidOperationException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String componentId;
    private final String operation;
    private final String reason;
    private final String componentType;

    public InvalidOperationException(String componentId, String componentType,
                                     String operation, String reason) {
        super(String.format("%s '%s' cannot perform '%s': %s",
              componentType, componentId, operation, reason));
        this.componentId = componentId;
        this.componentType = componentType;
        this.operation = operation;
        this.reason = reason;
    }

    public InvalidOperationException(String message) {
        super(message);
        this.componentId = null;
        this.componentType = null;
        this.operation = null;
        this.reason = message;
    }

    public InvalidOperationException(String deviceId, String operation, String reason) {
        this(deviceId, "Device", operation, reason);
    }

    public String getComponentId() { return componentId; }
    public String getComponentType() { return componentType; }
    public String getOperation() { return operation; }
    public String getReason() { return reason; }
    
    @Override
    public String toString() {
        return "InvalidOperationException: " + getMessage();
    }
}