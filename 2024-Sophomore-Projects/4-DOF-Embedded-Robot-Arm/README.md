# 4 DOF Embedded Robot Arm (2024)

**Course:** Microprocessor and Lab (Sophomore Year, Semester 2)
**Collaboration:** Individual Lab Project
**Role:** Embedded Systems Developer
**Tech Stack:** C (AVR) , ATmega128 , PWM , ADC , Interrupts

***

### Description
A hardware-software integration project focused on designing and manufacturing a 4-degree-of-freedom (DOF) robot arm. The system utilizes an AVR microcontroller to interpret manual inputs from a joystick and buttons, translating them into precise pincer movements for object manipulation.

***

### Key Technical Implementations

* **Low-Level PWM Generation:** Engineered a 50Hz PWM signal control system using Timer 4 (for Servos 1-3) and Timer 3 (for Servo 4) with 16-bit ICR registers set to 40,000 for high-resolution pulse width management.
* **Interrupt-Driven Architecture:** Implemented External Interrupt 6 (INT6) for prioritized button handling and Timer 1 (CTC Mode) for consistent 1Hz system status logging via UART.
* **Real-Time Data Acquisition:** Developed an ADC (Analog-to-Digital Converter) layer to process X-axis joystick data (PF0), enabling proportional and intuitive control of the robot's angular displacement.
* **Feedback System Logic:** Authored a frequency-calculating module for an Active Buzzer to provide auditory system alerts and play pre-programmed melodies (e.g., Twinkle Twinkle Little Star) using half-period delay loops.
* **Firmware Optimization:** Structured the source code to handle state-based servo selection, manual coordinate mapping, and an automated "Home" positioning function for system resets.

***

### ⚙️ How to View
* Open Robot Arm Report.pdf for the full schematic diagrams and detailed code review.
* The Project.ino file contains the complete AVR source code, including register-level timer configurations.
* Refer to the Media folder for images of the physical wood-stick chassis construction.

***

### 📝 Challenges & Troubleshooting: Power Management & Hardware Reliability
Managing the power draw for four simultaneous servo motors proved to be the most critical challenge in this build. Initially, I powered the system using a 4-cell battery pack. However, during extended testing, I encountered severe voltage fluctuations. When the voltage spiked or the current draw exceeded the battery's stable output, the servos experienced intense jittering, causing the gripper to lose its holding torque and drop the payload.

During the final practical evaluation—which required manually piloting the arm via joystick to transport a ball across specific waypoints (Points C to B to A)—battery degradation caused a mid-test hardware stall. To save the evaluation, I quickly bypassed the depleted battery pack and re-wired the system directly to a laboratory DC bench power supply. This stabilized the voltage and current, instantly eliminating the servo jitter and allowing me to successfully complete the required tasks. This experience taught me the critical importance of power regulation and hardware redundancy in embedded systems.