# MyKiosk: System Automation Tool (2023)

**Course:** Creative Engineering Design (Freshman Year)  
**Collaboration:** Team Project (4 Members)  
**Role:** Lead Developer  
**Tech Stack:** Python (`PyAutoGUI`, `os`, `keyboard`, `time`)

### Project Overview
MyKiosk is a productivity-focused automation script developed to streamline daily workstation setups. It provides a GUI-driven interface for users to handle repetitive system tasks—such as checking news rankings, weather, or specific YouTube feeds—through a centralized "kiosk" system.

### Technical Contributions (Lead Developer)
As the Lead Developer, I was responsible for the core system architecture and the primary application engine:

**Application Framework & UI Flow:** Independently designed and implemented the state-management system (`window_now`) that controls the logic flow between the Main, Execution (실행), and Settings interfaces.
* **System Initialization Logic:** Developed the `초기 설정` (Initial Setup) module, handling the technical detection of system browsers (Chrome/Edge/Whale) and persistent user profiling.
* **Execution Dispatcher:** Built the logic for the "Execution" module, ensuring that user-selected macros correctly triggered the corresponding `os` and `PyAutoGUI` automation sequences.
* **Data Persistence Layer:** Developed the `ManageFiles` class to handle automated `.txt` file I/O, ensuring user configurations (browser choice, favorite sites) persist across sessions.


### Key Technical Features
* **Automated Workflow Sequences:** Uses `PyAutoGUI` to simulate hotkeys (e.g., `alt+space`, `x` for window maximizing) and mouse clicks for navigating complex web elements.
* **Multi-Browser Compatibility:** Supports Chrome, Microsoft Edge, and Naver Whale through localized system path commands.
* **User-Customizable Macros:** Includes a feature where users can map their own custom URLs to kiosk buttons, which are then saved securely via the `ManageFiles` system.

### 📝 Note on Implementation
This version utilizes coordinate-based clicking optimized for specific screen resolutions. Future iterations could implement image-recognition-based clicking (using `pg.locateOnScreen`) for enhanced cross-device compatibility.
