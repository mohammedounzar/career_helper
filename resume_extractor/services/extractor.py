import os
import requests
import cloudinary
from dotenv import load_dotenv
from cloudinary import api
from pdf2image import convert_from_bytes
import pytesseract

load_dotenv()

cloudinary.config(
    cloud_name=os.getenv("CLOUDINARY_CLOUD_NAME"),
    api_key=os.getenv("CLOUDINARY_API_KEY"),
    api_secret=os.getenv("CLOUDINARY_API_SECRET")
)

def fetch_pdf_from_cloud(public_id: str):
    """
    Fetches a raw PDF from Cloudinary using the authenticated resource API.
    """
    file_info = api.resource(public_id, resource_type="raw")
    url = file_info['secure_url']

    response = requests.get(url)
    response.raise_for_status()
    return response.content

def ocr_pdf(pdf_bytes: bytes):
    """
    Extract text from PDF using pytesseract (Tesseract OCR) instead of fitz.
    """
    text = ""
    pages = convert_from_bytes(pdf_bytes)  # Convert each PDF page to image
    for page in pages:
        text += pytesseract.image_to_string(page)
    return text

def extract_text_from_resume(public_id: str):
    pdf_bytes = fetch_pdf_from_cloud(public_id)
    return ocr_pdf(pdf_bytes)