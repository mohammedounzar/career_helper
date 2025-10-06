import os
import requests
from dotenv import load_dotenv
from consumer import consume
from services.db import get_resume_path, update_resume, get_job_description
from services.extractor import extract_text_from_resume
from publisher import publish

load_dotenv()

conf = {
        'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP"),
        'group.id': os.getenv("KAFKA_GROUP_ID"),
    }

def main():
    while True:
        resume_id = consume(conf, "KAFKA_TOPIC_REQUEST")

        if resume_id:
            obj_path = get_resume_path(resume_id)

            ocr_result = ""
            if obj_path:
                ocr_result = extract_text_from_resume(obj_path)
                update_resume(resume_id, ocr_result)
                publish(conf)
                print(f"✅ Resume {resume_id} updated successfully in the database.")
            else:
                print(f"⚠️ No object path found for resume {resume_id}")
        else:
            print("⚠️ No messages received from Kafka.")
        # get job desc id from kafka topic
        job_desc_id = consume(conf, "KAFKA_JOB_DESC_TOPIC")
        # get job description
        description = get_job_description(job_desc_id)
        # call ai-service API
        url = "https://localhost:9091/ai-service/match"
        response = requests.get(url)

        # Check status code
        if response.status_code == 200:
            data = response.json()  # Convert JSON to Python dict
            print(data)
        else:
            print("Error:", response.status_code)

if __name__ == "__main__":
    main()