param(
    [string]$TomcatDir = "D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"
)

$ErrorActionPreference = "Stop"

Write-Host "=========================================================" -ForegroundColor Cyan
Write-Host "  BAT DAU TU DONG HOA: BUILD & DEPLOY CURRENCY CONVERTER  " -ForegroundColor Cyan
Write-Host "=========================================================" -ForegroundColor Cyan

# 1. Xác định hệ điều hành và chạy Gradle build
$isWindows = $IsWindows -or ($env:OS -like "*Windows*")
Write-Host "`n[1/4] Dang kiem tra moi truong va bien dich du an..." -ForegroundColor Yellow

$gradleCmd = ""
if ($isWindows) {
    if (Test-Path ".\gradlew.bat") {
        $gradleCmd = ".\gradlew.bat"
    } else {
        $gradleCmd = "gradle"
    }
} else {
    if (Test-Path "./gradlew") {
        $gradleCmd = "./gradlew"
    } else {
        $gradleCmd = "gradle"
    }
}

Write-Host "Thuc thi lenh: $gradleCmd clean war" -ForegroundColor Gray
& $gradleCmd clean war

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n[LOI] Qua trinh bien dich (Build) that bai voi ma loi $LASTEXITCODE!" -ForegroundColor Red
    exit 1
}

# 2. Kiểm tra File Artefact (WAR)
Write-Host "`n[2/4] Kiem tra tep WAR sau khi build..." -ForegroundColor Yellow
$warPath = "build\libs\currency-converter.war"
if (-not (Test-Path $warPath)) {
    Write-Host "[LOI] Khong tim thay tep '$warPath' sau khi build!" -ForegroundColor Red
    exit 1
}
Write-Host "-> Tep WAR hop le: $warPath" -ForegroundColor Green

# 3. Triển khai (Deploy) vào Tomcat
Write-Host "`n[3/4] Trien khai vao Tomcat: $TomcatDir" -ForegroundColor Yellow
if (-not (Test-Path $TomcatDir)) {
    Write-Host "[LOI] Thu muc Tomcat khong ton tai: $TomcatDir" -ForegroundColor Red
    exit 1
}

$webappsDir = Join-Path $TomcatDir "webapps"
$targetWar = Join-Path $webappsDir "currency-converter.war"
$targetFolder = Join-Path $webappsDir "currency-converter"

# Dọn dẹp bản deploy cũ
if (Test-Path $targetWar) {
    Write-Host "-> Xoa tep WAR cu: $targetWar" -ForegroundColor Gray
    Remove-Item -Path $targetWar -Force
}
if (Test-Path $targetFolder) {
    Write-Host "-> Xoa thu muc ung dung cu: $targetFolder" -ForegroundColor Gray
    Remove-Item -Path $targetFolder -Recurse -Force
}

# Sao chép file WAR mới
Write-Host "-> Sao chep currency-converter.war vao webapps..." -ForegroundColor Gray
Copy-Item -Path $warPath -Destination $webappsDir -Force
Write-Host "-> Deploy tep WAR thanh cong!" -ForegroundColor Green

# 4. Khởi chạy Tomcat Server
Write-Host "`n[4/4] Khoi dong may chu Tomcat..." -ForegroundColor Yellow
$binDir = Join-Path $TomcatDir "bin"

if ($isWindows) {
    $startupBat = Join-Path $binDir "startup.bat"
    $env:CATALINA_HOME = $TomcatDir
    Start-Process -FilePath "cmd.exe" -ArgumentList "/c `"$startupBat`"" -WorkingDirectory $binDir
} else {
    $startupSh = Join-Path $binDir "startup.sh"
    chmod +x (Join-Path $binDir "*.sh")
    & $startupSh
}

Write-Host "`n=========================================================" -ForegroundColor Green
Write-Host "  TRIEN KHAI VA KHOI DONG TOMCAT HOAN TAT!               " -ForegroundColor Green
Write-Host "=========================================================" -ForegroundColor Green
Write-Host "Duong dan truy cap ung dung:" -ForegroundColor White
Write-Host "-> http://localhost:8080/currency-converter/" -ForegroundColor Cyan
Write-Host "=========================================================`n" -ForegroundColor Green
