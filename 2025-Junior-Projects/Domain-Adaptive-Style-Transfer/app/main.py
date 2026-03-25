import os
from pathlib import Path
from fastapi import FastAPI, Request
from fastapi.responses import HTMLResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates

# 1. This finds the 'app' folder where this script lives
BASE_DIR = Path(__file__).resolve().parent

# 2. This finds the 'demo_data' folder by going up one level from 'app'
# This works on Mac, Windows, and Linux automatically
DEMO_DATA_PATH = BASE_DIR.parent / "demo_data"

app = FastAPI()

# 3. Mount the directory so the browser can 'see' the images
app.mount("/static", StaticFiles(directory=str(DEMO_DATA_PATH)), name="static")

# 4. Point to the templates folder
templates = Jinja2Templates(directory=str(BASE_DIR / "templates"))

@app.get("/", response_class=HTMLResponse)
async def home(request: Request):
    # Path to your 'original' folder
    original_path = DEMO_DATA_PATH / "original"
    
    # Grab the filenames
    images = [f for f in os.listdir(original_path) if f.lower().endswith(('.png', '.jpg'))]
    
    return templates.TemplateResponse("index.html", {
        "request": request,
        "images": images
    })