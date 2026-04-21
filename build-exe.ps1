$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$distDir = Join-Path $projectRoot "dist"
$installerDir = Join-Path $projectRoot "installer"
$jarFile = Join-Path $distDir "CompactadorMP4.jar"
$mainJar = "CompactadorMP4.jar"
$appName = "CompactadorMP4"
$wixLight = Get-Command light.exe -ErrorAction SilentlyContinue
$wixCandle = Get-Command candle.exe -ErrorAction SilentlyContinue

if (-not (Test-Path $jarFile)) {
    Write-Host "JAR nao encontrado. Gerando agora..."
    powershell -ExecutionPolicy Bypass -File (Join-Path $projectRoot "build-jar.ps1")
}

New-Item -ItemType Directory -Force -Path $installerDir | Out-Null

if ($wixLight -and $wixCandle) {
    Write-Host "Gerando EXE com jpackage..."
    jpackage `
        --type exe `
        --input $distDir `
        --dest $installerDir `
        --name $appName `
        --main-jar $mainJar
    Write-Host "EXE gerado em: $installerDir"
} else {
    Write-Host "WiX Toolset nao encontrado no PATH. Gerando versao portatil em pasta..."
    jpackage `
        --type app-image `
        --input $distDir `
        --dest $installerDir `
        --name $appName `
        --main-jar $mainJar
    Write-Host "Versao portatil gerada em: $(Join-Path $installerDir $appName)"
    Write-Host "Para gerar .exe depois, instale o WiX Toolset e rode este script novamente."
}
