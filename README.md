# Hogwarts Leaderboard Backend

This is a Spring Boot backend service for tracking and streaming house points in real-time.

🚀 Features

REST API to ingest events (/api/ingest/event)

Aggregated leaderboard queries

Real-time leaderboard updates via Server-Sent Events (SSE) (/leaderboard/stream)

Python script (data_gen.py) to generate dynamic random event data (📂 located at src/main/resources/static/)

🛠️ Prerequisites

Java 17+

Maven 3.8+

Python 3.12+

MySQL Workbench (or MySQL CLI)

Setup Instructions

## 📂 Project Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/avishekKundu/hogwarts-backend.git
   cd hogwarts-backend

Configure Database

Make sure MySQL is running

Create a database(DB details in application.properties)

Build the Project

mvn clean install

Run the Application

Run the main class HogwartsApplication.java

The backend will run on:

http://localhost:8080

Running the Python Event Generator

The Python script is located at:

src/main/resources/static/data_gen.py


Install dependencies:

pip install requests


Run the script:

python data_gen.py