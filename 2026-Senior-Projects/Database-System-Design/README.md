# Kyo-Board: Integrated Book Club System (2026 – Ongoing)

**Course:** Database Design
**Collaboration:** Team Project  
**Tech Stack:** `SQL`, `Relational Modeling`, `Conceptual/Logical/Physical Design`  
**Domain:** Social Community Systems, Database Lifecycle Management, E-commerce Integration  
**Status:** In Progress (Requirement Analysis & Conceptual Design Phase)

***

### Project Overview
Kyo-Board is a newly developed social community system designed to integrate with the Kyobo Book Centre platform. While the current Kyobo system is a successful transactional e-commerce platform, it lacks integrated structures for active social engagement. This project focuses on developing a "Book Club" system to bridge that gap, facilitating peer-to-peer interaction through asynchronous digital engagement.

***

### System Architecture & Implementation

* **Asynchronous Community Modeling:** Adoption of a **threaded, asynchronous community structure** (inspired by forum-based models like Reddit or Naver Cafe). This architectural choice prioritizes digital persistence over real-time chat, providing a clear technical framework for long-form user engagement and structured data categorization.
* **Database Lifecycle Engineering:** Development of the system through a formal four-stage design lifecycle:
    * **Conceptual Design:** Mapping entities and relationships for community threads, shared notes, and memberships.
    * **Logical Design:** Engineering a relational model that maintains integrity with existing retail metadata using standard identifiers (ISBN/Genre Codes) as foreign keys.
    * **Physical Design:** Identifying indexing strategies on core search fields to ensure high-speed query performance for club discovery and content retrieval.
    * **Implementation:** Defining system metadata and schema requirements to support concurrent user activity and data persistence.
* **User View Management:** Definition and management of the **User View** specifications. This involves mapping granular permission sets (CRUD operations) and data visibility for four distinct user tiers: **System Administrator**, **Board Manager**, **Registered Member**, and **Guest**.
* **System Optimization & Security:** Integration of **Role-Based Access Control (RBAC)** to secure user-generated content and the utilization of asynchronous data transaction methods to ensure system stability during high-concurrency event cycles.

***

### 🔒 Academic Integrity Note
To comply with university academic integrity policies, the documentation in this repository has been abstracted. Specific grading rubrics, internal SQL implementation details, and full university lab reports have been omitted. This repository serves to demonstrate proficiency in database system development, requirement analysis, and architectural planning.
