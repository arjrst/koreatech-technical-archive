# Distance Mate: Portable Inter-Vehicle Alert System (2024)

**Course:** Computer Basic System Design (Sophomore Year)
**Collaboration:** Team Project (4 Members)
**Role:** Technical Lead / Requirements Engineering
**Methodologies:** AHP (Analytic Hierarchy Process) , Tradeoff Matrix , Requirements Engineering , SWOT Analysis
**Domain:** Systems Engineering, Product Architecture

***

### Project Overview
A design-centered systems engineering project focused on architecting a portable collision-prevention device for micro-mobility vehicles, with a primary focus on scooters and bicycles[cite: 34]. This study bridged the gap between hardware sensor limitations and real-time user safety requirements through rigorous technical specification, mathematical decision-making, and structural requirement engineering.

***

### System Architecture & Implementation

* [cite_start]**Requirements Engineering:** Authored comprehensive engineering specifications, translating high-level marketing goals into verifiable technical constraints, including a 20-meter detection range, a sub-1-second response time [cite: 83][cite_start], and 90% object detection accuracy[cite: 85].
* **Quantitative Decision Logic:** Implemented the Analytic Hierarchy Process (AHP) to mathematically evaluate and select optimal sensor suites based on heavily weighted criteria, specifically prioritizing Accuracy (49.7%) and Stability (26.2%) over Cost[cite: 118].
* [cite_start]**Safety-Critical Architecture:** Designed a multidirectional alerting system utilizing auditory output and vibration[cite: 83]. [cite_start]The system was optimized for high-noise urban environments and weatherproofing (IP67/IP68 ratings) to ensure robustness in various physical incidents[cite: 83].
* [cite_start]**Methodological Frameworks:** Utilized Concept Fans [cite: 105][cite_start], SWOT Analysis [cite: 122][cite_start], and Engineering-Marketing Tradeoff Matrices [cite: 96] to ensure the final system design effectively balanced physical portability (smartphone-sized) [cite: 104] with high-performance hazard identification.

***

### Engineering Challenges: Sensor Selection & Tradeoff Analysis
A primary challenge in this architectural design was balancing the cost and physical footprint of the sensor array with the strict requirement for multidirectional detection and weather independence. While LiDAR offered high resolution, our SWOT and AHP analyses revealed that its high cost and susceptibility to weather conditions made it unsuitable for a portable scooter device[cite: 122]. By utilizing a Tradeoff Matrix, I justified the selection of a Radar module[cite: 96, 122]. This provided the necessary environmental robustness and met the sub-1-second latency requirement while remaining within the strict power and budget constraints of a portable, bracket-mounted system[cite: 122].

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation in this repository has been abstracted. Specific grading rubrics, raw AHP calculation sheets, and full university lab reports have been omitted. This repository serves to demonstrate the systems engineering methodologies and architectural logic without providing a direct, copy-paste solution for active university coursework.

***

### ⚙️ How to View
* View the media folder for visual breakdowns of the Concept Fan, SWOT analysis, and AHP decision matrices.
* The abstract architectural diagrams and Tradeoff Matrix summaries are provided as standalone image files to illustrate the hardware-software balancing act performed during the design phase.