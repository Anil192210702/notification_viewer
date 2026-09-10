import os

target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"

for root, _, files in os.walk(target_dir):
    for file in files:
        if file.endswith(".kt"):
            filepath = os.path.join(root, file)
            with open(filepath, "r", encoding="utf-8") as f:
                content = f.read()
                
            new_content = content.replace("Color(0xFF0D61D2)Container", "Color(0xFFE3F2FD)")
            new_content = new_content.replace("Color.RedContainer", "Color(0xFFFEE2E2)")
            
            if new_content != content:
                with open(filepath, "w", encoding="utf-8") as f:
                    f.write(new_content)

print("Fixed Container append errors.")
