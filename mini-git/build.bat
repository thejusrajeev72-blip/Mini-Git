@echo off
REM Mini Git Build Script for Windows
REM This script compiles the Java source files and creates executable

echo ================================================
echo    SMART VERSION CONTROL SYSTEM (MINI GIT)
echo              Build Script
echo ================================================
echo.

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

echo Compiling Java source files...

REM Compile all Java files
javac -d bin src\com\minigit\*.java src\com\minigit\models\*.java src\com\minigit\core\*.java src\com\minigit\utils\*.java src\com\minigit\commands\*.java

if %errorlevel% equ 0 (
    echo [SUCCESS] Compilation successful!
    echo.
    echo To run Mini Git:
    echo   Interactive mode: java -cp bin com.minigit.MiniGit
    echo   Command mode: java -cp bin com.minigit.MiniGit ^<command^> [args]
    echo.
    
    REM Create JAR file
    echo Creating JAR file...
    cd bin
    jar cvfe ..\MiniGit.jar com.minigit.MiniGit .
    cd ..
    
    if %errorlevel% equ 0 (
        echo [SUCCESS] JAR file created successfully!
        echo.
        echo To run using JAR:
        echo   java -jar MiniGit.jar
        echo.
    )
) else (
    echo [ERROR] Compilation failed!
    exit /b 1
)

pause
