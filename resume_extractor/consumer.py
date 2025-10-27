from confluent_kafka import Consumer
import os

def consume(conf, topic):

    consumer = Consumer(conf)
    consumer.subscribe([os.getenv(topic)])

    print("Resume Extractor Service listening to Kafka...")
    while True:
        msg = consumer.poll(1.0)
        if msg is None:
            continue
        if msg.error():
            print("Consumer error: {}".format(msg.error()))
            continue

        try:
            id = int(msg.value().decode('utf-8'))
            print(f"ID: {id} processed successfully.")
            return id
        except Exception as e:
            print(f"Error processing: {e}")
