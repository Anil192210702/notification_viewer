import os
import re

target_dir = r"c:\Users\User\AndroidStudioProjects\goodlook\app\src\main\java\com\simats\goodlook\screens"

replacements = {
    r"Color\(0xFF1976D2\)": "MaterialTheme.colorScheme.primary",
    r"Color\.White": "MaterialTheme.colorScheme.background",
    r"Color\.Black": "MaterialTheme.colorScheme.onBackground",
    r"Color\.DarkGray": "MaterialTheme.colorScheme.onSurface",
    r"Color\.Gray": "MaterialTheme.colorScheme.onSurfaceVariant",
    r"Color\.LightGray": "MaterialTheme.colorScheme.surfaceVariant",
    r"Color\(0xFFF3F4F6\)": "MaterialTheme.colorScheme.surfaceVariant",
    r"Color\(0xFFF9FAFB\)": "MaterialTheme.colorScheme.surface",
    r"Color\(0xFFE3F2FD\)": "MaterialTheme.colorScheme.primaryContainer",
    r"Color\(0xFFE5E5E5\)": "MaterialTheme.colorScheme.surfaceVariant",
    r"Color\(0xFFFEE2E2\)": "MaterialTheme.colorScheme.errorContainer",
    r"Color\.Red": "MaterialTheme.colorScheme.error"
}

for root, _, files in os.walk(target_dir):
    for file in files:
        if file.endswith(".kt"):
            filepath = os.path.join(root, file)
            with open(filepath, "r", encoding="utf-8") as f:
                content = f.read()
            
            for old, new in replacements.items():
                content = re.sub(old, new, content)
            
            with open(filepath, "w", encoding="utf-8") as f:
                f.write(content)

print("Theme colors applied successfully.")
