# Interactive Snake Game (2024)

**Course:** Java Programming  
**Collaboration:** Individual Project  
**Tech Stack:** `Java` , `Swing Framework` , `Event Handling` , `OOP Logic`  
**Domain:** GUI Development, Object-Oriented Software Design  

***

### Project Overview
A graphical implementation of the classic arcade game developed to demonstrate fundamental object-oriented programming principles and GUI design techniques. The application features a grid-based navigation system where a user-controlled entity consumes targets to dynamically grow in size, driven by real-time event listeners and state tracking.

***

### System Architecture & Implementation

* **Graphical User Interface:** Engineered the rendering engine using Java's Swing library to handle real-time game graphics and user keyboard interactions without frame delay.
* **Encapsulated Class Design:** Structured the core game mechanics using nested inner classes (Snake, Food, and Tile) within the main execution class. This minimized external access and safeguarded the integrity of the game logic.
* **Real-Time Execution Loop:** Implemented a continuous game loop utilizing Java Timers to manage state updates, including coordinate tracking, boundary collision detection, and dynamic entity growth.
* **State Management:** Authored logic to track current score metrics and trigger specific game-over conditions upon self-collision or out-of-bounds traversal.

***

### Engineering Challenges: Modularity & State Encapsulation
A key architectural challenge in this project was managing the shared state between the rendering engine, the player's input, and the grid coordinates. Initially, separating these into entirely different files risked exposing sensitive game variables globally. To solve this, I utilized heavily encapsulated nested inner classes. By nesting the entity logic (Snake and Food) directly within the main game panel class, I ensured that coordinate data was only accessible to the localized game loop. This approach significantly improved code maintainability and demonstrated strict adherence to strict OOP encapsulation principles.

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation in this repository has been abstracted. Specific grading rubrics, architectural UML diagrams, and full university lab reports have been omitted. This repository serves to demonstrate software design methodologies without providing a direct, copy-paste solution for active university coursework.

***

### ⚙️ How to View
* Access the `src/` directory to view the core Java source code, including the `App.java` and `SnakeGame.java` files.
* **Note:** Visual diagrams and gameplay media have been intentionally omitted to protect the structural integrity of the course's active curriculum.
