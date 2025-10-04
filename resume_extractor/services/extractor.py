import os
import requests
import cloudinary
from cloudinary.utils import cloudinary_url
import fitz
from dotenv import load_dotenv

load_dotenv()
cloudinary.config(
    cloud_name=os.getenv("CLOUDINARY_CLOUD_NAME"),
    api_key=os.getenv("CLOUDINARY_API_KEY"),
    api_secret=os.getenv("CLOUDINARY_API_SECRET")
)

def fetch_pdf_from_cloud(public_id: str):
    url, _ = cloudinary_url(
        public_id,
        resource_type="raw",
        type="authenticated"  
    )

    response = requests.get(url)
    response.raise_for_status()
    return response.content

def ocr_pdf(pdf_bytes: bytes):
    text = ""
    with fitz.open(stream=pdf_bytes, filetype="pdf") as doc:
        for page in doc:
            text += page.get_text()
    return text

def extract_text_from_resume(public_id: str):
    pdf_bytes = fetch_pdf_from_cloud(public_id)
    return ocr_pdf(pdf_bytes)
