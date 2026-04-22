# 🎬 Compactador de Vídeo MP4 em Java

Aplicativo desktop desenvolvido em **Java** para compressão de vídeos MP4 utilizando o **FFmpeg**.

A aplicação foi criada para simplificar o processo de compressão de vídeos através de uma interface gráfica intuitiva, eliminando a necessidade de uso direto de linha de comando.

## 🎥 Demonstração

<p align="center">
  <img src="Animação.gif" alt="Demonstração do projeto" width="700"/>
</p>
 
</p>

---

## 💼 Contexto do Projeto

Este projeto foi desenvolvido para atender uma necessidade real no ambiente de trabalho: a compressão de vídeos MP4 para facilitar o compartilhamento e reduzir o uso de armazenamento.

Antes da ferramenta, o processo era feito manualmente via linha de comando utilizando FFmpeg, o que dificultava o uso por pessoas sem conhecimento técnico.

A proposta da aplicação foi simplificar esse fluxo através de uma interface gráfica acessível.

---

## 🧩 Problema Resolvido

- Processo manual e técnico para compressão de vídeos
- Dificuldade de uso para usuários não técnicos
- Falta de padronização no processo de compressão
- Tempo elevado para execução das tarefas

---

## 🚀 Funcionalidades

- Seleção de arquivos MP4
- Definição do diretório de saída
- Escolha de níveis de compressão
- Execução automática do FFmpeg
- Exibição de status em tempo real durante o processamento

---

## 🛠️ Tecnologias utilizadas

- Java (JDK 17)
- Java Swing (interface gráfica)
- FFmpeg (processamento de vídeo)

---

## 📁 Estrutura do projeto
src/com/julia/videocompressor/

│

├── Main.java # Ponto de entrada da aplicação

├── VideoCompressorFrame.java # Interface gráfica

├── CompressionPreset.java # Presets de compressão

└── FfmpegRunner.java # Execução do FFmpeg


---

## ⚙️ Como compilar

```powershell
javac -d out src\com\julia\videocompressor\*.java
```

## 📦 Gerar JAR
```
powershell -ExecutionPolicy Bypass -File .\build-jar.ps1
```

## Arquivo gerado:
```
dist\CompactadorMP4.jar
```
## 🧩 Gerar executável (.exe)
- powershell -ExecutionPolicy Bypass -File .\build-exe.ps1

## Saída:
````
installer\CompactadorMP4.exe
`````

## Caso o WiX Toolset não esteja instalado, será gerada uma versão portátil:
````
installer\CompactadorMP4\
`````

## 📈 Impacto

- Redução significativa no tempo de compressão de vídeos
- Facilidade de uso para usuários não técnicos
- Padronização do processo de compressão dentro do fluxo de trabalho
  
## 🧠 Observação

- A aplicação foi desenvolvida com foco em utilidade prática e aprendizado de integração entre Java e ferramentas externas como o FFmpeg.

## 🚧 Melhorias futuras

- Detecção automática do FFmpeg no sistema
- Barra de progresso durante compressão
- Suporte a múltiplos arquivos em fila
- Melhorias na interface gráfica
- Suporte a outros formatos de vídeo
  
## ⚠️ Requisitos

- Java JDK 17 (com suporte a jpackage)
- FFmpeg instalado ou configurado no PATH
- WiX Toolset (opcional para geração do instalador)
  
## 🔄 Fluxo recomendado

- Compilar o projeto
- Testar a aplicação
- Gerar JAR
- Gerar executável
- Utilizar a versão final
  
## 💡 Nota

- O FFmpeg deve estar disponível no sistema. Você pode configurar o caminho manualmente ou adicioná-lo ao PATH do sistema.

### Exemplo:
````
C:\ffmpeg\bin\ffmpeg.exe
````
