# Tự động hóa quy trình Biên dịch, Triển khai và Khởi chạy ứng dụng Spring MVC Simple Dictionary
$OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

$TomcatHome = "D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"
$WebappsDir = Join-Path $TomcatHome "webapps"
$WarFile = "build\libs\simple-dictionary.war"

Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "[1/5] BIÊN DỊCH DỰ ÁN (BUILD WAR)" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan

# 1. Xác định hệ điều hành và Gradle execution command
$IsWindows = $IsWindows -or ($env:OS -like "*Windows*")
if ($IsWindows) {
    if (Test-Path ".\gradlew.bat") {
        Write-Host "Tìm thấy gradlew.bat, đang thực thi: .\gradlew.bat clean war..." -ForegroundColor Yellow
        & .\gradlew.bat clean war
    } else {
        Write-Host "Đang thực thi: gradle clean war..." -ForegroundColor Yellow
        & gradle clean war
    }
} else {
    if (Test-Path "./gradlew") {
        chmod +x ./gradlew
        Write-Host "Tìm thấy gradlew, đang thực thi: ./gradlew clean war..." -ForegroundColor Yellow
        & ./gradlew clean war
    } else {
        Write-Host "Đang thực thi: gradle clean war..." -ForegroundColor Yellow
        & gradle clean war
    }
}

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERROR] Quá trình biên dịch thất bại với mã lỗi $LASTEXITCODE!" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host ""
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "[2/5] KIỂM TRA FILE ARTEFACT (WAR)" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan

# 2. Kiểm tra file WAR
if (-not (Test-Path $WarFile)) {
    Write-Host "[ERROR] Không tìm thấy file $WarFile! Quá trình build chưa tạo được file WAR." -ForegroundColor Red
    exit 1
}
Write-Host "File WAR đã sẵn sàng: $WarFile" -ForegroundColor Green

Write-Host ""
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "[3/5] TRIỂN KHAI (DEPLOY) VÀO TOMCAT" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan

# 3. Dọn dẹp bản deploy cũ và copy file WAR mới vào webapps
if (-not (Test-Path $WebappsDir)) {
    Write-Host "[ERROR] Không tìm thấy thư mục Tomcat webapps tại $WebappsDir!" -ForegroundColor Red
    exit 1
}

$OldWar = Join-Path $WebappsDir "simple-dictionary.war"
$OldFolder = Join-Path $WebappsDir "simple-dictionary"

if (Test-Path $OldWar) {
    Write-Host "Đang xóa file WAR cũ: $OldWar" -ForegroundColor Gray
    Remove-Item -Path $OldWar -Force
}
if (Test-Path $OldFolder) {
    Write-Host "Đang xóa thư mục deploy cũ: $OldFolder" -ForegroundColor Gray
    Remove-Item -Path $OldFolder -Recurse -Force
}

Write-Host "Sao chép file WAR vào $WebappsDir..." -ForegroundColor Yellow
Copy-Item -Path $WarFile -Destination $WebappsDir -Force
Write-Host "Đã triển khai thành công vào Tomcat webapps!" -ForegroundColor Green

Write-Host ""
Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "[4/5] KHỞI CHẠY TOMCAT SERVER" -ForegroundColor Cyan
Write-Host "===================================================" -ForegroundColor Cyan

# 4. Khởi chạy Tomcat
if ($IsWindows) {
    $StartupScript = Join-Path $TomcatHome "bin\startup.bat"
    Write-Host "Khởi chạy Tomcat qua: $StartupScript" -ForegroundColor Yellow
    Start-Process -FilePath "cmd.exe" -ArgumentList "/c", "`"$StartupScript`""
} else {
    $StartupScript = Join-Path $TomcatHome "bin/startup.sh"
    chmod +x (Join-Path $TomcatHome "bin/*.sh")
    Write-Host "Khởi chạy Tomcat qua: $StartupScript" -ForegroundColor Yellow
    & $StartupScript
}

Write-Host ""
Write-Host "===================================================" -ForegroundColor Green
Write-Host "[5/5] HOÀN TẤT!" -ForegroundColor Green
Write-Host "===================================================" -ForegroundColor Green
Write-Host "Ứng dụng Từ Điển Đơn Giản (Spring MVC) đã được deploy và khởi động!" -ForegroundColor Green
Write-Host "Đường dẫn truy cập: http://localhost:8080/simple-dictionary/" -ForegroundColor White -BackgroundColor DarkBlue
Write-Host "===================================================" -ForegroundColor Green
