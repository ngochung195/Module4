# Deployment Script for Tomcat 10
param(
    [string]$TomcatHome = "D:\Application\TomCat\apache-tomcat-10.1.57-windows-x64\apache-tomcat-10.1.57",
    [switch]$NoBrowser
)

$ErrorActionPreference = "Stop"
$projectDir = $PSScriptRoot
$warName = "nasa-apod.war"
$contextName = "nasa-apod"
$webappsPath = Join-Path $TomcatHome "webapps"

Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   TU DONG HOA BIEN DICH VA TRIEN KHAI LEN TOMCAT 10        " -ForegroundColor Yellow
Write-Host "============================================================" -ForegroundColor Cyan

# 1. Kiem tra thu muc Tomcat
if (-not (Test-Path $TomcatHome)) {
    Write-Host "Loi: Khong tim thay thu muc Tomcat tai: $TomcatHome" -ForegroundColor Red
    exit 1
}

# 2. Bien dich va dong goi WAR
Write-Host "`n[1/5] Dang bien dich va dong goi WAR..." -ForegroundColor Green
Set-Location $projectDir

if (Test-Path (Join-Path $projectDir "pom.xml")) {
    Write-Host "  -> Maven project (pom.xml) phat hien: Dang chay 'mvn clean package -DskipTests'..." -ForegroundColor Gray
    & mvn clean package -DskipTests
} elseif (Test-Path (Join-Path $projectDir "build.gradle")) {
    Write-Host "  -> Gradle project phat hien: Dang chay build WAR..." -ForegroundColor Gray
    if (Test-Path ".\gradlew.bat") {
        & .\gradlew.bat war
    } else {
        & gradle war
    }
} else {
    Write-Host "Loi: Khong tim thay pom.xml hoac build.gradle trong $projectDir" -ForegroundColor Red
    exit 1
}

if ($LASTEXITCODE -ne 0) {
    Write-Host "Loi: Qua trinh bien dich / dong goi WAR that bai!" -ForegroundColor Red
    exit 1
}

$sourceWar = Join-Path $projectDir "target\$warName"
if (-not (Test-Path $sourceWar)) {
    $gradleWar = Join-Path $projectDir "build\libs\$warName"
    if (Test-Path $gradleWar) {
        $sourceWar = $gradleWar
    } else {
        Write-Host "Loi: Khong tim thay file WAR tai: $sourceWar" -ForegroundColor Red
        exit 1
    }
}
Write-Host "  -> Dong goi thanh cong: $sourceWar" -ForegroundColor Cyan

# 3. Dung Tomcat neu dang chay tren cong 8080
Write-Host "`n[2/5] Kiem tra va dung Tomcat cu de tranh khoa file..." -ForegroundColor Green
$connections = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue
if ($connections) {
    Write-Host "  -> Phat hien Tomcat dang lang nghe tren cong 8080. Dang dung..." -ForegroundColor Yellow
    $shutdownBat = Join-Path $TomcatHome "bin\shutdown.bat"
    if (Test-Path $shutdownBat) {
        & $shutdownBat | Out-Null
    }
    
    $timeout = 6
    while ((Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue) -and ($timeout -gt 0)) {
        Start-Sleep -Seconds 1
        $timeout--
    }

    $staleConnections = Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue
    if ($staleConnections) {
        $pids = $staleConnections | Select-Object -ExpandProperty OwningProcess -Unique
        foreach ($p in $pids) {
            Write-Host "  -> Giai phong tien trinh treo PID: $p" -ForegroundColor DarkYellow
            Stop-Process -Id $p -Force -ErrorAction SilentlyContinue
        }
        Start-Sleep -Seconds 1
    }
    Write-Host "  -> Da dung Tomcat hoan tat." -ForegroundColor Cyan
} else {
    Write-Host "  -> Tomcat hien khong chay." -ForegroundColor Gray
}

# 4. Don dep ban deploy cu va copy WAR moi
Write-Host "`n[3/5] Trien khai file WAR vao thu muc webapps..." -ForegroundColor Green
$targetWar = Join-Path $webappsPath $warName
$targetDir = Join-Path $webappsPath $contextName

if (Test-Path $targetWar) {
    Remove-Item -Path $targetWar -Force
    Write-Host "  -> Da xoa WAR cu: $targetWar" -ForegroundColor DarkGray
}
if (Test-Path $targetDir) {
    Remove-Item -Path $targetDir -Recurse -Force
    Write-Host "  -> Da xoa thu muc giai nen cu: $targetDir" -ForegroundColor DarkGray
}

Copy-Item -Path $sourceWar -Destination $webappsPath -Force
Write-Host "  -> Da copy WAR moi vao: $targetWar" -ForegroundColor Cyan

# 5. Khoi dong Tomcat 10
Write-Host "`n[4/5] Khoi dong Tomcat 10..." -ForegroundColor Green
$startupBat = Join-Path $TomcatHome "bin\startup.bat"
Start-Process -FilePath "cmd.exe" -ArgumentList "/c `"$startupBat`""
Write-Host "  -> Da kich hoat startup.bat thanh cong." -ForegroundColor Cyan

# 6. Kiem tra trang thai ung dung
Write-Host "`n[5/5] Cho Tomcat khoi dong va kiem tra ung dung..." -ForegroundColor Green
$appUrl = "http://localhost:8080/$contextName/"
$ready = $false
for ($i = 1; $i -le 15; $i++) {
    Start-Sleep -Seconds 1
    try {
        $res = Invoke-WebRequest -Uri $appUrl -UseBasicParsing -TimeoutSec 2 -ErrorAction Stop
        if ($res.StatusCode -eq 200) {
            $ready = $true
            break
        }
    } catch {
        # Dang khoi dong
    }
}

Write-Host "============================================================" -ForegroundColor Cyan
if ($ready) {
    Write-Host "           TRIEN KHAI VA KHOI DONG THANH CONG!              " -ForegroundColor Green
} else {
    Write-Host "   Tomcat dang khoi dong, ung dung se san sang trong giay lat. " -ForegroundColor Yellow
}
Write-Host "   URL ung dung: $appUrl" -ForegroundColor Yellow
Write-Host "============================================================" -ForegroundColor Cyan

if (-not $NoBrowser) {
    Start-Process $appUrl
}
