import requests, time, random, uuid
from datetime import datetime, timezone

URL = "http://localhost:8080/api/ingest/event"

HOUSES = ["Gryff", "Slyth", "Raven", "Huff"]

def send_event():
    ev = {
        "category": random.choice(HOUSES),
        "points": random.randint(1, 100),
        "timestamp": datetime.now(timezone.utc).strftime("%Y-%m-%dT%H:%M:%SZ")
    }
    r = requests.post(URL, json=ev)
    print("sent", ev, "status", r.status_code)

if __name__ == "__main__":
    while True:
        send_event()
        time.sleep(random.uniform(0.5, 2.0))
