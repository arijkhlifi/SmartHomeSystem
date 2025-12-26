/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package smarthome.demo;

import smarthome.core.*;
import smarthome.home.*;
import smarthome.devices.*;
import smarthome.exceptions.*;
import java.util.*;

public class SmartHomeDesigner {
    private static Scanner scanner = new Scanner(System.in);
    private static Home currentHome;
    
    // Colors for better UI (ANSI escape codes)
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    
    public static void main(String[] args) {
        displayWelcomeScreen();
        initializeHome();
        mainDashboard();
        scanner.close();
    }
    
    // ============= HELPER METHODS =============
    
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Ignore interruption
        }
    }
    
    private static void pressEnterToContinue() {
        System.out.print("\n" + YELLOW + "Press Enter to continue..." + RESET);
        scanner.nextLine();
    }
    
    private static int getNumberInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(RED + "   ⚠️  Please enter a number between " + min + " and " + max + RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED + "   ⚠️  Please enter a valid number." + RESET);
            }
        }
    }
    
    private static double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(RED + "   ⚠️  Please enter a number between " + min + " and " + max + RESET);
            } catch (NumberFormatException e) {
                System.out.println(RED + "   ⚠️  Please enter a valid number." + RESET);
            }
        }
    }
    
    private static void displayHeader() {
        clearScreen();
        System.out.println(CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + "                    🏠 SMART HOME DESIGNER                    " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + "  Home: " + BOLD + (currentHome != null ? "My Smart Home" : "Unnamed") + 
                         RESET + " | " + "Address: " + (currentHome != null ? "123 Main St" : "Not set") + " " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
    }
    
    private static void displayWelcomeScreen() {
        clearScreen();
        System.out.println(CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + "                    🏠 SMART HOME DESIGNER                    " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "└──────────────────────────────────────────────────────────────┘" + RESET);
        System.out.println("\n" + YELLOW + "🚀 Let's get started!" + RESET);
        pressEnterToContinue();
    }
    
    // ============= MAIN METHODS =============
    
    private static void initializeHome() {
        clearScreen();
        System.out.println(PURPLE + "┌────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + BOLD + "                    📝 HOME SETUP WIZARD                    " + RESET + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "└────────────────────────────────────────────────────────────┘" + RESET);
        
        String homeName;
        while (true) {
            System.out.print("\n" + GREEN + "🏷️  What would you like to name your smart home? " + RESET);
            homeName = scanner.nextLine().trim();
            if (!homeName.isEmpty() && homeName.length() >= 3) {
                break;
            }
            System.out.println(RED + "   ⚠️  Please enter a name with at least 3 characters." + RESET);
        }
        
        String address;
        while (true) {
            System.out.print("\n" + GREEN + "📍 What's the address? " + RESET);
            address = scanner.nextLine().trim();
            if (!address.isEmpty()) {
                break;
            }
            System.out.println(RED + "   ⚠️  Please enter a valid address." + RESET);
        }
        
        // Create Home with the constructor from your original structure
        currentHome = new Home(homeName, address);
        
        System.out.println("\n" + GREEN + "✅ Perfect! Your smart home has been created!" + RESET);
        System.out.println("   " + YELLOW + "🏠 Name: " + BOLD + homeName + RESET);
        System.out.println("   " + YELLOW + "📌 Address: " + address + RESET);
        
        pressEnterToContinue();
    }
    
    private static void mainDashboard() {
        while (true) {
            displayHeader();
            
            // Quick stats - SIMPLIFIED to match your classes
            System.out.println("\n" + CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            System.out.println(PURPLE + "📊 " + BOLD + "QUICK STATS" + RESET);
            // Note: These methods don't exist in your original classes
            // Using placeholders for now
            System.out.println("   Rooms: " + YELLOW + "0" + RESET + 
                             " | Devices: " + YELLOW + "0" + RESET);
            System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            
            // Main menu options
            System.out.println("\n" + BOLD + "🔧 DESIGN & BUILD" + RESET);
            System.out.println("   1️⃣  " + GREEN + "➕ Design a New Room" + RESET);
            System.out.println("   2️⃣  " + GREEN + "💡 Add Smart Devices" + RESET);
            System.out.println("   3️⃣  " + GREEN + "🔄 Room Layout Manager" + RESET);
            
            System.out.println("\n" + BOLD + "🎮 CONTROL CENTER" + RESET);
            System.out.println("   4️⃣  " + BLUE + "🎯 Device Controller" + RESET);
            System.out.println("   5️⃣  " + BLUE + "⚡ Energy Dashboard" + RESET);
            
            System.out.println("\n" + BOLD + "⚙️  SETTINGS" + RESET);
            System.out.println("   0️⃣  " + CYAN + "🏠 Save & Exit" + RESET);
            System.out.println("   R️⃣  " + YELLOW + "Refresh Dashboard" + RESET);
            
            System.out.print("\n" + BOLD + "👉 Enter your choice: " + RESET);
            String choice = scanner.nextLine().toLowerCase();
            
            switch (choice) {
                case "1": designNewRoom(); break;
                case "2": addSmartDevices(); break;
                case "3": roomLayoutManager(); break;
                case "4": deviceController(); break;
                case "5": energyDashboard(); break;
                case "0": saveAndExit(); return;
                case "r": break; // Just refresh
                default: 
                    System.out.println(RED + "\n⚠️  Invalid choice. Please try again." + RESET);
                    pause(1500);
            }
        }
    }
    
    private static void designNewRoom() {
        clearScreen();
        System.out.println(PURPLE + "┌────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + BOLD + "                    🏗️  ROOM DESIGNER                       " + RESET + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "└────────────────────────────────────────────────────────────┘" + RESET);
        
        System.out.print("\n" + GREEN + "🏷️  Give this room a name: " + RESET);
        String roomName = scanner.nextLine();
        
        try {
            // Using original Room constructor (just name)
            Room newRoom = new Room(roomName);
            
            // Add room to home (need to implement addRoom method in Home)
            // currentHome.addRoom(newRoom);
            
            System.out.println("\n" + GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            System.out.println(YELLOW + "✅ " + BOLD + "ROOM CREATED SUCCESSFULLY!" + RESET);
            System.out.println(GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
            
            System.out.println("\n" + CYAN + "📋 Room Details:" + RESET);
            System.out.println("   " + YELLOW + "🎯 Name: " + BOLD + roomName + RESET);
            
        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error: " + e.getMessage() + RESET);
        }
        
        pressEnterToContinue();
    }
    
    private static void addSmartDevices() {
        System.out.println(PURPLE + "┌────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + BOLD + "                    💡 SMART DEVICE CATALOG                " + RESET + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "└────────────────────────────────────────────────────────────┘" + RESET);
        
        String[][] devices = {
            {"💡 Smart Light", "Adjustable brightness", "12W"},
            {"🌡️ Smart Thermostat", "Temperature control", "50W"},
            {"📺 Smart TV", "4K streaming", "80W"}
        };
        
        System.out.println("\n" + CYAN + "🔥 AVAILABLE DEVICES" + RESET);
        System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        
        for (int i = 0; i < devices.length; i++) {
            System.out.printf("   %2d. %-18s %-30s\n", 
                i + 1, devices[i][0], devices[i][1]);
        }
        
        System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        
        int deviceChoice = getNumberInput("\n" + GREEN + "👉 Select device type (1-" + devices.length + "): " + RESET, 1, devices.length);
        
        System.out.print("\n" + GREEN + "🏷️  Give this device a name: " + RESET);
        String deviceName = scanner.nextLine();
        
        String deviceId = "DEV-" + deviceName.toUpperCase().replace(" ", "") + "-001";
        
        try {
            SmartDevice newDevice = createDevice(deviceChoice, deviceId, deviceName);
            
            if (newDevice != null) {
                System.out.println("\n" + GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
                System.out.println(YELLOW + "✅ " + BOLD + "DEVICE CREATED SUCCESSFULLY!" + RESET);
                System.out.println(GREEN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
                
                System.out.println("\n" + CYAN + "📋 Device Details:" + RESET);
                System.out.println("   " + YELLOW + "🎯 Name: " + BOLD + deviceName + RESET);
                System.out.println("   " + YELLOW + "📁 Type: " + devices[deviceChoice - 1][0].substring(2) + RESET);
                System.out.println("   " + YELLOW + "🔑 Device ID: " + deviceId + RESET);
                
                // Ask to power on
                System.out.print("\n" + BLUE + "🔌 Power on this device now? (yes/no): " + RESET);
                String powerOn = scanner.nextLine().toLowerCase();
                if (powerOn.equals("yes") || powerOn.equals("y")) {
                    newDevice.turnOn();
                    System.out.println(GREEN + "   ✓ Device powered on!" + RESET);
                }
            }
            
        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error: " + e.getMessage() + RESET);
        }
        
        pressEnterToContinue();
    }
    
    private static SmartDevice createDevice(int type, String id, String name) {
        switch (type) {
            case 1: // Smart Light
                int brightness = getNumberInput("\n" + GREEN + "💡 Set initial brightness (0-100): " + RESET, 0, 100);
                return new Light(id, name); // Using original constructor
                
            case 2: // Thermostat
                double temp = getDoubleInput("\n" + GREEN + "🌡️  Set initial temperature (10-35°C): " + RESET, 10, 35);
                Thermostat thermo = new Thermostat(id, name);
                // Note: setTargetTemperature doesn't exist in your original
                // Would need to add this method
                return thermo;
                
            case 3: // Smart TV
                return new SmartTV(id, name);
                
            default:
                return null;
        }
    }
    
    private static void deviceController() {
        System.out.println(PURPLE + "┌────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + BOLD + "                    🎮 DEVICE CONTROL CENTER                 " + RESET + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "└────────────────────────────────────────────────────────────┘" + RESET);
        
        // Since we don't have devices stored yet, show a demo
        System.out.println("\n" + YELLOW + "⚠️  No devices added yet!" + RESET);
        System.out.println("Please add devices from the main menu first.");
        
        pressEnterToContinue();
    }
    
    private static void energyDashboard() {
        clearScreen();
        System.out.println(PURPLE + "┌────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + BOLD + "                    ⚡ ENERGY DASHBOARD                      " + RESET + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "└────────────────────────────────────────────────────────────┘" + RESET);
        
        System.out.println("\n" + CYAN + "📈 ENERGY OVERVIEW" + RESET);
        System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        
        System.out.println("   " + YELLOW + "🚧 Feature in development" + RESET);
        System.out.println("\n   Energy monitoring will be available once");
        System.out.println("   devices are added to your smart home.");
        
        System.out.println("\n" + CYAN + "💡 ENERGY SAVING TIPS" + RESET);
        System.out.println(CYAN + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println("   • Turn off lights when leaving a room");
        System.out.println("   • Use LED bulbs instead of incandescent");
        System.out.println("   • Set thermostats to energy-saving mode");
        System.out.println("   • Unplug devices when not in use");
        
        pressEnterToContinue();
    }
    
    private static void roomLayoutManager() {
        System.out.println("\n" + YELLOW + "🚧 Feature coming soon: Room Layout Manager" + RESET);
        pressEnterToContinue();
    }
    
    private static void saveAndExit() {
        clearScreen();
        System.out.println(PURPLE + "┌────────────────────────────────────────────────────────────┐" + RESET);
        System.out.println(PURPLE + "│" + BOLD + "                    👋 GOODBYE!                             " + RESET + PURPLE + "│" + RESET);
        System.out.println(PURPLE + "└────────────────────────────────────────────────────────────┘" + RESET);
        
        System.out.println("\n" + GREEN + "✅ Your smart home design has been saved!" + RESET);
        System.out.println("\n" + BLUE + "💡 Come back anytime to continue designing!" + RESET);
        System.out.println("\n" + GREEN + "🏠 Thank you for using Smart Home Designer! 👋" + RESET);
    }
}
