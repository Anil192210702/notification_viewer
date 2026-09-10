import os
import re

target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"

replacements = {
    r"MaterialTheme\.colorScheme\.primary": "Color(0xFF0D61D2)",
    r"MaterialTheme\.colorScheme\.background": "Color.White",
    r"MaterialTheme\.colorScheme\.onBackground": "Color.Black",
    r"MaterialTheme\.colorScheme\.onSurfaceVariant": "Color.Gray",
    r"MaterialTheme\.colorScheme\.onSurface": "Color.Black",
    r"MaterialTheme\.colorScheme\.surfaceVariant": "Color(0xFFF3F4F6)",
    r"MaterialTheme\.colorScheme\.surface": "Color.White",
    r"MaterialTheme\.colorScheme\.primaryContainer": "Color(0xFFE3F2FD)",
    r"MaterialTheme\.colorScheme\.errorContainer": "Color(0xFFFEE2E2)",
    r"MaterialTheme\.colorScheme\.error": "Color.Red"
}

for root, _, files in os.walk(target_dir):
    for file in files:
        if file.endswith(".kt"):
            filepath = os.path.join(root, file)
            with open(filepath, "r", encoding="utf-8") as f:
                content = f.read()
            
            # ensure Color is imported
            if "import androidx.compose.ui.graphics.Color" not in content:
                content = content.replace("import androidx.compose.runtime.Composable", "import androidx.compose.ui.graphics.Color\nimport androidx.compose.runtime.Composable")
                
            for old, new in replacements.items():
                content = re.sub(old, new, content)
            
            with open(filepath, "w", encoding="utf-8") as f:
                f.write(content)

print("Reverted to raw colors successfully.")
