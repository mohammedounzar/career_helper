from pypdf import PdfReader

def extract_text_from_resume(file_path):
    reader = PdfReader(file_path)

    text = ""
    for page in reader.pages:
        text += page.extract_text() or "" 
        text += "\n"

    text = page.extract_text()
    return text
