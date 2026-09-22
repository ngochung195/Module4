@echo off
setlocal enabledelayedexpansion

set "TOMCAT_DIR=D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"

echo =========================================================
echo   BAT DAU TU DONG HOA: BUILD ^& DEPLOY CURRENCY CONVERTER
echo =========================================================

echo.
echo [1/4] Dang bien dich du an bang Gradle...
if exist "gradlew.bat" (
    call gradlew.bat clean war
) else (
    call gradle clean war
)

if %ERRORLEVEL% neq 0 (
    echo [LOI] Qua trinh bien dich (Build) that bai voi ma loi %ERRORLEVEL%!
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo [2/4] Kiem tra tep WAR sau khi build...
if not exist "build\libs\currency-converter.war" (
    echo [LOI] Khong tim thay tep build\libs\currency-converter.war!
    pause
    exit /b 1
)
echo -^> Tep WAR hop le!

echo.
echo [3/4] Trien khai vao Tomcat tai %TOMCAT_DIR%...
if not exist "%TOMCAT_DIR%" (
    echo [LOI] Thu muc Tomcat khong ton tai: %TOMCAT_DIR%
    pause
    exit /b 1
)

set "WEBAPPS_DIR=%TOMCAT_DIR%\webapps"

if exist "%WEBAPPS_DIR%\currency-converter.war" (
    echo -^> Xoa tep WAR cu...
    del /f /q "%WEBAPPS_DIR%\currency-converter.war"
)

if exist "%WEBAPPS_DIR%\currency-converter" (
    echo -^> Xoa thu muc ung dung cu...
    rd /s /q "%WEBAPPS_DIR%\currency-converter"
)

echo -^> Sao chep currency-converter.war vao webapps...
copy /y "build\libs\currency-converter.war" "%WEBAPPS_DIR%\"

if %ERRORLEVEL% neq 0 (
    echo [LOI] Khong the sao chep tep WAR vao thu muc webapps cua Tomcat!
    pause
    exit /b %ERRORLEVEL%
)
echo -^> Deploy tep WAR thanh cong!

echo.
echo [4/4] Khoi dong may chu Tomcat...
set "CATALINA_HOME=%TOMCAT_DIR%"
start "" "%TOMCAT_DIR%\bin\startup.bat"

echo.
echo =========================================================
echo   TRIEN KHAI VA KHOI DONG TOMCAT HOAN TAT!
echo =========================================================
echo Duong dan truy cap ung dung:
echo -^> http://localhost:8080/currency-converter/
echo =========================================================
