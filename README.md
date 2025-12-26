# HomeNest – Smart Home Automation System

HomeNest is an object-oriented Java application designed to simulate an intelligent smart home environment.  
The system allows users to design rooms, add and control smart devices, monitor energy consumption, and automate behaviors using scheduling and rules.

This project was developed as an academic Object-Oriented Programming (OOP) project.

## Project Overview
The system models a real-world smart home using clean architecture and OOP principles.  
It separates responsibilities across devices, rooms, controllers, and automation logic to ensure scalability and extensibility.

Key capabilities include:
- Real-time device control
- Energy monitoring and optimization
- Room-based device management
- Rule-based automation and scheduling

## System Architecture
The architecture is organized into clear layers:

- **Core Layer**  
  Defines abstract behaviors and shared capabilities using:
  - `SmartDevice` (abstract class)
  - `Controllable` interface
  - `EnergyConsumer` interface

- **Devices Layer**  
  Concrete implementations of smart devices such as:
  - Light
  - Thermostat
  - MotionSensor
  - SmartPlug
  - DoorLock

- **Home Structure**
  - `Home` class: central registry for rooms and devices
  - `Room` class: manages devices, capacity limits, and energy constraints

- **Controller Layer**
  - Central controller responsible for coordinating devices, rooms, automation, and energy management

- **Automation System**
  - Scheduled tasks and rule-based automation (time-based, activity-based, and manual triggers)

- **Exception Handling**
  - Graceful error handling for invalid configurations, room capacity limits, and invalid operations

## Features
- Add and remove smart devices
- Assign devices to rooms
- Turn devices ON/OFF individually or in bulk
- Monitor real-time energy consumption (with precision)
- Prevent energy and capacity overloads
- Schedule automated actions (e.g. temperature changes, lighting control)
- Console-based interactive menu

## Object-Oriented Concepts Used
- **Abstraction**: shared device behaviors via abstract classes and interfaces
- **Encapsulation**: protected internal device states with validation
- **Composition**: Home → Room → Device hierarchy
- **Polymorphism**: unified control logic for different device types

## Technologies Used
- Java
- NetBeans IDE
- Object-Oriented Programming (OOP)

## How to Run
1. Open the project in NetBeans
2. Run the main demo class
3. Interact with the system using the console-based menu

## Authors
- Arij Khlifi
- Rouaida Hentati
- Takwa Dalensi
- Lina Smiri
- Siwar Jerbi


