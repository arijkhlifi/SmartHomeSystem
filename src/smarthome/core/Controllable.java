/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author arijk
 */
package smarthome.core;

public interface Controllable {
    void turnOn();
    void turnOff();
    String getStatus();
}
