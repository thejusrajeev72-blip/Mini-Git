#!/bin/bash

# Mini Git Build Script
# This script compiles the Java source files and creates executable

echo "╔═══════════════════════════════════════════════╗"
echo "║   SMART VERSION CONTROL SYSTEM (MINI GIT)    ║"
echo "║              Build Script                     ║"
echo "╚═══════════════════════════════════════════════╝"
echo ""

# Create bin directory if it doesn't exist
mkdir -p bin

echo "Compiling Java source files..."

# Compile all Java files
javac -d bin src/com/minigit/*.java \
              src/com/minigit/models/*.java \
              src/com/minigit/core/*.java \
              src/com/minigit/utils/*.java \
              src/com/minigit/commands/*.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
    echo "To run Mini Git:"
    echo "  Interactive mode: java -cp bin com.minigit.MiniGit"
    echo "  Command mode: java -cp bin com.minigit.MiniGit <command> [args]"
    echo ""
    
    # Create JAR file
    echo "Creating JAR file..."
    cd bin
    jar cvfe ../MiniGit.jar com.minigit.MiniGit .
    cd ..
    
    if [ $? -eq 0 ]; then
        echo "✓ JAR file created successfully!"
        echo ""
        echo "To run using JAR:"
        echo "  java -jar MiniGit.jar"
        echo ""
    fi
else
    echo "✗ Compilation failed!"
    exit 1
fi
