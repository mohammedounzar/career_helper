import psycopg2
import os

def get_connection():
    return psycopg2.connect(
        dbname=os.getenv("DB_NAME"),
        user=os.getenv("DB_USER"),
        password=os.getenv("DB_PASSWORD"),
        host=os.getenv("DB_HOST"),
        port=os.getenv("DB_PORT")
    )

def get_resume_path(resume_id):
    conn = get_connection()
    cur = conn.cursor()
    cur.execute("SELECT object_path FROM resume WHERE id = %s", (resume_id,))
    row = cur.fetchone()
    cur.close()
    conn.close()
    return row[0] if row else None

def update_resume(resume_id, content):
    conn = get_connection()
    cur = conn.cursor()
    cur.execute(
        "UPDATE resume SET ocr_result = %s, state = %s WHERE id = %s",
        (content, "OCR_DONE", resume_id)
    )
    conn.commit()
    cur.close()
    conn.close()

def get_job_description(job_desc_id):
    conn = get_connection()
    cur = conn.cursor()
    cur.execute("SELECT description FROM job_description WHERE id = %s", (job_desc_id,))
    row = cur.fetchone()
    cur.close()
    conn.close()
    return row[0] if row else None