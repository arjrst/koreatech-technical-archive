# Distance Mate: Portable Inter-Vehicle Alert System (2024)

**Course:** Computer Basic System Design  
**Collaboration:** Team Project (4 Members)  
**Role:** Technical Lead / Requirements Engineering  
**Methodologies:** `Tradeoff Matrix` , `Requirements Engineering` , `AHP (Team)` , `SWOT Analysis (Team)`  
**Domain:** Systems Engineering, Product Architecture

***

### Project Overview
A design-centered systems engineering team project focused on architecting a portable collision-prevention device for micro-mobility vehicles. This study bridged the gap between hardware sensor limitations and real-time user safety requirements through rigorous technical specification, mathematical decision-making, and structural requirement engineering.

***

### System Architecture & Implementation

* **Requirements Engineering:** Authored comprehensive engineering specifications, translating high-level marketing goals into verifiable technical constraints, including a 20-meter detection range, a sub-1-second response time, and 90% object detection accuracy.
* **Tradeoff Analysis (Personal Contribution):** Developed the Engineering-Marketing Tradeoff Matrix to evaluate the physical constraints of the hardware. This ensured the final system design effectively balanced physical portability (smartphone-sized) with high-performance hazard identification.
* **Quantitative Decision Logic (Team):** The team implemented the Analytic Hierarchy Process (AHP) and SWOT analyses to mathematically evaluate and select optimal sensor suites based on heavily weighted criteria.
* **Safety-Critical Architecture:** Designed a multidirectional alerting system utilizing auditory output and vibration, optimized for high-noise urban environments and weatherproofing (IP67/IP68 ratings).

***

### Engineering Challenges: Sensor Selection & Tradeoff Analysis
A primary challenge in this architectural design was balancing the cost and physical footprint of the sensor array with the strict requirement for multidirectional detection and weather independence. While the team's initial research into LiDAR offered high resolution, it proved unsuitable for a portable scooter device. To resolve this, I utilized a Tradeoff Matrix to justify the selection of a specialized Radar module. This provided the necessary environmental robustness and met the sub-1-second latency requirement while remaining within the strict power and budget constraints of a portable, bracket-mounted system.

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation in this repository has been abstracted. Specific grading rubrics and full university lab reports have been omitted. Furthermore, visual diagrams are strictly limited to my personal contributions to the team project to ensure accurate attribution of work. 

***

### ⚙️ How to View
* View the `media/` folder for the Engineering-Marketing Tradeoff Matrix, which illustrates the hardware-software balancing act performed during the design phase.
