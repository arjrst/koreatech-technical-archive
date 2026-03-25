# OS-Concurrency-Project (2025)

**Course:** Operating Systems  
**Collaboration:** Individual Project  
**Tech Stack:** `Java` (JDK 23), `Swing Framework`, `Multithreading`, `Semaphore Logic`  
**Domain:** System Synchronization, Concurrent Programming, GUI Visualization

***

### Project Overview
A technical implementation and real-time visualization of the classic **Producer-Consumer Problem** utilizing a circular multi-buffer architecture. This project was developed to demonstrate the practical application of synchronization primitives—such as semaphores and mutexes—to manage shared memory resources and prevent race conditions within a multithreaded environment.

***

### System Architecture & Implementation

* **Graphical User Interface:** Engineered a custom rendering engine using Java Swing to map abstract system states into a visual circular buffer. This provides real-time feedback on pointer positions and slot occupancy without interfering with the core logic.
* **Synchronization Primitives:** Implemented a robust thread-management system utilizing **Semaphores** (`nrfull`, `nrempty`) and **Mutexes** to ensure mutual exclusion and coordinate communication between concurrent producer and consumer entities.
* **Circular Buffer Management:** Designed a 4-slot array structure using dual-pointer tracking (`inPtr` and `outPtr`). The logic handles $O(1)$ data insertion and extraction while successfully managing the wrap-around logic of a circular queue.
* **Automated Scenario Simulation:** Authored an execution engine to process a predefined 24-step synchronization scenario. This stress-tests the logic across edge cases, including simultaneous requests and resource exhaustion.

***

### Engineering Challenges: Visualizing Thread Blocking
The primary architectural challenge was accurately visualizing "Blocked" states (Buffer Full/Empty) without halting the entire application's event dispatch thread. In a standard OS environment, a thread would simply sleep; however, for a portfolio-ready visualization, the state needs to be captured and rendered instantaneously. I solved this by implementing a **Non-Blocking Monitor** that intercepts semaphore decrement attempts. By mapping these "Wait" signals to dynamic UI updates—turning the pointer indicators red and incrementing state counters—I was able to prove the mathematical accuracy of the synchronization logic while maintaining a smooth, 60fps visual representation of the thread lifecycle.

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation in this repository has been abstracted. Specific grading rubrics, internal logic flowcharts, and full university lab reports have been omitted. This repository serves to demonstrate proficiency in concurrent systems design without providing a direct solution for active university coursework.

***

### ⚙️ How to View
* Access the `src/` directory to view the core Java source code, including the circular buffer and GUI implementation.
* Visit the `media/` folder to view high-resolution captures of the system in various states (Normal Flow, Producer Blocked, and Consumer Blocked).