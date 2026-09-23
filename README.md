# 🚗 Parking Lot – Java LLD Project

A **concurrent and thread-safe Parking Lot Management System** built in **Java** using Low-Level Design (LLD) principles and object-oriented programming.

### ✨ Features

* Multi-floor parking lot management
* Supports **Cars, Bikes, and Trucks**
* Dynamic ticket generation
* Vehicle-specific parking prices
* Payment processing using **Strategy Pattern**
* Floor and spot validation
* Ticket tracking and management
* Thread-safe ticket ID generation
* Spot-level locking using `ReentrantLock`
* Concurrent data handling using `ConcurrentHashMap`
* Singleton-based Parking Lot management

### 🏗️ Design & Architecture

The project is structured into multiple layers:

* **Model** – Floor, Spot, Ticket
* **Manager** – Parking, Floor, and Ticket management
* **Service** – Parking Lot orchestration
* **Strategy** – Payment processing
* **Middleware** – Floor and spot validation
* **Enums** – Payment types

The system uses **resource-level locking**, where each parking spot has its own lock. This allows different vehicles to park concurrently while preventing two threads from occupying the same physical spot.

### 🔒 Concurrency Testing

The system was tested with **500 concurrent parking requests** across 5 floors with a total capacity of 500 spots.

**Test Result:**

* Total Requests: 500
* Successful: 500
* Rejected: 0
* Exceptions: 0
* Cars: 250
* Bikes: 150
* Trucks: 100

This project focuses on applying **Java concurrency, OOP, SOLID principles, design patterns, and LLD concepts** to a practical backend-style system.
