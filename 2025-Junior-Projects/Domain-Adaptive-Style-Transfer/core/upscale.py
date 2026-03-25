import os
from PIL import Image
import shutil

# --- CONFIGURATION ---
source_folder = './results_model2_small/model2_realism/test_latest/images/fake_B'
target_folder = './my_project_data/images'
TARGET_WIDTH = 1914
TARGET_HEIGHT = 1052
# ---------------------

os.makedirs(target_folder, exist_ok=True)

print(f"Scanning {source_folder}...")

# Check if folder actually has files
try:
    files = os.listdir(source_folder)
except FileNotFoundError:
    print("ERROR: The folder path is still wrong. Check your spelling!")
    exit()

count = 0
for filename in files:
    # ACCEPT ANY PNG FILE (Don't require 'fake_B' in the name)
    if filename.endswith(".png") or filename.endswith(".jpg"):
        try:
            # 1. Open
            src_path = os.path.join(source_folder, filename)
            img = Image.open(src_path)
            
            # 2. Upscale
            img_resized = img.resize((TARGET_WIDTH, TARGET_HEIGHT), Image.Resampling.LANCZOS)
            
            # 3. Handle Renaming
            # If it has _fake_B, remove it. If not, keep the name.
            clean_name = filename.replace("_fake_B.png", ".png")
            
            dst_path = os.path.join(target_folder, clean_name)
            
            # 4. Save
            img_resized.save(dst_path)
            count += 1
            
            if count % 100 == 0:
                print(f"Upscaled {count} images...")
                
        except Exception as e:
            print(f"Error on {filename}: {e}")

print(f"Success! Upscaled and moved {count} images.")