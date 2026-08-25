# Kyo-Board: Integrated Book Club System (2026)

**Course:** Database Design  
**Collaboration:** Team Project  
**Tech Stack:** `SQL`, `Relational Modeling`, `ER Modeling`, `Conceptual/Logical/Physical Design`  
**Domain:** Social Community Systems, Database Design, E-commerce Integration  

***

### Project Overview
Kyo-Board is a social community system designed as an extension of the Kyobo Book Centre platform. While Kyobo Book Centre provides a comprehensive e-commerce environment for discovering and purchasing books, the project identifies an opportunity to support social interaction among readers.

The system introduces an integrated **Book Club** environment where users can create and participate in book-centered communities, share posts and notes, and interact through persistent, asynchronous discussions.

***

### System Architecture & Database Design

* **Asynchronous Community Modeling:** Designed a **threaded, asynchronous community structure** for book-centered discussions. The model supports persistent posts, replies, shared notes, and club membership rather than real-time messaging.

* **Database Design Lifecycle:** Developed the database through the standard design process:
    * **Requirement Analysis:** Identified system requirements, user types, community functions, and data relationships.
    * **Conceptual Design:** Modeled the major entities and relationships required for users, books, clubs, posts, replies, notes, and memberships.
    * **Logical Design:** Transformed the conceptual model into a relational schema while maintaining relationships with existing book metadata through identifiers such as **ISBN and genre codes**.
    * **Physical Design:** Considered table structures, keys, constraints, and indexing strategies required for efficient data storage and retrieval.
    * **Implementation:** Translated the finalized relational design into SQL-based database structures.

* **User View & Access Management:** Defined data visibility and CRUD permissions for four user levels:
    * **System Administrator**
    * **Board Manager**
    * **Registered Member**
    * **Guest**

* **Relational Integrity:** Applied primary keys, foreign keys, relationship constraints, and normalization principles to maintain consistency across the database.

* **Team-Based Development:** Worked collaboratively across the database design lifecycle, rotating responsibilities and participating in different stages of requirement analysis, modeling, schema design, and implementation rather than maintaining fixed individual roles.


