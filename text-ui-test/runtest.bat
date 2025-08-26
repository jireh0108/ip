@ECHO OFF

REM create bin directory fresh
if exist ..\bin rmdir /s /q ..\bin
mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile all .java files under src/main/java
for /R ..\src\main\java\ %%f in (*.java) do (
    javac -cp ..\src\main\java\ -Xlint:none -d ..\bin "%%f"
)
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program
java -classpath ..\bin nova.Nova < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
