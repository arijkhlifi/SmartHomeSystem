/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.exceptions;

public class InvalidConfigurationException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String component;
    private final String parameter;
    private final String providedValue;
    private final String expectedValue;

    public InvalidConfigurationException(String component, String parameter, 
                                         String providedValue, String expectedValue) {
        super(String.format("Invalid configuration for '%s': parameter '%s' - " +
              "provided '%s', expected '%s'",
              component, parameter, providedValue, expectedValue));
        this.component = component;
        this.parameter = parameter;
        this.providedValue = providedValue;
        this.expectedValue = expectedValue;
    }

    public InvalidConfigurationException(String component, String parameter, String reason) {
        super(String.format("Invalid configuration for '%s': parameter '%s' - '%s'",
              component, parameter, reason));
        this.component = component;
        this.parameter = parameter;
        this.providedValue = null;
        this.expectedValue = null;
    }

    public InvalidConfigurationException(String message) {
        super(message);
        this.component = null;
        this.parameter = null;
        this.providedValue = null;
        this.expectedValue = null;
    }

    public String getComponent() { return component; }
    public String getParameter() { return parameter; }
    public String getProvidedValue() { return providedValue; }
    public String getExpectedValue() { return expectedValue; }
    
    @Override
    public String toString() {
        return "InvalidConfigurationException: " + getMessage();
    }
}
