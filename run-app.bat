@echo off

REM Compile backend and frontend Java files
echo Compiling Java files...
javac -d out backend\*.java frontend\*.java

REM Check if compilation was successful
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b
)

REM Copy resources to the output folder
echo Copying resources...
xcopy frontend\resources out\frontend\resources /E /I

REM Check if resources were copied successfully
if %ERRORLEVEL% NEQ 0 (
    echo Failed to copy resources!
    pause
    exit /b
)

REM Run the application
echo Starting the application...
cd out
java frontend.MainApp

REM Pause after the application ends
pause
