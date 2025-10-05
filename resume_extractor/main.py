import os
from dotenv import load_dotenv
from consumer import consume
from services.db import get_resume_path, update_resume
from services.extractor import extract_text_from_resume
from publisher import publish

load_dotenv()

conf = {
        'bootstrap.servers': os.getenv("KAFKA_BOOTSTRAP"),
        'group.id': os.getenv("KAFKA_GROUP_ID"),
    }

def main():
    while True:
        resume_id = consume(conf)

        if resume_id:
            obj_path = get_resume_path(resume_id)

            if obj_path:
                ocr_result = extract_text_from_resume(obj_path)
                update_resume(resume_id, ocr_result)
                publish(conf)
                print(f"✅ Resume {resume_id} updated successfully in the database.")
            else:
                print(f"⚠️ No object path found for resume {resume_id}")
        else:
            print("⚠️ No messages received from Kafka.")


if __name__ == "__main__":
    main()