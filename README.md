# Hogwarts Leaderboard Backend

This is a Spring Boot backend service for tracking and streaming house points in real-time.

## 🚀 Features
- REST API to ingest events (`/api/ingest/event`)
- Aggregated leaderboard queries
- Real-time leaderboard updates via **Server-Sent Events (SSE)** (`/leaderboard/stream`)
- Python script (`data_gen.py`) to generate dynamic random event data

---

## 🛠️ Prerequisites
- **Java 17+**
- **Maven 3.8+**
- **Python 3.12+**
- Install required Python dependencies:
  ```bash
  pip install requests

