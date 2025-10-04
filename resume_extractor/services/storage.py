import requests

def download_file(link: str, resume_id: int) -> str:
    response = requests.get(link)
    file_path = f"/tmp/resume_{resume_id}.pdf"
    with open(file_path, "wb") as f:
        f.write(response.content)
    return file_path
