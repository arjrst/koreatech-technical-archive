# 4 DOF Embedded Robot Arm (2024)

**Course:** Microprocessor and Lab
**Tech Stack:** `C (AVR)` , `ATmega128` , `PWM` , `ADC` , `UART`  
**Domain:** Embedded Systems, Hardware-Software Integration

***

### Project Overview
A comprehensive hardware-software integration project focused on designing, building, and programming a 4-degree-of-freedom (DOF) robotic manipulator. The system utilizes an AVR microcontroller to process manual analog inputs and translate them into precise, real-time multi-axis kinematics for payload manipulation.

***

### Key Features & Hardware
* **4 DOF Robot Arm Design:** Enables various complex movements through 4 degrees of freedom.
* **Pincer-Type Hand:** Capable of securely grabbing and transporting physical objects.
* **Wood Stick Structure:** A simple, cost-effective, and custom-built physical chassis.
* **Hardware Components:** 4x Servo Motors, 1x Analog Joystick, 4x Tactile Switch Buttons, 1x Active Buzzer, 4x Resistors, and wiring for circuit connection and power distribution.

***

### System Architecture & Implementation

* **Hardware Controller:** Engineered the core logic using an 8-bit AVR microcontroller, managing multiple peripherals simultaneously without an underlying operating system.
* **Actuation System:** Developed a custom PWM signal generator to control four independent servo motors. Instead of relying on high-level libraries, the pulse widths were managed directly via hardware timers to ensure non-blocking, high-resolution movement.
* **Input Processing:** Implemented an ADC (Analog-to-Digital Converter) pipeline to poll joystick coordinates, applying software-level smoothing to translate raw analog voltages into proportional angular displacement.
* **Event-Driven Logic:** Utilized hardware interrupts to manage state changes, system resets, and auditory feedback via an active buzzer, ensuring that critical physical inputs were prioritized over the main execution loop.

***

### Engineering Challenges: Current & Voltage Distribution
During extended load testing, the system experienced severe servo jitter and torque loss. I diagnosed this as a current and voltage distribution issue. The simultaneous current draw of four servo motors caused unstable voltage drops across the circuit, which the standard battery pack could not reliably support. To ensure system reliability during the final payload-transport evaluation, I bypassed the unstable battery source and re-routed the power distribution to a regulated DC bench supply. This provided a stable current flow and proper voltage regulation, instantly eliminating the hardware jitter and reinforcing the critical relationship between firmware execution and physical power constraints.

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation and source code discussed in this repository have been abstracted. Specific hardcoded register values, proprietary lab rubrics, and exact circuit schematics have been generalized or omitted. This repository serves to demonstrate the architectural logic and firmware structure without providing a direct, copy-paste solution for active university coursework.

***

### ⚙️ How to View
* Access the `Project_Abstracted.ino` file to view the core logic structure and interrupt-driven architecture.
* View the `media/` folder for visual demonstrations of the custom-built physical chassis and motor actuation.
