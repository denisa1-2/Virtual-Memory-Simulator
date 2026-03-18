# Virtual-Memory-Simulator

## Overview
Virtual Memory Simulator is a Java desktop application that visually demonstrates how page replacement works in an operating system.

The application allows the user to configure the number of physical memory frames, define or generate a page reference sequence, choose a page replacement algorithm, and watch the simulation step by step through a graphical interface.

This project was built to improve understanding of virtual memory management concepts such as page faults, page tables, frame allocation, and replacement strategies.

---

## Features
- Interactive java Swing graphical interface
- Step-by-step visualization of page accesses
- Support for multiple page replacement algorithms:
    - FIFO (First-In, First-Out)
    - LRU (Least Recently Used)
    - OPT (Optimal Page Replacement)
- Manual page sequence input
- Random page sequence generation
- Real-time HIT / MISS highlighting
- Simulation statistics history
- Performance metrics calculation:
    - Total accesses
    - Total page faults
    - Fault rate
    - Average Memory Access Time (AMAT)
    - Memory utilization

---

## How It Works
For each page access in the sequence, the simulator:
1. Checks whether the page is already loaded in a physical frame
2. Marks the access as a **HIT** or **MISS**
3. Loads the page into a free frame if one exists
4. Otherwise, selects a victim page using the chosen replacement algorithm
5. Updates the page table and memory state
6. Displays the results visually in the GUI

The simulation is animated step by step so the user can follow how memory changes over time.

---

## Algorithms Implemented

### FIFO
Replaces the page that has been in memory the longest.

### LRU
Replaces the page that was used least recently, based on the last access time.

### OPT
Replaces the page that will not be used for the longest time in the future.
This is a theoretical optimal algorithm and is useful as a benchmark.

---

## Technologies Used
- Java
- Java Swing
- Object-Oriented Programmin

---

## Project Structure
```text
src/
|- algorithms/
|   |- FIFO.java
|   |- LRU.java
|   |- OPT.java
|   |- PageReplacementAlgorithm.java
|- core/
|   |- MemoryManager.java
|- gui/
|  |- SimulationFrame.java
|  |- StatisticsDialog.java
|- model/
|  |- Frame.java
|  |- Page.java
|  |- PageTable.java
|  |- PageTableEntry.java
|- statistics/
|  |- PerformanceMetrics.java
|  |- RunResult.java
|- utils/
|  |- PageSequenceGenerator.java
|- Main.java
```

## Example Configuration
- Algorithm: FIFO / LRU / OPT
- Frames: 3
- Pages: 10
- Sequence: `6 0 1 2 0 3 0 1`

During the simulation:
- the current page is highlighted in **green** for a HIT
- the current page is highlighted in **red** for a MISS
- the affected frame is visually updated
- the current memory state is shown after every step

---

## Statistics Provided
After each simulation, the application stores and displays:
- algorithm used
- number of frames
- number of pages
- total number of accesses
- number of page faults
- fault rate
- average memory access time (AMAT)
- memory utilization percentage

This makes it easier to compare the behavior of different algorithms under the same input sequence.

---

## Running the Project

### Requirements
- Java JDK installed
- An IDE such as IntelliJ IDEA

### Steps
1. Clone the reposiroty:
   ```bash
   git clone https://github.com/your-username/Virtual-Memory-Simulator.git
2. Open the project in IntelliJ IDEA
3. Run the **Main** class

## Why I Built This Project

This project was developed as part of a university assignment, but also as a personal learning initiative.

I wanted to better understand how virtual memory works at a deeper level, including how page replacement algorithms behave in practice and how operating systems manage memory efficently.

By building this simulator, I was able to explore both the theoretical and practical aspects of memory management, as well as imrpove my skills in Java, object-oriented design, and building graphical user interfaces.

## Possible Improvements
- Add charts for comparing algorithm performance
- Export simulation results to file
- Add more replacement strategies such as Clock or LFU
- Improve input validation
- Add unit tests
- Package the application as an executable JAR

## Screenshots
1. Main window
2. 
<img width="881" height="555" alt="image" src="https://github.com/user-attachments/assets/e15befa8-b0f1-4a22-b935-fec10bf1452f" />
*Main interface of the simulator showing the page sequence and memory frames.*

3. Running simulation

<img width="629" height="432" alt="image" src="https://github.com/user-attachments/assets/c538a301-c57d-4b1c-83db-86ed34e4fd55" />
<img width="561" height="456" alt="image" src="https://github.com/user-attachments/assets/42c978bc-f3c6-4ed5-9f3b-ea9c34c54269" />
*Step-by-step simulation highlighting page hits (green) and page faults (red).*

3. Statistics
<img width="877" height="321" alt="image" src="https://github.com/user-attachments/assets/10e95a1f-ba88-4b49-9763-10abe7c73e73" />
*Statistics table displaying performance metrics such as page faults, fault rate, and memory utilization.*
