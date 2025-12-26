/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.controller;

import smarthome.core.*;
import smarthome.home.*;
import smarthome.devices.*;
import smarthome.exceptions.*;  // ADD THIS LINE

public class CentralControllerDemo {
    public static void main(String[] args) {
        System.out.println("===============================");
        System.out.println("  SMART HOME CENTRAL CONTROLLER DEMO");
        System.out.println("===============================");

        // Step 1: Create home and rooms
        System.out.println("\n1. CREATING SMART HOME");
        System.out.println("===============================");
        Home myHome = new Home("My Smart Villa", "123 Automation Avenue");

        Room livingRoom = new Room("Living Room", "Entertainment", 25.0);
        Room bedroom = new Room("Bedroom", "Sleeping", 20.0);
        Room kitchen = new Room("Kitchen", "Cooking", 15.0);

        myHome.addRoom(livingRoom);
        myHome.addRoom(bedroom);
        myHome.addRoom(kitchen);

        // Step 2: Create devices
        System.out.println("\n2. CREATING DEVICES");
        System.out.println("===============================");

        Light livingLight = new Light("LIVING-LIGHT-1", "Living Room Light", 80, 15.0);
        Thermostat livingThermostat = new Thermostat("LIVING-THERMO-1", "Living Thermostat", 22.0);
        SmartTV livingTV = new SmartTV("LIVING-TV-1", "Living Room TV");

        Light kitchenLight = new Light("KITCHEN-LIGHT-1", "Kitchen Light", 100, 12.0);
        Light kitchenCabinet = new Light("KITCHEN-LIGHT-2", "Cabinet Light", 70, 8.0);

        Light bedroomLight = new Light("BEDROOM-LIGHT-1", "Bedside Lamp", 60, 10.0);
        Thermostat bedroomThermostat = new Thermostat("BEDROOM-THERMO-1", "Bedroom Thermostat", 20.0);

        // Step 3: Add devices to rooms - FIX IS HERE
        System.out.println("\n3. ADDING DEVICES TO ROOMS");
        System.out.println("===============================");

        try {
            myHome.addDeviceToRoomByName("Living Room", livingLight);
            myHome.addDeviceToRoomByName("Living Room", livingThermostat);
            myHome.addDeviceToRoomByName("Living Room", livingTV);

            myHome.addDeviceToRoomByName("Kitchen", kitchenLight);
            myHome.addDeviceToRoomByName("Kitchen", kitchenCabinet);

            myHome.addDeviceToRoomByName("Bedroom", bedroomLight);
            myHome.addDeviceToRoomByName("Bedroom", bedroomThermostat);
        } catch (Exception e) {  // CHANGE TO CATCH ALL EXCEPTIONS
            System.out.println("Error: " + e.getMessage());
        }

        // REST OF YOUR CODE STAYS EXACTLY THE SAME...
        // Step 4: Create Central Controller
        System.out.println("\n4. CREATING CENTRAL CONTROLLER");
        System.out.println("===============================");
        CentralController controller = new CentralController(myHome);

        // Step 5: Test listing features
        System.out.println("\n5. TESTING LISTING FEATURES");
        System.out.println("===============================");
        controller.listAllDevices();
        controller.listDevicesByRoom("Living Room");
        controller.listDevicesByType("LIGHT");
        controller.getDeviceStatus("LIVING-LIGHT-1");

        // Step 6: Test batch actions
        System.out.println("\n6. TESTING BATCH ACTIONS");
        System.out.println("===============================");
        controller.turnAllDevicesOn();

        System.out.println("\nWaiting a moment...\n");

        controller.turnAllDevicesInRoomOff("Bedroom");
        controller.turnAllDevicesOfTypeOff("LIGHT");

        // Step 7: Test energy management
        System.out.println("\n7. TESTING ENERGY MANAGEMENT");
        System.out.println("===============================");
        controller.displayEnergyReport();

        System.out.println("\nReducing energy usage:");
        controller.reduceEnergyUsage();

        controller.displayEnergyReport();

        // Step 8: Test scheduled tasks
        System.out.println("\n8. TESTING SCHEDULED TASKS");
        System.out.println("===============================");

        controller.scheduleTask("Morning Heat", "06:00", "ON", "LIVING-THERMO-1");
        controller.scheduleTask("Morning Lights", "06:00", "ON", "LIGHT");
        controller.scheduleTask("Set Evening Temp", "19:00", "SET_TEMPERATURE",
            "LIVING-THERMO-1", "22");
        controller.scheduleTask("Set Bedroom Temp", "21:00", "SET_TEMPERATURE",
            "BEDROOM-THERMO-1", "18");
        controller.scheduleTask("Dim Bedroom", "22:00", "SET_BRIGHTNESS", "BEDROOM-LIGHT-1", "30");
        controller.scheduleTask("Night Off", "23:00", "OFF", "Living Room");

        controller.listScheduledTasks();

        // Step 9: Simulate task execution
        System.out.println("\n9. SIMULATING TASK EXECUTION");
        System.out.println("===============================");

        controller.executeScheduledTasks("06:00");
        controller.executeScheduledTasks("19:00");
        controller.executeScheduledTasks("22:00");
        controller.executeScheduledTasks("23:00");

        // Step 10: Test task management
        System.out.println("\n10. TESTING TASK MANAGEMENT");
        System.out.println("===============================");

        controller.setTaskEnabled("Night Off", false);
        System.out.println();
        controller.listScheduledTasks();

        System.out.println();
        controller.removeScheduledTask("Morning Heat");
        System.out.println();
        controller.listScheduledTasks();

        // Step 11: Display final statistics
        System.out.println("\n11. FINAL STATISTICS");
        System.out.println("===============================");
        controller.displayStatistics();

        System.out.println("\n===============================");
        System.out.println("  ✅ DEMO COMPLETED SUCCESSFULLY");
        System.out.println("===============================");
    }
}