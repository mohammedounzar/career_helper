import os
import requests
from dotenv import load_dotenv
from consumer import consume
from services.db import get_resume_path, update_resume, get_job_description
from services.extractor import extract_text_from_resume
from publisher import publish

load_dotenv()

producer_conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP"),
}

consumer_conf = {
    'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP"),
    'group.id': os.getenv("KAFKA_GROUP_ID"),
    'auto.offset.reset': 'latest'
}


def main():
    while True:
        resume_id = consume(consumer_conf, "KAFKA_TOPIC_REQUEST")

        if resume_id:
            obj_path = get_resume_path(resume_id)

            ocr_result = ""
            if obj_path:
                ocr_result = extract_text_from_resume(obj_path)
                update_resume(resume_id, ocr_result)
                publish(producer_conf)
                print(f"✅ Resume {resume_id} updated successfully in the database.")

                # get job desc id from kafka topic
                print("📥 Consuming from Kafka topic job description ID...")
                job_desc_id = consume(consumer_conf, "KAFKA_JOB_DESC_TOPIC")
                # get job description
                if job_desc_id:
                    description = get_job_description(job_desc_id)
                    print(f"✅ Job Description {job_desc_id} fetched successfully from the database. The description: {description}")
                    # call ai-service API
                    url = "http://localhost:9091/calculateMatch"
                    params = {
                        "jobDescription": description,
                        "resumeText": ocr_result,
                    }
                    response = requests.get(url, params=params)
                    print(f"AI API status code: {response.status_code}")
                    print(f"Response: {response.text}")
                else:
                    print(f"⚠️ No job description ID received from Kafka.")
            else:
                print(f"⚠️ No object path found for resume {resume_id}")
        else:
            print("⚠️ No messages received from Kafka.")

if __name__ == "__main__":
    main()