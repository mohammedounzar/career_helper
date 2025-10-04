from confluent_kafka import Consumer
import os

def consume(conf):

    consumer = Consumer(conf)
    consumer.subscribe([os.getenv("KAFKA_TOPIC_REQUEST")])

    print("✅ Resume Extractor listening to Kafka...")
    while True:
        msg = consumer.poll(1.0)
        if msg is None:
            continue
        if msg.error():
            print("Consumer error: {}".format(msg.error()))
            continue

        try:
            resume_id = int(msg.value().decode('utf-8'))
            print(f"✅ Resume {resume_id} processed successfully.")
            return resume_id
        except Exception as e:
            print(f"❌ Error processing resume: {e}")
