/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package smarthome.core;

public interface Schedulable {
    void schedule(String time, String action);
    void cancelSchedule(String scheduleId);
}
