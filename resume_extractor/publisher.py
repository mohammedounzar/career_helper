from confluent_kafka import Producer
import json
import os
from dotenv import load_dotenv

load_dotenv()

KAFKA_BOOTSTRAP = os.getenv("KAFKA_BOOTSTRAP")
KAFKA_TOPIC = os.getenv("KAFKA_TOPIC_RESPONSE")

def delivery_report(err, msg):
    if err is not None:
        print(f"❌ Message delivery failed: {err}")
    else:
        print(f"✅ Message delivered to {msg.topic()} [{msg.partition()}]")

def publish(conf):
    producer = Producer(conf)
    message = json.dumps({
        "status": "OCR_DONE"
    })
    producer.produce(KAFKA_TOPIC, value=message, callback=delivery_report)
    producer.flush() 
