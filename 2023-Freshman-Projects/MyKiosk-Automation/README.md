# MyKiosk: System Automation Tool (2023)
**Course:** Creative Engineering Design  
**Collaboration:** Team Project (4 Members)  
**Role:** Lead Developer  
**Tech Stack:** Python (`PyAutoGUI`, `os`, `keyboard`, `time`)
---
### Project Overview
MyKiosk is a productivity-focused automation script developed to streamline daily workstation setups. It provides a GUI-driven interface for users to handle repetitive system tasks—such as checking news rankings, weather, or specific YouTube feeds—through a centralized "kiosk" system.
---
### Technical Contributions (Lead Developer)
As the Lead Developer, I was responsible for the core application logic and primary user-facing modules:

**Application Framework & UI Flow:** Independently designed and implemented the state-management system (`window_now`) that controls the logic flow between the Main, Execution (실행), and Settings interfaces.
* **System Initialization Logic:** Developed the `초기 설정` (Initial Setup) module, handling browser detection (Chrome/Edge/Whale) and user profiling on first launch.
* **Execution Dispatcher:** Built the logic for the "Execution" module, ensuring that user-selected macros correctly triggered the corresponding `os` and `PyAutoGUI` automation sequences.
* **Configuration Persistence:** Developed the `ManageFiles` class to handle `.txt` file I/O, saving user preferences (browser choice, favorite sites) between sessions.
---
### Key Technical Features
* **Automated Workflow Sequences:** Uses `PyAutoGUI` to simulate hotkeys (e.g., `alt+space`, `x` for window maximizing) and mouse clicks for navigating complex web elements.
* **Multi-Browser Compatibility:** Supports Chrome, Microsoft Edge, and Naver Whale through localized system path commands.
* **User-Customizable Macros:** Includes a feature where users can map their own custom URLs to kiosk buttons, stored via the `ManageFiles` class.
---
### 📝 Note on Implementation
This version utilizes coordinate-based clicking optimized for specific screen resolutions. Future iterations could implement image-recognition-based clicking (using `pg.locateOnScreen`) for enhanced cross-device compatibility.