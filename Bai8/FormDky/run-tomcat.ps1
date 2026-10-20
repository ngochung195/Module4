# Script tự động build, deploy và khởi chạy Tomcat
$tomcatHome = "D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"
$url = "http://localhost:8080/FormDky/"

Write-Host "==> Đang build và deploy ứng dụng vào Tomcat..." -ForegroundColor Cyan
./gradlew deployToTomcat

if ($LASTEXITCODE -ne 0) {
    Write-Host "Lỗi trong quá trình build/deploy!" -ForegroundColor Red
    exit $LASTEXITCODE
}

# Kiểm tra xem Tomcat có đang chạy trên cổng 8080 không
$portInUse = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue

if (-not $portInUse) {
    Write-Host "==> Tomcat chưa chạy. Đang khởi động Tomcat..." -ForegroundColor Yellow
    Start-Process -FilePath "$tomcatHome\bin\startup.bat"
    Start-Sleep -Seconds 4
} else {
    Write-Host "==> Tomcat đang chạy. File WAR đã được deploy tự động vào webapps!" -ForegroundColor Green
}

Write-Host "==> Mở trình duyệt tại: $url" -ForegroundColor Green
Start-Process $url
