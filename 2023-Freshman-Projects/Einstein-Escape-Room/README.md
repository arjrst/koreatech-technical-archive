# Einstein's Memory: C-Based Logic Escape Room (2023)

**Course:** Problem Solving and Programming  
**Collaboration:** Team of 2
**Role:** Lead Developer *(Logic & State Management)*  
**Tech Stack:** `C` , `Standard I/O` , `String.h` , `Time.h`

---

### The Narrative Arc
This project combines fundamental C programming with interactive storytelling. The player takes on the role of Albert Einstein (IQ 200) in a high-stakes time-travel mishap.

**The Story:**
1. **The Accident:** Einstein travels back to 1664 to meet Isaac Newton. While Newton is dropping apples from a tree to study gravity, Einstein arrives underneath, gets struck on the head, and loses his memory.
2. **The Trap:** Einstein wakes up in a strange labyrinth of rooms.
3. **The Recovery:** To regain his memories and return to his own time, he must solve a series of logic riddles and "nonsense" quizzes.
4. **The Ending:** By recovering his memories and conversing with Newton, Einstein secures his historical legacy  
*(Note: This is a work of fiction).*

---  

### Technical Contributions (My Role)
I served as the Lead Developer, focusing on the core engine and the randomized room selection logic (Sections 2, 3, and 6 of the project draft):

* **Procedural Logic Engine:** Engineered a randomization system using `srand(time(NULL))` to dynamically assign "Quiz" vs. "Empty" rooms for every session.
* **Complex State Transitions:** Developed the "Memory Recovery" sequence, where successfully solved riddles trigger narrative milestones and unlock deeper levels of the labyrinth.
* **Robust Input Validation:** Implemented string validation using `strcmp` and managed the input buffer with `getchar()` to ensure smooth user interaction during text-heavy segments.
* **Game-Over Mechanics:** Built nested `while` loops to track player attempts, ensuring a balanced difficulty curve.

---

### Engineering Features
* **Randomized Selection:** A multi-room system where only a specific subset of rooms contains the necessary "Memory Quizzes."
* **Interactive Fiction Engine:** Bridged low-level C programming with a 6-part narrative structure.
* **Safety Logic:** Integrated validation for room choices to prevent program crashes during invalid user input.

---

### ⚙️ How to Run
1. **Compile:** `gcc teamprojectmypart.c -o einstein_escape`
2. **Execute:** `./einstein_escape`
3. **Gameplay:** Use numeric inputs for room navigation and text-based inputs for solving the memory riddles.
