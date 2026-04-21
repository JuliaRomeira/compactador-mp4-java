# Compactador de Video MP4 em Java

Projeto inicial de um aplicativo desktop simples feito com Java Swing para compactar videos MP4 usando FFmpeg.

## Objetivo

Aprender Java construindo um app util para o dia a dia:

- selecionar um arquivo MP4
- escolher onde salvar o resultado
- definir o nivel de compactacao
- executar o FFmpeg pela interface
- acompanhar mensagens de status

## Estrutura

- `src/com/julia/videocompressor/Main.java`: ponto de entrada
- `src/com/julia/videocompressor/VideoCompressorFrame.java`: interface grafica
- `src/com/julia/videocompressor/CompressionPreset.java`: presets de compressao
- `src/com/julia/videocompressor/FfmpegRunner.java`: montagem e execucao do comando

## Como compilar

```powershell
javac -d out src\com\julia\videocompressor\*.java
```

## Como executar

```powershell
java -cp out com.julia.videocompressor.Main
```

## Como gerar JAR

```powershell
powershell -ExecutionPolicy Bypass -File .\build-jar.ps1
```

O arquivo sera criado em:

```text
dist\CompactadorMP4.jar
```

## Como gerar EXE

```powershell
powershell -ExecutionPolicy Bypass -File .\build-exe.ps1
```

O executavel sera criado em:

```text
installer\CompactadorMP4.exe
```

Se o WiX Toolset nao estiver instalado, o script vai gerar automaticamente uma versao portatil em pasta:

```text
installer\CompactadorMP4\
```

Essa pasta ja permite abrir o app pelo arquivo principal, mesmo sem o `.exe` instalador.

## Requisitos para o EXE

- Java JDK 17 com `jpackage`
- WiX Toolset no PATH para gerar `.exe` no Windows
- FFmpeg instalado ou caminho informado dentro do app

## Fluxo recomendado

1. Rodar `.\build-jar.ps1`
2. Testar o app
3. Rodar `.\build-exe.ps1`
4. Abrir o `.exe` gerado em `installer\`

## Observacao

O `ffmpeg` ainda precisa estar instalado no computador. Nesta primeira versao, voce pode:

- informar o caminho completo do `ffmpeg.exe`
- ou usar apenas `ffmpeg` se ele estiver no PATH

Exemplo de caminho:

```text
C:\ffmpeg\bin\ffmpeg.exe
```
