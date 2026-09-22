@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ===================================================
echo [1/5] BIEN DICH DU AN (BUILD WAR)
echo ===================================================

if exist gradlew.bat (
    echo Tim thay Gradle Wrapper, bat dau chay gradlew.bat clean war...
    call gradlew.bat clean war
) else (
    echo Khong tim thay gradlew.bat, chay gradle clean war...
    call gradle clean war
)

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Qua trinh bien dich that bai! Vui long kiem tra lai ma nguon.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ===================================================
echo [2/5] KIEM TRA ARTEFACT (WAR FILE)
echo ===================================================

set WAR_FILE=build\libs\simple-dictionary.war

if not exist "%WAR_FILE%" (
    echo [ERROR] Khong tim thay file %WAR_FILE%! Qua trinh build chua tao ra file WAR.
    pause
    exit /b 1
)
echo Da tao thanh cong file: %WAR_FILE%

echo.
echo ===================================================
echo [3/5] TRIEN KHAI (DEPLOY) VAO TOMCAT
echo ===================================================

set TOMCAT_HOME=D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57
set WEBAPPS_DIR=%TOMCAT_HOME%\webapps

if not exist "%WEBAPPS_DIR%" (
    echo [ERROR] Khong tim thay thu muc webapps tai %WEBAPPS_DIR%!
    pause
    exit /b 1
)

echo Don dep ban deploy cu tai Tomcat webapps...
if exist "%WEBAPPS_DIR%\simple-dictionary.war" del /f /q "%WEBAPPS_DIR%\simple-dictionary.war"
if exist "%WEBAPPS_DIR%\simple-dictionary" rmdir /s /q "%WEBAPPS_DIR%\simple-dictionary"

echo Sao chep file WAR vao thu muc webapps...
copy /y "%WAR_FILE%" "%WEBAPPS_DIR%\"

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Khong the sao chep file WAR vao thu muc webapps!
    pause
    exit /b %ERRORLEVEL%
)
echo Trien khai thanh cong file WAR vao: %WEBAPPS_DIR%\simple-dictionary.war

echo.
echo ===================================================
echo [4/5] KHOI CHAY TOMCAT SERVER
echo ===================================================

echo Khoi chay Tomcat tu %TOMCAT_HOME%\bin\startup.bat...
start "" "%TOMCAT_HOME%\bin\startup.bat"

echo.
echo ===================================================
echo [5/5] HOAN TAT!
echo ===================================================
echo Ung dung Tra cuu Tu dien Spring MVC da duoc deploy va khoi dong thanh cong!
echo Duong dan truy cap: http://localhost:8080/simple-dictionary/
echo ===================================================

endlocal
