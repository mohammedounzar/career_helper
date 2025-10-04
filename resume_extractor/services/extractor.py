import textract

def extract_text(file_path: str) -> str:
    text = textract.process(file_path)
    return text.decode("utf-8")
