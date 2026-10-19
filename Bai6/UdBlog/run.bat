@echo off
chcp 65001 >nul
echo ========================================================
echo   Dang build va deploy ung dung UdBlog len Tomcat...
echo ========================================================

call .\gradlew.bat deploy
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Build that bai! Vui long kiem tra loi tren.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo ========================================================
echo   Khoi dong Apache Tomcat 10.1...
echo ========================================================
set "CATALINA_HOME=D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"
start "Apache Tomcat" "%CATALINA_HOME%\bin\startup.bat"

echo Dang doi Tomcat khoi dong (khoang 5 giay)...
ping 127.0.0.1 -n 6 >nul

echo.
echo ========================================================
echo   Mo trinh duyet toi dia chi:
echo   http://localhost:8080/blog-management-jpa/blogs
echo ========================================================
start http://localhost:8080/blog-management-jpa/blogs

echo Hoan tat! Ban co the tiep tuc su dung ung dung tren trinh duyet.
