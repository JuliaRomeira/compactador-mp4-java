$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$srcDir = Join-Path $projectRoot "src"
$outDir = Join-Path $projectRoot "out"
$distDir = Join-Path $projectRoot "dist"
$mainClass = "com.julia.videocompressor.Main"
$jarFile = Join-Path $distDir "CompactadorMP4.jar"
$javaFiles = Get-ChildItem -Path $srcDir -Recurse -Filter *.java | ForEach-Object { $_.FullName }

New-Item -ItemType Directory -Force -Path $outDir | Out-Null
New-Item -ItemType Directory -Force -Path $distDir | Out-Null

if ($javaFiles.Count -eq 0) {
    throw "Nenhum arquivo .java foi encontrado em $srcDir"
}

Write-Host "Compilando arquivos Java..."
javac -d $outDir $javaFiles

Write-Host "Gerando JAR..."
jar --create --file $jarFile --main-class $mainClass -C $outDir .

Write-Host "JAR criado em: $jarFile"
