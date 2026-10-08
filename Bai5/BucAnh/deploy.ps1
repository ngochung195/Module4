# Deployment Script for Tomcat 10
$ErrorActionPreference = "Stop"

$tomcatPath = "D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57"
$webappsPath = Join-Path $tomcatPath "webapps"
$warName = "nasa-apod.war"
$contextName = "nasa-apod"
$projectDir = $PSScriptRoot

Write-Host "=================================================" -ForegroundColor Cyan
Write-Host "   BAT DAU DONG GOI VA TRIEN KHAI LEN TOMCAT 10   " -ForegroundColor Yellow
Write-Host "=================================================" -ForegroundColor Cyan

# 1. Dong goi WAR bang Maven
Write-Host "`n[1/4] Dang bien dich va dong goi WAR bang Maven..." -ForegroundColor Green
Set-Location $projectDir
& mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Loi: Qua trinh dong goi Maven that bai!" -ForegroundColor Red
    exit 1
}

$sourceWar = Join-Path $projectDir "target\$warName"
if (-not (Test-Path $sourceWar)) {
    Write-Host "Loi: Khong tim thay file $sourceWar" -ForegroundColor Red
    exit 1
}

# 2. Xoa phien ban cu trong Tomcat webapps (neu co)
Write-Host "`n[2/4] Don dep ban deploy cu trong thu muc webapps..." -ForegroundColor Green
$targetWar = Join-Path $webappsPath $warName
$targetDir = Join-Path $webappsPath $contextName

if (Test-Path $targetWar) {
    Remove-Item -Path $targetWar -Force
    Write-Host "  -> Da xoa: $targetWar" -ForegroundColor DarkGray
}
if (Test-Path $targetDir) {
    Remove-Item -Path $targetDir -Recurse -Force
    Write-Host "  -> Da xoa thu muc giai nen cu: $targetDir" -ForegroundColor DarkGray
}

# 3. Copy file WAR moi vao webapps
Write-Host "`n[3/4] Dang sao chep $warName vao thu muc webapps cua Tomcat..." -ForegroundColor Green
Copy-Item -Path $sourceWar -Destination $webappsPath -Force
Write-Host "  -> Da trien khai thanh cong vao: $targetWar" -ForegroundColor Cyan

# 4. Kiem tra va khoi dong Tomcat
Write-Host "`n[4/4] Kiem tra trang thai Tomcat 10..." -ForegroundColor Green
$port8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue

if ($port8080) {
    Write-Host "Tomcat dang chay tren cong 8080. Tomcat se tu dong nap lai file WAR moi!" -ForegroundColor Green
} else {
    Write-Host "Tomcat chua chay. Dang khoi dong Tomcat 10..." -ForegroundColor Yellow
    $startupBat = Join-Path $tomcatPath "bin\startup.bat"
    Start-Process -FilePath "cmd.exe" -ArgumentList "/c `"$startupBat`""
    Write-Host "  -> Da kich hoat Tomcat 10 startup!" -ForegroundColor Green
}

Write-Host "=================================================" -ForegroundColor Cyan
Write-Host "           TRIEN KHAI HOAN TAT THANH CONG!         " -ForegroundColor Green
Write-Host "  URL ung dung: http://localhost:8080/$contextName  " -ForegroundColor Yellow
Write-Host "=================================================" -ForegroundColor Cyan
