# ⚙️ Concurrent Language Interpreter

A multi-threaded interpreter for a custom programming language, developed in Java. This project serves as a practical implementation of core computer science and language design concepts, exploring how programming languages manage memory, validate logic, and execute concurrent operations under the hood. 

The application is structured using the Model-View-Controller (MVC) pattern and includes a graphical dashboard to visualize the internal state of the interpreter in real-time.

## ✨ System Features

* **Concurrency & Multi-Threading:** Supports concurrent program execution via a custom `fork` statement. Threads are managed using Java's `ExecutorService`, allowing each thread to maintain its own Execution Stack and Symbol Table while safely sharing global resources.
* **Memory Management & Garbage Collection:** Implements a dynamic, shared Heap with a conservative Garbage Collector that automatically identifies and reclaims unreferenced memory locations to prevent memory leaks during execution.
* **Static Type Checking:** Features a robust pre-execution validation phase. The Type Checker evaluates all expressions, variable declarations, and state changes to guarantee type safety before the program is allowed to run.
* **JavaFX Monitoring Dashboard:** A fully integrated GUI that provides a real-time, step-by-step visualization of the language's internal architecture, including the shared Heap, File Table, and the individual Execution Stacks and Symbol Tables for every active thread.

## 🏗️ Internal Architecture

The interpreter processes execution states (`PrgState`) consisting of:
1.  **Execution Stack:** A stack of statements to execute for the current thread.
2.  **Symbol Table:** A thread-local dictionary mapping variable names to their values.
3.  **Heap:** A globally shared dictionary mapping memory addresses to allocated values.
4.  **File Table:** A globally shared registry of open files and their file descriptors.
5.  **Output Console:** A shared list maintaining the standard output of the program.

## 💻 Tech Stack

* **Language:** Java
* **GUI Framework:** JavaFX
* **Core Concepts:** Compiler/Interpreter Design, Concurrency, Lock Primitives, Garbage Collection, MVC Architecture, Static Type Validation.

## 📸 Screenshots
*(Coming Soon)*

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 11 or higher
* JavaFX SDK configured in your IDE
